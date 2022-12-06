package com.project.miniReddit.controller;

import com.project.miniReddit.dto.PostResponseDto;
import com.project.miniReddit.entity.PostLikeRequestDto;
import com.project.miniReddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user/posts/like")
    public ResponseEntity<Void> likePost(@RequestBody PostLikeRequestDto postLikeRequestDto){
        userService.likePost(postLikeRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/user/posts/like/{user_id}")
    public List<PostResponseDto> getAllLikedPost(@PathVariable Long user_id){
        return userService.getAllLikedPost(user_id);
    }
}
