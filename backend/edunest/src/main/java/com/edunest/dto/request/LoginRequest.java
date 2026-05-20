package com.edunest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * LoginRequest
 * ─────────────────────────────────────────────────────────────
 * Path:  src/main/java/com/edunest/dto/request/LoginRequest.java
 *
 * POST /api/auth/login
 */
@Data
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}