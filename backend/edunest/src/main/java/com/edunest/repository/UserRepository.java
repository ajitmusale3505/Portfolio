package com.edunest.repository;

import com.edunest.enums.Role;

import com.edunest.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // ── Lookup by unique fields ───────────────────────────────────────

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailVerificationToken(String token);

    Optional<User> findByPasswordResetToken(String token);

    // ── Existence checks (used in validation) ────────────────────────

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    // ── Admin: list / filter users ───────────────────────────────────

    Page<User> findByRole(Role role, Pageable pageable);

    Page<User> findByIsActive(Boolean isActive, Pageable pageable);

    Page<User> findByUniversityAndBranch(String university, String branch, Pageable pageable);

    // ── Search users by name or email (admin search bar) ─────────────

    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<User> searchUsers(@Param("keyword") String keyword, Pageable pageable);

    // ── Leaderboard — top students by reputation ─────────────────────

    @Query("SELECT u FROM User u WHERE u.role = 'STUDENT' AND u.isActive = true " +
           "ORDER BY u.reputationPoints DESC")
    Page<User> findTopStudentsByReputation(Pageable pageable);

    // ── Increment / decrement cached counters ────────────────────────

    @Modifying
    @Query("UPDATE User u SET u.totalUploads = u.totalUploads + 1 WHERE u.id = :userId")
    void incrementTotalUploads(@Param("userId") String userId);

    @Modifying
    @Query("UPDATE User u SET u.totalUploads = u.totalUploads - 1 WHERE u.id = :userId AND u.totalUploads > 0")
    void decrementTotalUploads(@Param("userId") String userId);

    @Modifying
    @Query("UPDATE User u SET u.totalDownloads = u.totalDownloads + 1 WHERE u.id = :userId")
    void incrementTotalDownloads(@Param("userId") String userId);

    @Modifying
    @Query("UPDATE User u SET u.reputationPoints = u.reputationPoints + :points WHERE u.id = :userId")
    void addReputationPoints(@Param("userId") String userId, @Param("points") int points);

    @Modifying
    @Query("UPDATE User u SET u.lastLoginAt = :time WHERE u.id = :userId")
    void updateLastLoginAt(@Param("userId") String userId, @Param("time") LocalDateTime time);

    // ── Expire stale password-reset tokens ───────────────────────────

    @Query("SELECT u FROM User u WHERE u.passwordResetExpiry < :now AND u.passwordResetToken IS NOT NULL")
    java.util.List<User> findUsersWithExpiredResetTokens(@Param("now") LocalDateTime now);
}