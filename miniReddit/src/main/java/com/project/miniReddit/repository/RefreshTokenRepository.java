package com.project.miniReddit.repository;

import com.project.miniReddit.dto.RefreshTokenDto;
import com.project.miniReddit.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    public Optional<RefreshToken> findByRefreshToken(String token);
    public void deleteByRefreshToken(String token);
}
