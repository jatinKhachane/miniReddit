package com.project.miniReddit.controller;

import com.project.miniReddit.dto.SubredditDto;
import com.project.miniReddit.service.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubredditController {

    @Autowired
    private SubredditService subredditService;

    @GetMapping("/api/subreddit")
    public List<SubredditDto> getAllSubreddit(){
        return subredditService.getAllSubreddits();
    }

    @PostMapping("/api/subreddit")
    public SubredditDto createSubreddit(@RequestBody SubredditDto subredditDto) throws Exception{
        try{
            return subredditService.createSubreddit(subredditDto);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/api/subreddit/{subreddit_Id}")
    public SubredditDto getSubreddit(@PathVariable Long subreddit_Id) throws Exception {
        return subredditService.getSubredditById(subreddit_Id);
    }
}
