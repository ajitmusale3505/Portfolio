package com.edunest.controller;

import com.edunest.dto.request.ChangePasswordRequest;

import com.edunest.dto.request.UpdateProfileRequest;
import com.edunest.dto.response.ApiResponse;
import com.edunest.dto.response.UserProfileResponse;
import com.edunest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * UserController — handles all /api/users/** requests.
 *
 * ┌─────────────────────────────────────────────────────────────────────┐
 * │  Route                              │ Role         │ Description     │
 * ├─────────────────────────────────────────────────────────────────────┤
 * │  GET    /api/users/me               │ Any Auth     │ Own profile      │
 * │  PATCH  /api/users/me               │ Any Auth     │ Update profile   │
 * │  POST   /api/users/me/picture       │ Any Auth     │ Upload picture   │
 * │  DELETE /api/users/me/picture       │ Any Auth     │ Remove picture   │
 * │  POST   /api/users/me/password      │ Any Auth     │ Change password  │
 * │  DELETE /api/users/me               │ Any Auth     │ Deactivate self  │
 * │  GET    /api/users/me/stats         │ Any Auth     │ My stats         │
 * │  GET    /api/users/{id}             │ Any Auth     │ Public profile   │
 * │  GET    /api/users/leaderboard      │ Any Auth     │ Top students     │
 * │  GET    /api/users                  │ ADMIN        │ All users        │
 * │  GET    /api/users/search           │ ADMIN        │ Search users     │
 * │  GET    /api/users/admin/{id}       │ ADMIN        │ Full profile     │
 * │  PATCH  /api/users/{id}/status      │ ADMIN        │ Enable/disable   │
 * │  PATCH  /api/users/{id}/role        │ ADMIN        │ Change role      │
 * │  DELETE /api/users/{id}             │ ADMIN        │ Delete user      │
 * └─────────────────────────────────────────────────────────────────────┘
 *
 * How to get logged-in userId:
 *   @AuthenticationPrincipal UserDetails userDetails
 *   String userId = userDetails.getUsername();
 *   (JwtAuthFilter puts the userId as the "username" in UserDetails)
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class Usercontroller {

    private final UserService userService;

    // ═══════════════════════════════════════════════════════════════════
    // STUDENT / OWN PROFILE ENDPOINTS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * GET /api/users/me
     * Returns the full profile of the currently logged-in user.
     *
     * Frontend use: render the "My Profile" page.
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails) {

        String userId = userDetails.getUsername();
        log.info("GET /api/users/me  →  userId={}", userId);

        UserProfileResponse profile = userService.getMyProfile(userId);
        return ResponseEntity.ok(ApiResponse.success("Profile fetched successfully", profile));
    }

    /**
     * PATCH /api/users/me
     * Updates the logged-in user's editable profile fields.
     * Only non-null fields in the request body are updated.
     *
     * Request body example:
     * {
     *   "name": "Rahul Sharma",
     *   "bio": "CS student @ MIT Pune",
     *   "semester": 5,
     *   "branch": "Computer Science"
     * }
     */
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateMyProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdateProfileRequest request) {

        String userId = userDetails.getUsername();
        log.info("PATCH /api/users/me  →  userId={}", userId);

        UserProfileResponse updated = userService.updateProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", updated));
    }

    /**
     * POST /api/users/me/picture
     * Uploads a new profile picture (multipart/form-data).
     * Replaces the old picture automatically.
     *
     * Form field name: "file"
     * Allowed types:   JPEG, PNG, WEBP
     * Max size:        5 MB
     */
    @PostMapping(value = "/me/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadProfilePicture(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile file) {

        String userId = userDetails.getUsername();
        log.info("POST /api/users/me/picture  →  userId={}", userId);

        String imageUrl = userService.uploadProfilePicture(userId, file);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Profile picture uploaded successfully", imageUrl));
    }

    /**
     * DELETE /api/users/me/picture
     * Removes the current profile picture.
     * Sets profilePictureUrl to null in DB and deletes file from disk.
     */
    @DeleteMapping("/me/picture")
    public ResponseEntity<ApiResponse<Void>> deleteProfilePicture(
            @AuthenticationPrincipal UserDetails userDetails) {

        String userId = userDetails.getUsername();
        log.info("DELETE /api/users/me/picture  →  userId={}", userId);

        userService.deleteProfilePicture(userId);
        return ResponseEntity.ok(ApiResponse.success("Profile picture removed successfully"));
    }

    /**
     * POST /api/users/me/password
     * Changes the logged-in user's password.
     *
     * Request body:
     * {
     *   "currentPassword": "oldPass123",
     *   "newPassword":     "newPass456",
     *   "confirmPassword": "newPass456"
     * }
     */
    @PostMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ChangePasswordRequest request) {

        String userId = userDetails.getUsername();
        log.info("POST /api/users/me/password  →  userId={}", userId);

        userService.changePassword(userId, request);
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully"));
    }

    /**
     * DELETE /api/users/me
     * Deactivates (soft-disables) the logged-in user's own account.
     * Sets isActive = false. The account remains in DB but cannot log in.
     */
    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deactivateMyAccount(
            @AuthenticationPrincipal UserDetails userDetails) {

        String userId = userDetails.getUsername();
        log.info("DELETE /api/users/me (deactivate)  →  userId={}", userId);

        userService.deactivateMyAccount(userId);
        return ResponseEntity.ok(ApiResponse.success("Your account has been deactivated"));
    }

    /**
     * GET /api/users/me/stats
     * Returns lightweight stats for the profile stats widget.
     * Response: reputationPoints, totalUploads, totalDownloads
     */
    @GetMapping("/me/stats")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyStats(
            @AuthenticationPrincipal UserDetails userDetails) {

        String userId = userDetails.getUsername();
        log.info("GET /api/users/me/stats  →  userId={}", userId);

        UserProfileResponse stats = userService.getMyStats(userId);
        return ResponseEntity.ok(ApiResponse.success("Stats fetched successfully", stats));
    }

    // ═══════════════════════════════════════════════════════════════════
    // PUBLIC ENDPOINTS (any authenticated user)
    // ═══════════════════════════════════════════════════════════════════

    /**
     * GET /api/users/{userId}
     * Returns the public profile of any user.
     * Sensitive fields (email, phone) are hidden.
     *
     * Frontend use: visit another student's profile page.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserById(
            @PathVariable String userId) {

        log.info("GET /api/users/{}  (public profile)", userId);

        UserProfileResponse profile = userService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.success("User profile fetched successfully", profile));
    }

    /**
     * GET /api/users/leaderboard?page=0&size=10
     * Returns top students ranked by reputation points.
     *
     * Query params:
     *   page  (default 0)
     *   size  (default 10)
     */
    @GetMapping("/leaderboard")
    public ResponseEntity<ApiResponse<Page<UserProfileResponse>>> getLeaderboard(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("GET /api/users/leaderboard  page={} size={}", page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<UserProfileResponse> leaderboard = userService.getLeaderboard(pageable);
        return ResponseEntity.ok(ApiResponse.success("Leaderboard fetched successfully", leaderboard));
    }

    // ═══════════════════════════════════════════════════════════════════
    // ADMIN-ONLY ENDPOINTS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * GET /api/users?page=0&size=10&sort=createdAt,desc
     * Admin: paginated list of all registered users.
     *
     * Query params:
     *   page  (default 0)
     *   size  (default 10)
     *   sort  (default createdAt,desc)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<UserProfileResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0")          int    page,
            @RequestParam(defaultValue = "10")         int    size,
            @RequestParam(defaultValue = "createdAt")  String sortBy,
            @RequestParam(defaultValue = "desc")       String sortDir) {

        log.info("GET /api/users (admin)  page={} size={}", page, size);

        Sort sort     = sortDir.equalsIgnoreCase("asc")
                        ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UserProfileResponse> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", users));
    }

    /**
     * GET /api/users/search?keyword=rahul&page=0&size=10
     * Admin: search users by name, email, or username.
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<UserProfileResponse>>> searchUsers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("GET /api/users/search  keyword={}", keyword);

        Pageable pageable = PageRequest.of(page, size);
        Page<UserProfileResponse> results = userService.searchUsers(keyword, pageable);
        return ResponseEntity.ok(ApiResponse.success("Search results fetched", results));
    }

    /**
     * GET /api/users/admin/{userId}
     * Admin: fetch FULL profile of any user (includes email, phone, tokens status, etc.)
     */
    @GetMapping("/admin/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getFullUserById(
            @PathVariable String userId) {

        log.info("GET /api/users/admin/{}  (admin full profile)", userId);

        UserProfileResponse profile = userService.getFullUserById(userId);
        return ResponseEntity.ok(ApiResponse.success("Full user profile fetched", profile));
    }

    /**
     * PATCH /api/users/{userId}/status?isActive=false
     * Admin: enable or disable any user account.
     *
     * Query param:
     *   isActive = true  → enable account
     *   isActive = false → disable account
     */
    @PatchMapping("/{userId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserProfileResponse>> toggleUserStatus(
            @PathVariable String  userId,
            @RequestParam Boolean isActive) {

        log.info("PATCH /api/users/{}/status  isActive={}", userId, isActive);

        UserProfileResponse updated = userService.toggleUserStatus(userId, isActive);
        String msg = Boolean.TRUE.equals(isActive) ? "User account enabled" : "User account disabled";
        return ResponseEntity.ok(ApiResponse.success(msg, updated));
    }

    /**
     * PATCH /api/users/{userId}/role?role=MODERATOR
     * Admin: change the role of any user.
     *
     * Query param:
     *   role = STUDENT | MODERATOR | ADMIN
     */
    @PatchMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserProfileResponse>> changeUserRole(
            @PathVariable String userId,
            @RequestParam String role) {

        log.info("PATCH /api/users/{}/role  role={}", userId, role);

        UserProfileResponse updated = userService.changeUserRole(userId, role);
        return ResponseEntity.ok(ApiResponse.success("User role updated to " + role.toUpperCase(), updated));
    }

    /**
     * DELETE /api/users/{userId}
     * Admin: permanently delete a user and all their data.
     * WARNING: This is irreversible. Prefer disabling via /status endpoint.
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable String userId) {

        log.info("DELETE /api/users/{}  (admin permanent delete)", userId);

        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User permanently deleted"));
    }
}