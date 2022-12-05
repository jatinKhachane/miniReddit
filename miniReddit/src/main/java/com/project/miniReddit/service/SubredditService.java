package com.project.miniReddit.service;

import com.project.miniReddit.dto.SubredditDto;
import com.project.miniReddit.entity.Subreddit;
import com.project.miniReddit.repository.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubredditService {

    @Autowired
    private SubredditRepository subredditRepository;
    @Autowired
    private AuthService authService;

    @Transactional
    public List<SubredditDto> getAllSubreddits(){
        List<Subreddit> subreddits = subredditRepository.findAll();
        return subreddits.stream().map(this::mapToSubredditDto).collect(Collectors.toList());
    }

    private SubredditDto mapToSubredditDto(Subreddit subreddit) {
        SubredditDto subredditDto = SubredditDto.builder()
                .subredditId(subreddit.getSubredditId())
                .name(subreddit.getName())
                .desc(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
        return subredditDto;
    }

    public SubredditDto createSubreddit(SubredditDto subredditDto) {
        Subreddit subreddit = subredditRepository.save(mapToSubredditEntity(subredditDto));
        subredditDto.setSubredditId(subreddit.getSubredditId());
        return subredditDto;
    }

    private Subreddit mapToSubredditEntity(SubredditDto subredditDto) {
        Subreddit subreddit = Subreddit.builder()
                .subredditId(subredditDto.getSubredditId())
                .name("/r "+subredditDto.getName())
                .description(subredditDto.getDesc())
                .createdDate(Instant.now())
                .user(authService.getCurrentUser())
                .build();
        return subreddit;
    }

    public SubredditDto getSubredditById(Long subreddit_id) throws Exception{
        Subreddit subreddit = subredditRepository.findById(subreddit_id).orElseThrow(()-> new RuntimeException("Subreddit Not Found"));
        return mapToSubredditDto(subreddit);
    }
}
