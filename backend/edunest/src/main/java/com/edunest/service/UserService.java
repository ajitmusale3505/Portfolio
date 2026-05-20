package com.edunest.service;

import com.edunest.dto.request.ChangePasswordRequest;
import com.edunest.dto.request.UpdateProfileRequest;
import com.edunest.dto.response.UserProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * UserService — declares every operation the UserController needs.
 *
 * Implemented by UserServiceImpl.
 * This interface + impl separation lets you:
 *   - swap implementations easily
 *   - mock the interface in unit tests
 *   - apply Spring AOP (e.g. @Transactional) cleanly on the impl
 */
public interface UserService {

    // ── 1. GET OWN PROFILE ───────────────────────────────────────────
    /**
     * Returns the full profile of the currently logged-in user.
     * @param userId  extracted from JWT token in the controller
     */
    UserProfileResponse getMyProfile(String userId);

    // ── 2. GET ANY USER'S PUBLIC PROFILE ────────────────────────────
    /**
     * Returns public profile data of any user by their ID.
     * Sensitive fields (email, phone, tokens) are hidden for other users.
     * @param userId  path variable from the URL
     */
    UserProfileResponse getUserById(String userId);

    // ── 3. UPDATE OWN PROFILE ────────────────────────────────────────
    /**
     * Updates the editable profile fields of the logged-in user.
     * Only non-null fields in the request are updated (partial update).
     * @param userId   from JWT
     * @param request  fields to update
     */
    UserProfileResponse updateProfile(String userId, UpdateProfileRequest request);

    // ── 4. UPLOAD / CHANGE PROFILE PICTURE ──────────────────────────
    /**
     * Saves the uploaded image, deletes the old one, updates profilePictureUrl.
     * @param userId  from JWT
     * @param file    the image file (jpg/png, validated in service)
     * @return        new profile picture URL
     */
    String uploadProfilePicture(String userId, MultipartFile file);

    // ── 5. DELETE / REMOVE PROFILE PICTURE ──────────────────────────
    /**
     * Removes the current profile picture and sets profilePictureUrl to null.
     * @param userId  from JWT
     */
    void deleteProfilePicture(String userId);

    // ── 6. CHANGE PASSWORD ───────────────────────────────────────────
    /**
     * Verifies current password, then updates to the new password.
     * Throws exception if currentPassword is wrong or newPassword != confirmPassword.
     * @param userId   from JWT
     * @param request  currentPassword + newPassword + confirmPassword
     */
    void changePassword(String userId, ChangePasswordRequest request);

    // ── 7. DEACTIVATE OWN ACCOUNT ────────────────────────────────────
    /**
     * Sets isActive = false on the user's account (soft disable).
     * The account remains in DB but the user cannot log in.
     * @param userId  from JWT
     */
    void deactivateMyAccount(String userId);

    // ── 8. GET MY REPUTATION & STATS ────────────────────────────────
    /**
     * Returns lightweight stats: reputationPoints, totalUploads, totalDownloads.
     * Used for the profile stats widget on the frontend.
     * @param userId  from JWT
     */
    UserProfileResponse getMyStats(String userId);

    // ── 9. SEARCH USERS (Admin) ───────────────────────────────────────
    /**
     * Admin-only: full-text search across name, email, username.
     * @param keyword   search term
     * @param pageable  page + size + sort
     */
    Page<UserProfileResponse> searchUsers(String keyword, Pageable pageable);

    // ── 10. GET ALL USERS (Admin) ────────────────────────────────────
    /**
     * Admin-only: paginated list of all users.
     * @param pageable  page + size + sort
     */
    Page<UserProfileResponse> getAllUsers(Pageable pageable);

    // ── 11. GET USER BY ID (Admin) ───────────────────────────────────
    /**
     * Admin-only: full profile including sensitive fields.
     * @param userId  path variable
     */
    UserProfileResponse getFullUserById(String userId);

    // ── 12. TOGGLE USER ACTIVE STATUS (Admin) ────────────────────────
    /**
     * Admin-only: enable or disable any user account.
     * @param userId    target user
     * @param isActive  true = enable, false = disable
     */
    UserProfileResponse toggleUserStatus(String userId, Boolean isActive);

    // ── 13. CHANGE USER ROLE (Admin) ─────────────────────────────────
    /**
     * Admin-only: promote/demote a user's role.
     * e.g. STUDENT → MODERATOR, MODERATOR → STUDENT
     * @param userId  target user
     * @param role    new role as a string ("STUDENT", "MODERATOR", "ADMIN")
     */
    UserProfileResponse changeUserRole(String userId, String role);

    // ── 14. GET LEADERBOARD ──────────────────────────────────────────
    /**
     * Returns top students ranked by reputationPoints.
     * Visible to all authenticated users.
     * @param pageable  page + size (typically top 10)
     */
    Page<UserProfileResponse> getLeaderboard(Pageable pageable);

    // ── 15. DELETE USER (Admin) ──────────────────────────────────────
    /**
     * Admin-only: permanently deletes a user and all their data.
     * Use deactivate (toggleUserStatus) for soft disable instead.
     * @param userId  target user
     */
    void deleteUser(String userId);
}