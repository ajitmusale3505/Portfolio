package com.edunest.service;

import com.edunest.dto.request.LoginRequest;
import com.edunest.dto.request.RegisterRequest;
import com.edunest.dto.response.JwtResponse;

/**
 * AuthService
 * ─────────────────────────────────────────────────────────────
 * Path:  src/main/java/com/edunest/service/AuthService.java
 */
public interface AuthService {

    // POST /api/auth/register
    JwtResponse register(RegisterRequest request);

    // POST /api/auth/login
    JwtResponse login(LoginRequest request);
}