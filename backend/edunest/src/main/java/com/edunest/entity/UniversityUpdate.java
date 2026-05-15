package com.edunest.entity;


import com.edunest.enums.UpdateCategory;
import com.edunest.util.CustomIdGenerator;
import com.edunest.util.IdPrefix;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * An announcement posted by an admin — exam schedules, results,
 * holidays, placement drives, events, etc.
 *
 * Targeting rules:
 *   university = null  →  shown to ALL universities
 *   branch     = null  →  shown to ALL branches
 *   semester   = null  →  shown to ALL semesters
 *
 * Custom ID format:  UPD1000, UPD1001 ...
 */
@IdPrefix(prefix = "UPD", tableName = "university_updates", columnName = "id")
@Entity
@Table(name = "university_updates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniversityUpdate {

    // ----------------------------------------------------------------
    // Primary Key
    // ----------------------------------------------------------------
    @Id
    @GeneratedValue(generator = "update-id-gen")
    @GenericGenerator(name = "update-id-gen", type = CustomIdGenerator.class)
    @Column(name = "id", updatable = false, nullable = false, length = 20)
    private String id;

    // ----------------------------------------------------------------
    // Content
    // ----------------------------------------------------------------
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "attachment_url", length = 500)
    private String attachmentUrl;                   // Optional PDF / image attachment

    // ----------------------------------------------------------------
    // Categorisation & targeting
    // ----------------------------------------------------------------
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 30)
    private UpdateCategory category;

    @Column(name = "university", length = 150)
    private String university;                      // null = all universities

    @Column(name = "branch", length = 100)
    private String branch;                          // null = all branches

    @Column(name = "semester")
    private Integer semester;                       // null = all semesters

    @Column(name = "event_date")
    private LocalDateTime eventDate;                // For EXAM / EVENT category updates

    // ----------------------------------------------------------------
    // Visibility flags
    // ----------------------------------------------------------------
    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;                // Soft delete / hide without deleting

    @Column(name = "is_pinned", nullable = false)
    @Builder.Default
    private Boolean isPinned = false;               // Pinned to top of updates feed

    // ----------------------------------------------------------------
    // Timestamps
    // ----------------------------------------------------------------
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ----------------------------------------------------------------
    // Relationships — Many-to-One (owning side)
    // ----------------------------------------------------------------

    /** Admin who posted this update */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by", referencedColumnName = "id", nullable = false)
    private User postedBy;
}