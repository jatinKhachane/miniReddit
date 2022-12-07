package com.project.miniReddit.controller;

import com.project.miniReddit.dto.VoteRequestDto;
import com.project.miniReddit.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping("/votes")
    public ResponseEntity<Void> doVote(@RequestBody VoteRequestDto voteRequestDto){
        voteService.doVote(voteRequestDto);
        return ResponseEntity.ok().build();
    }
}
