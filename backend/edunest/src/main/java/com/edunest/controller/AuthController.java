package com.edunest.controller;

import com.edunest.dto.request.LoginRequest;
import com.edunest.dto.request.RegisterRequest;
import com.edunest.dto.response.ApiResponse;
import com.edunest.dto.response.JwtResponse;
import com.edunest.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController
 * ─────────────────────────────────────────────────────────────
 * Path:  src/main/java/com/edunest/controller/AuthController.java
 *
 * PUBLIC routes — no JWT token needed.
 *
 * ┌──────────────────────────────────────────────────────────┐
 * │  POST  /api/auth/register  →  Create account + get token │
 * │  POST  /api/auth/login     →  Login + get token          │
 * └──────────────────────────────────────────────────────────┘
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * POST /api/auth/register
     *
     * Request body:
     * {
     *   "name":       "Rahul Sharma",
     *   "email":      "rahul@gmail.com",
     *   "password":   "pass1234",
     *   "university": "Mumbai University",   (optional)
     *   "branch":     "Computer Science",    (optional)
     *   "semester":   5                      (optional)
     * }
     *
     * Response:
     * {
     *   "success": true,
     *   "message": "Registration successful",
     *   "data": {
     *     "token": "eyJhbGci...",
     *     "type":  "Bearer",
     *     "id":    "USER1000",
     *     "name":  "Rahul Sharma",
     *     "email": "rahul@gmail.com",
     *     "role":  "STUDENT"
     *   }
     * }
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<JwtResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        log.info("POST /api/auth/register  email={}", request.getEmail());

        JwtResponse response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful", response));
    }

    /**
     * POST /api/auth/login
     *
     * Request body:
     * {
     *   "email":    "rahul@gmail.com",
     *   "password": "pass1234"
     * }
     *
     * Response:
     * {
     *   "success": true,
     *   "message": "Login successful",
     *   "data": {
     *     "token": "eyJhbGci...",
     *     "type":  "Bearer",
     *     "id":    "USER1000",
     *     "name":  "Rahul Sharma",
     *     "email": "rahul@gmail.com",
     *     "role":  "STUDENT"
     *   }
     * }
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        log.info("POST /api/auth/login  email={}", request.getEmail());

        JwtResponse response = authService.login(request);

        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }
}