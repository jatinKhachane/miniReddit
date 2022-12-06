package com.project.miniReddit.controller;

import com.project.miniReddit.dto.PostRequestDto;
import com.project.miniReddit.dto.PostResponseDto;
import com.project.miniReddit.entity.Post;
import com.project.miniReddit.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/api/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto){
        return postService.createPost(postRequestDto);
    }

    @GetMapping("/api/posts")
    public List<PostResponseDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/api/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @GetMapping("/api/posts/user/{u_id}")
    public List<PostResponseDto> getPostsByUser(@PathVariable Long u_id){
        return postService.getPostsByUser(u_id);
    };

    @GetMapping("/api/posts/subreddit/{s_id}")
    public List<PostResponseDto> getPostsBySubreddit(@PathVariable Long s_id){
        return postService.getPostsBySubreddit(s_id);
    };
}
