package com.project.miniReddit.repository;

import com.project.miniReddit.entity.Post;
import com.project.miniReddit.entity.Subreddit;
import com.project.miniReddit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    List<Post> findAllByPosts(Long id);
}
