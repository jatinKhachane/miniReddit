package com.project.miniReddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDetailsDto {
    private String currentUsername;
    private String newUsername;
    private String newEmail;
}
