package com.project.miniReddit.service;

import com.project.miniReddit.dto.PostRequestDto;
import com.project.miniReddit.dto.PostResponseDto;
import com.project.miniReddit.entity.Post;
import com.project.miniReddit.entity.Subreddit;
import com.project.miniReddit.repository.PostRepository;
import com.project.miniReddit.repository.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private SubredditRepository subredditRepository;
    @Autowired
    private AuthService authService;

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Subreddit subreddit = subredditRepository.findByName(postRequestDto.getSubredditname());

        Post post = Post.builder()
                .postName(postRequestDto.getPostname())
                .description(postRequestDto.getDesc())
                .createdDate(Instant.now())
                .user(authService.getCurrentUser())
                .subreddit(subreddit)
                .voteCount(0)
                .commentCount(0)
                .build();

        postRepository.save(post);

        PostResponseDto postResponseDto = PostResponseDto.builder()
                .post_id(post.getPostId())
                .postname(post.getPostName())
                .desc(post.getDescription())
                .subredditname(post.getSubreddit().getName())
                .username(post.getUser().getUsername())
                .build();

        return postResponseDto;
    }

    @Transactional
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToPostResponseDto).collect(toList());
    }

    private PostResponseDto mapToPostResponseDto(Post post) {

        PostResponseDto postResponseDto = PostResponseDto.builder()
                .post_id(post.getPostId())
                .postname(post.getPostName())
                .desc(post.getDescription())
                .subredditname(post.getSubreddit().getName())
                .username(post.getUser().getUsername())
                .votecount(post.getVoteCount())
                .commentcount(post.getCommentCount())
                .build();

        return postResponseDto;
    }

    public PostResponseDto getPost(Long id) {
        Post post = postRepository.getById(id);
        return mapToPostResponseDto(post);
    }

    public List<PostResponseDto> getPostsByUser(Long u_id,
                                                Integer pNumber,
                                                Integer pSize,
                                                String sortParam) {
        Integer pageNumber = pNumber;
        Integer pageSize = pSize;
        String attr = null;
        String direction = null;

        if(sortParam.equals("newest")){ attr = "created_date"; direction = "desc";}
        else if(sortParam.equals("oldest")){ attr = "created_date"; direction = "asc";}
        else if(sortParam.equals("comment-count")){ attr = "comment_count"; direction = "desc";};

        Pageable pageable = null;

        if(direction.equals("desc"))
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(attr).descending());
        else
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(attr).ascending());

        Page<Post> pages_posts = postRepository.getAllPostsByUserId(u_id, attr, direction, pageable);
        List<Post> posts = pages_posts.getContent();
        return posts.stream().map(this::mapToPostResponseDto).collect(Collectors.toList());
    }

    public List<PostResponseDto> getPostsBySubreddit(Long s_id,
                                                     Integer pNumber,
                                                     Integer pSize,
                                                     String sortParam) {
        Integer pageNumber = pNumber;
        Integer pageSize = pSize;
        String attr = null;
        String direction = null;

        if(sortParam.equals("newest")){ attr = "created_date"; direction = "desc";}
        else if(sortParam.equals("oldest")){ attr = "created_date"; direction = "asc";}
        else if(sortParam.equals("comment-count")){ attr = "comment_count"; direction = "desc";};

        Pageable pageable = null;

        if(direction.equals("desc"))
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(attr).descending());
        else
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(attr).ascending());

        Page<Post> pages_posts = postRepository.getAllPostsBySubredditId(s_id, attr, direction, pageable);
        List<Post> posts = pages_posts.getContent();
        return posts.stream().map(this::mapToPostResponseDto).collect(Collectors.toList());
    }

    public List<PostResponseDto> getPostsBySubreddit(Long s_id) {
        List<Post> posts = postRepository.getAllPostsBySubredditId(s_id);
        return posts.stream().map(this::mapToPostResponseDto).collect(Collectors.toList());
    }
}
