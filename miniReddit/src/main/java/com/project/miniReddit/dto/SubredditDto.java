package com.project.miniReddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubredditDto {
    private long subredditId;
    private String name;
    private String desc;
    private Integer numberOfPosts;
    private Long followersCount;
}
