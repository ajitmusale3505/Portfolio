package com.edunest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * RegisterRequest
 * ─────────────────────────────────────────────────────────────
 * Path:  src/main/java/com/edunest/dto/request/RegisterRequest.java
 *
 * POST /api/auth/register
 */
@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
    private String password;

    // Optional fields during registration
    private String university;
    private String branch;
    private Integer semester;
}