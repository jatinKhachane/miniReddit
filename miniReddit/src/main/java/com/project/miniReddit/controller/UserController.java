package com.project.miniReddit.controller;

import com.project.miniReddit.dto.PostResponseDto;
import com.project.miniReddit.dto.UserResponseDto;
import com.project.miniReddit.dto.UserUpdateDetailsDto;
import com.project.miniReddit.entity.PostLikeRequestDto;
import com.project.miniReddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/getDetails/{user_id}")
    public UserResponseDto getUserDetails(@PathVariable Long user_id){
        return userService.getUserDetails(user_id);
    }

    @PutMapping("/user/update")
    public UserResponseDto updateUserDetails(@RequestBody UserUpdateDetailsDto userUpdateDetailsDtoDto){
        return userService.updateUserDetails(userUpdateDetailsDtoDto);
    }


    @PostMapping("/user/posts/like")
    public ResponseEntity<Void> likePost(@RequestBody PostLikeRequestDto postLikeRequestDto){
        userService.likePost(postLikeRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/posts/like/{user_id}")
    public List<PostResponseDto> getAllLikedPost(@PathVariable Long user_id){
        return userService.getAllLikedPost(user_id);
    }
}
