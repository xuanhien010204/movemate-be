package com.toptierteam.movemate.service;

import com.toptierteam.movemate.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
    int deleteByUserId(Long userId);
    void deleteByToken(String token);
}
