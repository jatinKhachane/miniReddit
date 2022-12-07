package com.project.miniReddit.service;

import com.project.miniReddit.dto.VoteRequestDto;
import com.project.miniReddit.entity.Post;
import com.project.miniReddit.entity.User;
import com.project.miniReddit.entity.Vote;
import com.project.miniReddit.exception.SpringRedditException;
import com.project.miniReddit.repository.PostRepository;
import com.project.miniReddit.repository.UserRepository;
import com.project.miniReddit.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.project.miniReddit.entity.VoteType.UPVOTE;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AuthService authService;

    public void  doVote(VoteRequestDto voteRequestDto){
        //current User wants to vote on Post with Post_id
        // check if his last vote is upvote and now also upvote
        Post post = postRepository.findById(voteRequestDto.getPostId()).orElseThrow(
                ()-> new SpringRedditException("Post with id " + voteRequestDto.getPostId() + "Not found!!")
        );

        User user = authService.getCurrentUser();
        Optional<Vote> vote = voteRepository.getTheLatestVoteByCurrentUserOnThisPost(user.getUserId(), voteRequestDto.getPostId());

        if(vote.isPresent() && vote.get().getVoteType().equals(voteRequestDto.getVoteType())){
            throw new SpringRedditException("You have already " + voteRequestDto.getVoteType() + "'d for this post");
        }

        //increment voteCount on the Post
        if (voteRequestDto.getVoteType().equals(UPVOTE)) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }


        // remove the last vote by user
        if(vote.isPresent())
            voteRepository.deleteById(vote.get().getVoteId());

        Vote voteToSave = Vote.builder()
                .voteType(voteRequestDto.getVoteType())
                .post(post)
                .user(user)
                .build();

        voteRepository.save(voteToSave);
        postRepository.save(post);
    }
}
