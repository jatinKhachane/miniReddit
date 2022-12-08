package com.project.miniReddit.controller;

import com.project.miniReddit.dto.PostResponseDto;
import com.project.miniReddit.dto.UserResponseDto;
import com.project.miniReddit.dto.UserUpdateDetailsDto;
import com.project.miniReddit.dto.PostBookMarkRequestDto;
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

    @GetMapping("/user/get-details/{user_id}")
    public UserResponseDto getUserDetails(@PathVariable Long user_id){
        return userService.getUserDetails(user_id);
    }

    @PutMapping("/user/update")
    public UserResponseDto updateUserDetails(@RequestBody UserUpdateDetailsDto userUpdateDetailsDtoDto){
        return userService.updateUserDetails(userUpdateDetailsDtoDto);
    }


    @PostMapping("/user/posts/bookmark")
    public ResponseEntity<Void> bookMarkPost(@RequestBody PostBookMarkRequestDto postBookMarkRequestDto){
        userService.bookMarkPost(postBookMarkRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/posts/bookmark/{user_id}")
    public List<PostResponseDto> getAllBookMarkeddPost(@PathVariable Long user_id){
        return userService.getAllBookMarkeddPost(user_id);
    }

    @PostMapping("/user/follow-subreddit/{subreddit_id}")
    public ResponseEntity<Void> followSubreddit(@PathVariable Long subreddit_id) throws  Exception{
        userService.followSubreddit(subreddit_id);
        return ResponseEntity.ok().build();
    }
}
