package com.toptierteam.movemate.service.impl;

import com.toptierteam.movemate.dto.request.LoginRequest;
import com.toptierteam.movemate.dto.request.RefreshTokenRequest;
import com.toptierteam.movemate.dto.request.RegisterRequest;
import com.toptierteam.movemate.dto.response.JwtResponse;
import com.toptierteam.movemate.dto.response.MessageResponse;
import com.toptierteam.movemate.entity.RefreshToken;
import com.toptierteam.movemate.entity.users.User;
import com.toptierteam.movemate.enums.RoleType;
import com.toptierteam.movemate.repository.UserRepository;
import com.toptierteam.movemate.security.UserPrincipal;
import com.toptierteam.movemate.service.AuthService;
import com.toptierteam.movemate.service.RefreshTokenService;
import com.toptierteam.movemate.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userPrincipal.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userPrincipal.getId());

        return JwtResponse.builder()
            .accessToken(jwt)
            .refreshToken(refreshToken.getToken())
            .tokenType("Bearer")
            .id(userPrincipal.getId())
            .username(userPrincipal.getUsername())
            .email(userPrincipal.getEmail())
            .fullName(userPrincipal.getFullName())
            .build();
    }

    @Override
    public MessageResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username đã tồn tại!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        User user = User.builder()
            .username(registerRequest.getUsername())
            .email(registerRequest.getEmail())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .fullName(registerRequest.getFullName())
            .phoneNumber(registerRequest.getPhoneNumber())
            .role(RoleType.CUSTOMER)
            .active(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        userRepository.save(user);

        return new MessageResponse("Đăng ký thành công!");
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                String token = jwtUtils.generateJwtToken(user.getUsername());
                return JwtResponse.builder()
                    .accessToken(token)
                    .refreshToken(requestRefreshToken)
                    .tokenType("Bearer")
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .fullName(user.getFullName())
                    .build();
            })
            .orElseThrow(() -> new RuntimeException("Refresh token không hợp lệ!"));
    }

    @Override
    public MessageResponse logout(RefreshTokenRequest request) {
        refreshTokenService.deleteByToken(request.getRefreshToken());
        return new MessageResponse("Đăng xuất thành công!");
    }
}
