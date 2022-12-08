package com.project.miniReddit.service;

import com.project.miniReddit.dto.PostResponseDto;
import com.project.miniReddit.dto.UserResponseDto;
import com.project.miniReddit.dto.UserUpdateDetailsDto;
import com.project.miniReddit.entity.Post;
import com.project.miniReddit.dto.PostBookMarkRequestDto;
import com.project.miniReddit.entity.Subreddit;
import com.project.miniReddit.entity.User;
import com.project.miniReddit.exception.SpringRedditException;
import com.project.miniReddit.repository.PostRepository;
import com.project.miniReddit.repository.SubredditRepository;
import com.project.miniReddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private SubredditRepository subredditRepository;

    @Autowired
    private PostService postService;

    @Transactional
    public void bookMarkPost(PostBookMarkRequestDto postBookMarkRequestDto) {
        //get the post and update it
        //get the user and update it
        User user = userRepository.findById(postBookMarkRequestDto.getUserId()).get();
        Post post = postRepository.findById(postBookMarkRequestDto.getPostId()).get();

        user.getSavedPosts().add(post);
        post.getUserswhobookmarked().add(user);

        userRepository.save(user);
        postRepository.save(post);

    }

    public List<PostResponseDto> getAllBookMarkeddPost(Long user_id) {
//        User user = userRepository.findById(user_id).get();
//        //get the Posts liked by a user from PostRepository -> find Posts by User
//        List<Post> posts = postRepository.findAllByUserswholiked(user);
        List<Long> postIds = userRepository.getAllPostsBookMarkedByUser(user_id);
        return postIds.stream().map(this::mapPostIdsToPostResponseDto).collect(Collectors.toList());
    }

    private PostResponseDto mapPostIdsToPostResponseDto(Long post_id){
        Post post = postRepository.findById(post_id).orElseThrow(()-> new SpringRedditException("post not found"));
        return mapToPostResponseDto(post);
    }

    private PostResponseDto mapToPostResponseDto(Post post){
        PostResponseDto postResponseDto = PostResponseDto.builder()
                .post_id(post.getPostId())
                .postname(post.getPostName())
                .desc(post.getDescription())
                .subredditname(post.getSubreddit().getName())
                .username(post.getUser().getUsername())
                .build();

        return postResponseDto;
    }

    public UserResponseDto getUserDetails(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(
                ()->new SpringRedditException("User Not Found")
        );
        return mapToUserResponseDto(user);
    }

    private UserResponseDto mapToUserResponseDto(User user){
        Date date = Date.from(user.getCreated());

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .created(date)
                .build();
        return userResponseDto;
    }

    public UserResponseDto updateUserDetails(UserUpdateDetailsDto userUpdateDetailsDtoDto) {
        User user = userRepository.findByUsername(userUpdateDetailsDtoDto.getCurrentUsername()).orElseThrow(() -> new SpringRedditException("User not found"));
        if(userUpdateDetailsDtoDto.getNewUsername() != null)
            user.setUsername(userUpdateDetailsDtoDto.getNewUsername());
        if(userUpdateDetailsDtoDto.getNewEmail() != null)
            user.setEmail(userUpdateDetailsDtoDto.getNewEmail());
        userRepository.save(user);
        return mapToUserResponseDto(user);
    }

    @Transactional
    public void followSubreddit(Long subreddit_id) throws Exception{
        User user = authService.getCurrentUser();
        Subreddit subreddit = subredditRepository.findById(subreddit_id).orElseThrow(()-> new SpringRedditException("Subreddit not found"));
        int followed = userRepository.checkIfAlreadyFollowedByUser(user.getUserId(), subreddit_id);
        System.out.println(user.getUserId() + "-" + subreddit_id);
        if(followed != 0)
            throw new SpringRedditException("User have already followed this subreddit !!");
        userRepository.followSubredditWithId(user.getUserId(), subreddit_id);
        subreddit.setFollowersCount(subreddit.getFollowersCount()+1);
        subredditRepository.save(subreddit);
    }

    public List<PostResponseDto> getFeedPosts(Long user_id) {
        //get the posts from subreddits followed by user
        List<Long> subredditIdsFollowedByUser = userRepository.findSubredditsFollowedByUser(user_id);
        List<PostResponseDto> returnList = new ArrayList<PostResponseDto>();

        for(Long id : subredditIdsFollowedByUser){
            returnList.addAll(postService.getPostsBySubreddit(id));
        }

        Collections.shuffle(returnList);

        return returnList;
    }
}
