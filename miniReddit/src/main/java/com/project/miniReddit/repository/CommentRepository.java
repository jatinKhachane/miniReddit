package com.project.miniReddit.repository;

import com.project.miniReddit.entity.Comment;
import com.project.miniReddit.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select * from comment where comment.post_id=?1", nativeQuery = true)
    public List<Comment> getAllCommentsByPostId(Long id);

    @Query(value = "select * from comment where comment.user_id=?1", nativeQuery = true)
    public List<Comment> getAllCommentsByUserId(Long id);

}
