package com.edunest.service.impl;

import com.edunest.dto.request.ChangePasswordRequest;

import com.edunest.dto.request.UpdateProfileRequest;
import com.edunest.dto.response.UserProfileResponse;
import com.edunest.enums.Role;
import com.edunest.exception.ResourceNotFoundException;
import com.edunest.exception.BadRequestException;
import com.edunest.entity.User;
import com.edunest.repository.UserRepository;
import com.edunest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    // Upload directory — should be in application.properties as file.upload-dir
    private static final String UPLOAD_DIR         = "./uploads/profiles/";
    private static final long   MAX_FILE_SIZE      = 5 * 1024 * 1024; // 5 MB
    private static final String[] ALLOWED_TYPES    = {"image/jpeg", "image/png", "image/webp"};

    // ═══════════════════════════════════════════════════════════════════
    // 1. GET OWN PROFILE
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile(String userId) {
        log.info("Fetching profile for user: {}", userId);
        User user = findUserById(userId);
        return mapToProfileResponse(user);
    }

    // ═══════════════════════════════════════════════════════════════════
    // 2. GET ANY USER'S PUBLIC PROFILE
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getUserById(String userId) {
        log.info("Fetching public profile for user: {}", userId);
        User user = findUserById(userId);

        // Return public-safe response (hides email, phone for other users)
        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .profilePictureUrl(user.getProfilePictureUrl())
                .bio(user.getBio())
                .university(user.getUniversity())
                .branch(user.getBranch())
                .semester(user.getSemester())
                .role(user.getRole())
                .reputationPoints(user.getReputationPoints())
                .totalUploads(user.getTotalUploads())
                .createdAt(user.getCreatedAt())
                .build();
    }

    // ═══════════════════════════════════════════════════════════════════
    // 3. UPDATE OWN PROFILE
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional
    public UserProfileResponse updateProfile(String userId, UpdateProfileRequest request) {
        log.info("Updating profile for user: {}", userId);
        User user = findUserById(userId);

        // Only update fields that are NOT null in the request (partial update)
        if (request.getName() != null)             user.setName(request.getName());
        if (request.getBio() != null)              user.setBio(request.getBio());
        if (request.getUniversity() != null)       user.setUniversity(request.getUniversity());
        if (request.getBranch() != null)           user.setBranch(request.getBranch());
        if (request.getSemester() != null)         user.setSemester(request.getSemester());
        if (request.getYearOfStudy() != null)      user.setYearOfStudy(request.getYearOfStudy());
        if (request.getEnrollmentNumber() != null) user.setEnrollmentNumber(request.getEnrollmentNumber());
        if (request.getPhoneNumber() != null)      user.setPhoneNumber(request.getPhoneNumber());

        // Username — check uniqueness before updating
        if (request.getUsername() != null) {
            String newUsername = request.getUsername();
            if (!newUsername.equals(user.getUsername())) {
                if (userRepository.existsByUsername(newUsername)) {
                    throw new BadRequestException("Username '" + newUsername + "' is already taken");
                }
                user.setUsername(newUsername);
            }
        }

        User saved = userRepository.save(user);
        log.info("Profile updated successfully for user: {}", userId);
        return mapToProfileResponse(saved);
    }

    // ═══════════════════════════════════════════════════════════════════
    // 4. UPLOAD PROFILE PICTURE
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional
    public String uploadProfilePicture(String userId, MultipartFile file) {
        log.info("Uploading profile picture for user: {}", userId);
        User user = findUserById(userId);

        // Validate file
        validateImageFile(file);

        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Delete old profile picture if exists
            deleteOldProfilePicture(user.getProfilePictureUrl());

            // Generate unique filename: userId_uuid.extension
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String extension        = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename      = userId + "_" + UUID.randomUUID() + extension;

            // Save file to disk
            Path filePath = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Build accessible URL (adjust base URL in production)
            String fileUrl = "/uploads/profiles/" + newFilename;

            // Persist URL in user record
            user.setProfilePictureUrl(fileUrl);
            userRepository.save(user);

            log.info("Profile picture uploaded successfully for user: {}", userId);
            return fileUrl;

        } catch (IOException e) {
            log.error("Failed to upload profile picture for user: {}", userId, e);
            throw new BadRequestException("Failed to upload profile picture. Please try again.");
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    // 5. DELETE PROFILE PICTURE
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional
    public void deleteProfilePicture(String userId) {
        log.info("Deleting profile picture for user: {}", userId);
        User user = findUserById(userId);

        if (user.getProfilePictureUrl() == null) {
            throw new BadRequestException("No profile picture to delete");
        }

        deleteOldProfilePicture(user.getProfilePictureUrl());
        user.setProfilePictureUrl(null);
        userRepository.save(user);
        log.info("Profile picture deleted for user: {}", userId);
    }

    // ═══════════════════════════════════════════════════════════════════
    // 6. CHANGE PASSWORD
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional
    public void changePassword(String userId, ChangePasswordRequest request) {
        log.info("Changing password for user: {}", userId);
        User user = findUserById(userId);

        // 1. Verify current password matches
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }

        // 2. New password and confirm password must match
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("New password and confirm password do not match");
        }

        // 3. New password must not be same as current password
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new BadRequestException("New password cannot be the same as the current password");
        }

        // 4. Hash and save
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        log.info("Password changed successfully for user: {}", userId);
    }

    // ═══════════════════════════════════════════════════════════════════
    // 7. DEACTIVATE OWN ACCOUNT
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional
    public void deactivateMyAccount(String userId) {
        log.info("Deactivating account for user: {}", userId);
        User user = findUserById(userId);
        user.setIsActive(false);
        userRepository.save(user);
        log.info("Account deactivated for user: {}", userId);
    }

    // ═══════════════════════════════════════════════════════════════════
    // 8. GET MY STATS
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getMyStats(String userId) {
        log.info("Fetching stats for user: {}", userId);
        User user = findUserById(userId);

        // Return only stat-relevant fields
        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .reputationPoints(user.getReputationPoints())
                .totalUploads(user.getTotalUploads())
                .totalDownloads(user.getTotalDownloads())
                .build();
    }

    // ═══════════════════════════════════════════════════════════════════
    // 9. SEARCH USERS (Admin)
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional(readOnly = true)
    public Page<UserProfileResponse> searchUsers(String keyword, Pageable pageable) {
        log.info("Admin searching users with keyword: {}", keyword);
        return userRepository.searchUsers(keyword, pageable)
                             .map(this::mapToProfileResponse);
    }

    // ═══════════════════════════════════════════════════════════════════
    // 10. GET ALL USERS (Admin)
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional(readOnly = true)
    public Page<UserProfileResponse> getAllUsers(Pageable pageable) {
        log.info("Admin fetching all users");
        return userRepository.findAll(pageable)
                             .map(this::mapToProfileResponse);
    }

    // ═══════════════════════════════════════════════════════════════════
    // 11. GET FULL USER BY ID (Admin)
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getFullUserById(String userId) {
        log.info("Admin fetching full profile for user: {}", userId);
        User user = findUserById(userId);
        return mapToProfileResponse(user);  // Full response including email, phone, etc.
    }

    // ═══════════════════════════════════════════════════════════════════
    // 12. TOGGLE USER ACTIVE STATUS (Admin)
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional
    public UserProfileResponse toggleUserStatus(String userId, Boolean isActive) {
        log.info("Admin toggling status for user: {} → isActive={}", userId, isActive);
        User user = findUserById(userId);
        user.setIsActive(isActive);
        User saved = userRepository.save(user);
        log.info("User {} status updated to: {}", userId, isActive);
        return mapToProfileResponse(saved);
    }

    // ═══════════════════════════════════════════════════════════════════
    // 13. CHANGE USER ROLE (Admin)
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional
    public UserProfileResponse changeUserRole(String userId, String roleName) {
        log.info("Admin changing role for user: {} → {}", userId, roleName);
        User user = findUserById(userId);

        Role newRole;
        try {
            newRole = Role.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role: " + roleName + ". Valid values: STUDENT, MODERATOR, ADMIN");
        }

        user.setRole(newRole);
        User saved = userRepository.save(user);
        log.info("Role updated to {} for user: {}", newRole, userId);
        return mapToProfileResponse(saved);
    }

    // ═══════════════════════════════════════════════════════════════════
    // 14. GET LEADERBOARD
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional(readOnly = true)
    public Page<UserProfileResponse> getLeaderboard(Pageable pageable) {
        log.info("Fetching leaderboard");
        return userRepository.findTopStudentsByReputation(pageable)
                             .map(user -> UserProfileResponse.builder()
                                     .id(user.getId())
                                     .name(user.getName())
                                     .username(user.getUsername())
                                     .profilePictureUrl(user.getProfilePictureUrl())
                                     .university(user.getUniversity())
                                     .branch(user.getBranch())
                                     .reputationPoints(user.getReputationPoints())
                                     .totalUploads(user.getTotalUploads())
                                     .build()
                             );
    }

    // ═══════════════════════════════════════════════════════════════════
    // 15. DELETE USER (Admin)
    // ═══════════════════════════════════════════════════════════════════
    @Override
    @Transactional
    public void deleteUser(String userId) {
        log.info("Admin deleting user: {}", userId);
        User user = findUserById(userId);

        // Delete profile picture from disk before removing user
        if (user.getProfilePictureUrl() != null) {
            deleteOldProfilePicture(user.getProfilePictureUrl());
        }

        userRepository.delete(user);
        log.info("User {} permanently deleted", userId);
    }

    // ═══════════════════════════════════════════════════════════════════
    // PRIVATE HELPERS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Centralized user lookup — throws ResourceNotFoundException if not found.
     */
    private User findUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    /**
     * Maps a User entity to UserProfileResponse DTO.
     * Single place to change mapping logic.
     */
    private UserProfileResponse mapToProfileResponse(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .username(user.getUsername())
                .profilePictureUrl(user.getProfilePictureUrl())
                .bio(user.getBio())
                .university(user.getUniversity())
                .branch(user.getBranch())
                .semester(user.getSemester())
                .yearOfStudy(user.getYearOfStudy())
                .enrollmentNumber(user.getEnrollmentNumber())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .isEmailVerified(user.getIsEmailVerified())
                .reputationPoints(user.getReputationPoints())
                .totalUploads(user.getTotalUploads())
                .totalDownloads(user.getTotalDownloads())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

    /**
     * Validates uploaded image file — type, size, null check.
     */
    private void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Please select an image file to upload");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("File size exceeds the maximum limit of 5 MB");
        }
        String contentType = file.getContentType();
        boolean validType = false;
        for (String allowed : ALLOWED_TYPES) {
            if (allowed.equals(contentType)) {
                validType = true;
                break;
            }
        }
        if (!validType) {
            throw new BadRequestException("Only JPEG, PNG and WEBP images are allowed");
        }
    }

    /**
     * Deletes an old profile picture file from disk.
     * Silently skips if file does not exist.
     */
    private void deleteOldProfilePicture(String profilePictureUrl) {
        if (profilePictureUrl == null) return;
        try {
            // Convert URL path  (/uploads/profiles/filename.jpg)  to  filesystem path
            String filename = profilePictureUrl.replace("/uploads/profiles/", "");
            Path oldFile    = Paths.get(UPLOAD_DIR + filename);
            Files.deleteIfExists(oldFile);
        } catch (IOException e) {
            log.warn("Could not delete old profile picture: {}", profilePictureUrl, e);
            // Non-fatal — log and continue
        }
    }
}