package com.project.miniReddit.repository;

import com.project.miniReddit.entity.Post;
import com.project.miniReddit.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "select p.post_id from user_saved_posts us, post p where us.post_id=p.post_id and us.user_id=?1",nativeQuery = true)
    List<Long> getAllPostsBookMarkedByUser(Long user_id);

    @Transactional
    @Modifying
    @Query(value = "insert into user_followed_subreddits values(?1,?2)", nativeQuery = true)
    void followSubredditWithId(Long user_id, Long subreddit_id);

    @Query(value = "select count(*) from user_followed_subreddits where user_id=?1 and subreddit_id=?2",nativeQuery = true)
    int checkIfAlreadyFollowedByUser(Long user_id, Long subreddit_id);
}
