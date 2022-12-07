package com.project.miniReddit.repository;

import com.project.miniReddit.entity.Post;
import com.project.miniReddit.entity.User;
import com.project.miniReddit.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query(value = "select * from vote v where v.user_id = ?1 and v.post_id = ?2 order by v.vote_id desc limit 1", nativeQuery = true)
    Optional<Vote> getTheLatestVoteByCurrentUserOnThisPost(Long user_id, Long post_id);
}
