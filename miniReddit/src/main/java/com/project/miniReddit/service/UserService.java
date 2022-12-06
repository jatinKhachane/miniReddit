package com.project.miniReddit.service;

import com.project.miniReddit.dto.PostResponseDto;
import com.project.miniReddit.entity.Post;
import com.project.miniReddit.entity.PostLikeRequestDto;
import com.project.miniReddit.entity.User;
import com.project.miniReddit.repository.PostRepository;
import com.project.miniReddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    @Transactional
    public void likePost(PostLikeRequestDto postLikeRequestDto) {
        //get the post and update it
        //get the user and update it
        User user = userRepository.findById(postLikeRequestDto.getUserId()).get();
        Post post = postRepository.findById(postLikeRequestDto.getPostId()).get();

        user.getLikedposts().add(post);
        post.getUserswholiked().add(user);

        userRepository.save(user);
        postRepository.save(post);

    }

    public List<PostResponseDto> getAllLikedPost(Long user_id) {
        User user = userRepository.findById(user_id).get();
        //get the Posts liked by a user from PostRepository -> find Posts by User
        List<Post> posts = postRepository.findAllByUserswholiked(user);
        return posts.stream().map(this::mapToPostResponseDto).collect(Collectors.toList());
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
}
