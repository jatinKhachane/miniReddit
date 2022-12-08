package com.project.miniReddit.repository;

import com.project.miniReddit.entity.Post;
import com.project.miniReddit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select * from post where post.subreddit_id=?1", nativeQuery = true)
    public Page<Post> getAllPostsBySubredditId(Long id, String attr, String direction, Pageable p);

    @Query(value = "select * from post where user_id=?1 order by ?2 ?3", nativeQuery = true)
    public Page<Post> getAllPostsByUserId(Long id, String attr, String direction, Pageable p);
}
