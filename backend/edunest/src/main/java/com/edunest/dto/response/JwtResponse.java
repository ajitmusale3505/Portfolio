package com.edunest.dto.response;

import com.edunest.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JwtResponse
 * ─────────────────────────────────────────────────────────────
 * Path:  src/main/java/com/edunest/dto/response/JwtResponse.java
 *
 * Returned after successful login or register.
 * Frontend stores the token and sends it in every request header:
 *   Authorization: Bearer <token>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String token;
    private String type = "Bearer";

    // Basic user info — so frontend doesn't need a separate /me call after login
    private String id;
    private String name;
    private String email;
    private Role   role;
    private Boolean isEmailVerified;
}