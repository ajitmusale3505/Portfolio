package com.edunest.entity;

import com.edunest.util.CustomIdGenerator;
import com.edunest.util.IdPrefix;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * An answer submitted by a student to a ForumPost.
 *
 * The post author can mark one answer as accepted (isAccepted = true).
 * When an answer is accepted:
 *   - ForumPost.status  →  ANSWERED
 *   - ForumPost.isResolved  →  true
 *   - Answer author's User.reputationPoints  += 10
 *
 * Custom ID format:  ANS1000, ANS1001 ...
 */
@IdPrefix(prefix = "ANS", tableName = "forum_answers", columnName = "id")
@Entity
@Table(name = "forum_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForumAnswer {

    // ----------------------------------------------------------------
    // Primary Key
    // ----------------------------------------------------------------
    @Id
    @GeneratedValue(generator = "answer-id-gen")
    @GenericGenerator(name = "answer-id-gen", type = CustomIdGenerator.class)
    @Column(name = "id", updatable = false, nullable = false, length = 20)
    private String id;

    // ----------------------------------------------------------------
    // Content
    // ----------------------------------------------------------------
    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    // ----------------------------------------------------------------
    // Status flags
    // ----------------------------------------------------------------
    @Column(name = "is_accepted", nullable = false)
    @Builder.Default
    private Boolean isAccepted = false;             // Only the post author can accept

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;                // Soft delete

    // ----------------------------------------------------------------
    // Engagement stats
    // ----------------------------------------------------------------
    @Column(name = "upvotes", nullable = false)
    @Builder.Default
    private Integer upvotes = 0;

    @Column(name = "downvotes", nullable = false)
    @Builder.Default
    private Integer downvotes = 0;

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

    /** The forum post this answer belongs to.
     *  CascadeType is managed by ForumPost (orphanRemoval = true there). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private ForumPost post;

    /** Student who wrote this answer */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}