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
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto){
        return postService.createPost(postRequestDto);
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @GetMapping("/posts/user/{u_id}")
    public List<PostResponseDto> getPostsByUser(@PathVariable Long u_id,
                                                @RequestParam(name = "page", required = false, defaultValue = "0") Integer pNumber,
                                                @RequestParam(name = "size", required = false, defaultValue = "2") Integer pSize,
                                                @RequestParam(name = "sortBy", required = false, defaultValue = "newest") String sortParam){
        return postService.getPostsByUser(u_id, pNumber, pSize, sortParam);
    };

    @GetMapping("/posts/subreddit/{s_id}")
    public List<PostResponseDto> getPostsBySubreddit(@PathVariable Long s_id,
                                                     @RequestParam(name = "page", required = false, defaultValue = "0") Integer pNumber,
                                                     @RequestParam(name = "size", required = false, defaultValue = "2") Integer pSize,
                                                     @RequestParam(name = "sortBy", required = false, defaultValue = "newest") String sortParam){
        return postService.getPostsBySubreddit(s_id, pNumber, pSize, sortParam);
    };
}
