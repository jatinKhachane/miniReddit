package com.project.miniReddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {
    private Long post_id;
    private String postname;
    private String desc;
    private String subredditname;
    private String username;
}
