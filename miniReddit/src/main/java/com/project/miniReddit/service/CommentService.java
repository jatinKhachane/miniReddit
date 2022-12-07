package com.project.miniReddit.service;

import com.project.miniReddit.dto.CommentDto;
import com.project.miniReddit.entity.Comment;
import com.project.miniReddit.entity.Post;
import com.project.miniReddit.entity.User;
import com.project.miniReddit.repository.CommentRepository;
import com.project.miniReddit.repository.PostRepository;
import com.project.miniReddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    public void createComment(CommentDto commentRequestDto) {
        Post post = postRepository.findById(commentRequestDto.getPost_id()).get();
        User user = userRepository.findByUsername(commentRequestDto.getUsername()).get();

        Comment comment = Comment.builder()
                .text(commentRequestDto.getText())
                .createdDate(Instant.now())
                .post(post)
                .user(user)
                .build();

        post.setCommentCount(post.getCommentCount() + 1);

        postRepository.save(post);
        commentRepository.save(comment);
    }

    public List<CommentDto> getCommentsByPostId(long post_id) {
        List<Comment> comments = commentRepository.getAllCommentsByPostId(post_id);
        return comments.stream().map(this::mapToCommentDto).collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByUserName(String username) {
        User user = userRepository.findByUsername(username).get();
        Long user_id = user.getUserId();
        System.out.println(user_id);
        List<Comment> comments = commentRepository.getAllCommentsByUserId(user_id);
        return comments.stream().map(this::mapToCommentDto).collect(Collectors.toList());
    }

    private CommentDto mapToCommentDto(Comment comment){
        CommentDto commentDto = CommentDto.builder()
                .text(comment.getText())
                .post_id(comment.getPost().getPostId())
                .username(comment.getUser().getUsername())
                .build();
        return commentDto;
    }
}
