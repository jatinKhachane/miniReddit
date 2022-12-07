package com.project.miniReddit.dto;

import com.project.miniReddit.entity.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteRequestDto {
    private VoteType voteType;
    private Long postId;
}
