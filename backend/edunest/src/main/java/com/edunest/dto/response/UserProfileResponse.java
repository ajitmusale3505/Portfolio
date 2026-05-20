package com.edunest.dto.response;

import com.edunest.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Returned to the frontend whenever user profile data is needed.
 * Never exposes: password, tokens, internal flags.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private String        id;
    private String        name;
    private String        email;
    private String        username;
    private String        profilePictureUrl;
    private String        bio;

    // Academic info
    private String        university;
    private String        branch;
    private Integer       semester;
    private Integer       yearOfStudy;
    private String        enrollmentNumber;
    private String        phoneNumber;

    // Role & status
    private Role          role;
    private Boolean       isActive;
    private Boolean       isEmailVerified;

    // Gamification
    private Integer       reputationPoints;
    private Integer       totalUploads;
    private Integer       totalDownloads;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
}