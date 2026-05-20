package com.edunest.service.impl;

import com.edunest.dto.request.LoginRequest;
import com.edunest.dto.request.RegisterRequest;
import com.edunest.dto.response.JwtResponse;
import com.edunest.entity.User;
import com.edunest.enums.Role;
import com.edunest.exception.BadRequestException;
import com.edunest.repository.UserRepository;
import com.edunest.security.JwtUtils;
import com.edunest.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * AuthServiceImpl
 * ─────────────────────────────────────────────────────────────
 * Path:  src/main/java/com/edunest/service/impl/AuthServiceImpl.java
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository        userRepository;
    private final PasswordEncoder       passwordEncoder;
    private final JwtUtils              jwtUtils;
    private final AuthenticationManager authenticationManager;

    // ═══════════════════════════════════════════════════════════════════
    // REGISTER
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional
    public JwtResponse register(RegisterRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());

        // 1. Check email not already taken
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException(
                "Email '" + request.getEmail() + "' is already registered"
            );
        }

        // 2. Build and save user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .university(request.getUniversity())
                .branch(request.getBranch())
                .semester(request.getSemester())
                .role(Role.STUDENT)
                .isActive(true)
                .isEmailVerified(false)
                .reputationPoints(0)
                .totalUploads(0)
                .totalDownloads(0)
                .build();

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {}", savedUser.getId());

        // 3. Generate JWT token
        String token = jwtUtils.generateToken(savedUser.getId());

        // 4. Update last login
        userRepository.updateLastLoginAt(savedUser.getId(), LocalDateTime.now());

        return JwtResponse.builder()
                .token(token)
                .type("Bearer")
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .isEmailVerified(savedUser.getIsEmailVerified())
                .build();
    }

    // ═══════════════════════════════════════════════════════════════════
    // LOGIN
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional
    public JwtResponse login(LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());

        // 1. Authenticate — Spring Security checks email + password
        //    Throws BadCredentialsException if wrong (caught by GlobalExceptionHandler)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Set in SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Get userId from authenticated UserDetails
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();  // we stored userId as username

        // 4. Load user entity to build response
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        // 5. Update last login timestamp
        userRepository.updateLastLoginAt(userId, LocalDateTime.now());

        // 6. Generate JWT token
        String token = jwtUtils.generateToken(userId);
        log.info("Login successful for user: {}", userId);

        return JwtResponse.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .isEmailVerified(user.getIsEmailVerified())
                .build();
    }
}