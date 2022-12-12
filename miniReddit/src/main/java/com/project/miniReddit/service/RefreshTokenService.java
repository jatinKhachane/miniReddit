package com.project.miniReddit.service;

import com.project.miniReddit.dto.RefreshTokenDto;
import com.project.miniReddit.entity.RefreshToken;
import com.project.miniReddit.exception.SpringRedditException;
import com.project.miniReddit.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenDto generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setCreatedData(Instant.now());

        refreshTokenRepository.save(refreshToken);

        RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
                .refreshToken(refreshToken.getRefreshToken())
                .createdDate(refreshToken.getCreatedData())
                .build();

        return refreshTokenDto;
    }

    public void validateRefreshToken(String token){
        refreshTokenRepository.findByRefreshToken(token).orElseThrow(
                ()-> new SpringRedditException("Invalid Refresh Token !!")
        );
    }

    @Transactional
    public void deleteToken(String token){
        refreshTokenRepository.deleteByRefreshToken(token);
    }
}
