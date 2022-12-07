package com.project.miniReddit.controller;

import com.project.miniReddit.dto.CommentDto;
import com.project.miniReddit.entity.Post;
import com.project.miniReddit.repository.CommentRepository;
import com.project.miniReddit.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity createComment(@RequestBody CommentDto commentDto){
        commentService.createComment(commentDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/comments/post/{post_id}")
    public List<CommentDto> getCommentsByPostId(@PathVariable long post_id) {
        return commentService.getCommentsByPostId(post_id);
    }

    @GetMapping("/comments/user/{username}")
    public List<CommentDto> getCommentsByUserName(@PathVariable String username) {
        return commentService.getCommentsByUserName(username);
    }
}
