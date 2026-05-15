package com.edunest.entity;


import com.edunest.enums.Role;
import com.edunest.util.CustomIdGenerator;
import com.edunest.util.IdPrefix;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents every person who uses EduNest.
 * Roles: STUDENT (default) | ADMIN | MODERATOR
 *
 * Custom ID format:  USER1000, USER1001, USER1002 ...
 */
@IdPrefix(prefix = "USER", tableName = "users", columnName = "id")
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    // ----------------------------------------------------------------
    // Primary Key  — custom string ID (USER1000, USER1001 ...)
    // ----------------------------------------------------------------
    @Id
    @GeneratedValue(generator = "user-id-gen")
    @GenericGenerator(name = "user-id-gen", type = CustomIdGenerator.class)
    @Column(name = "id", updatable = false, nullable = false, length = 20)
    private String id;

    // ----------------------------------------------------------------
    // Basic info
    // ----------------------------------------------------------------
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;                        // BCrypt hashed — never store plain text

    @Column(name = "username", unique = true, length = 50)
    private String username;                        // Optional short display handle

    @Column(name = "profile_picture_url", length = 500)
    private String profilePictureUrl;

    @Column(name = "bio", length = 300)
    private String bio;

    // ----------------------------------------------------------------
    // Academic info
    // ----------------------------------------------------------------
    @Column(name = "university", length = 150)
    private String university;

    @Column(name = "branch", length = 100)
    private String branch;                          // e.g. "Computer Science"

    @Column(name = "semester")
    private Integer semester;                       // 1 – 8

    @Column(name = "year_of_study")
    private Integer yearOfStudy;                    // 1 – 4

    @Column(name = "enrollment_number", length = 50)
    private String enrollmentNumber;                // College roll number

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    // ----------------------------------------------------------------
    // Role & account status
    // ----------------------------------------------------------------
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    @Builder.Default
    private Role role = Role.STUDENT;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;                // Disable account without deleting

    @Column(name = "is_email_verified", nullable = false)
    @Builder.Default
    private Boolean isEmailVerified = false;

    @Column(name = "email_verification_token", length = 200)
    private String emailVerificationToken;          // One-time token sent via email

    @Column(name = "password_reset_token", length = 200)
    private String passwordResetToken;

    @Column(name = "password_reset_expiry")
    private LocalDateTime passwordResetExpiry;

    // ----------------------------------------------------------------
    // Gamification / stats
    // ----------------------------------------------------------------
    @Column(name = "reputation_points", nullable = false)
    @Builder.Default
    private Integer reputationPoints = 0;           // +points when answer is accepted/upvoted

    @Column(name = "total_uploads", nullable = false)
    @Builder.Default
    private Integer totalUploads = 0;               // Cached counter

    @Column(name = "total_downloads", nullable = false)
    @Builder.Default
    private Integer totalDownloads = 0;

    // ----------------------------------------------------------------
    // Timestamps
    // ----------------------------------------------------------------
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    // ----------------------------------------------------------------
    // Relationships  (mappedBy = owning side is in child entity)
    // ----------------------------------------------------------------

    /** All resources this user has uploaded */
    @OneToMany(mappedBy = "uploadedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Resource> uploadedResources = new ArrayList<>();

    /** Forum questions posted by this user */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ForumPost> forumPosts = new ArrayList<>();

    /** Forum answers written by this user */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ForumAnswer> forumAnswers = new ArrayList<>();

    /** In-app notifications for this user */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();

    /** Resources bookmarked/saved by this user */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<SavedResource> savedResources = new ArrayList<>();

    /** Ratings given by this user on resources */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ResourceRating> resourceRatings = new ArrayList<>();

    /** Votes cast by this user on forum posts/answers */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ForumVote> forumVotes = new ArrayList<>();
}