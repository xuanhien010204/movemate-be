package com.toptierteam.movemate.service;

import com.toptierteam.movemate.dto.request.LoginRequest;
import com.toptierteam.movemate.dto.request.RefreshTokenRequest;
import com.toptierteam.movemate.dto.request.RegisterRequest;
import com.toptierteam.movemate.dto.response.JwtResponse;
import com.toptierteam.movemate.dto.response.MessageResponse;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);
    MessageResponse register(RegisterRequest registerRequest);
    JwtResponse refreshToken(RefreshTokenRequest request);
    MessageResponse logout(RefreshTokenRequest request);
}
