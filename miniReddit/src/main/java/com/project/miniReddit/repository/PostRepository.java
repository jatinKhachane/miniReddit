package com.project.miniReddit.repository;

import com.project.miniReddit.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select * from post where post.subreddit_id=?1", nativeQuery = true)
    public List<Post> getAllPostsBySubredditId(Long id);

    @Query(value = "select * from post where post.user_id=?1", nativeQuery = true)
    public List<Post> getAllPostsByUserId(Long id);
}
