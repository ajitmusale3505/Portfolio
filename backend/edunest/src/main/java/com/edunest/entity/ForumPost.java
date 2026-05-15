package com.edunest.entity;
 
import com.edunest.enums.PostStatus;
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
 * A doubt/question posted to the EduNest community forum.
 *
 * Lifecycle:  OPEN  →  ANSWERED (first accepted answer)  →  CLOSED (manually by author/admin)
 *
 * Custom ID format:  POST1000, POST1001 ...
 */
@IdPrefix(prefix = "POST", tableName = "forum_posts", columnName = "id")
@Entity
@Table(name = "forum_posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForumPost {

    // ----------------------------------------------------------------
    // Primary Key
    // ----------------------------------------------------------------
    @Id
    @GeneratedValue(generator = "post-id-gen")
    @GenericGenerator(name = "post-id-gen", type = CustomIdGenerator.class)
    @Column(name = "id", updatable = false, nullable = false, length = 20)
    private String id;

    // ----------------------------------------------------------------
    // Content
    // ----------------------------------------------------------------
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    // ----------------------------------------------------------------
    // Academic filters / tags
    // ----------------------------------------------------------------
    @Column(name = "subject", length = 100)
    private String subject;

    @Column(name = "subject_code", length = 50)
    private String subjectCode;

    @Column(name = "branch", length = 100)
    private String branch;

    @Column(name = "semester")
    private Integer semester;

    @Column(name = "university", length = 150)
    private String university;

    @Column(name = "tags", length = 500)
    private String tags;                            // Comma-separated

    // ----------------------------------------------------------------
    // Status & moderation
    // ----------------------------------------------------------------
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private PostStatus status = PostStatus.OPEN;

    @Column(name = "is_resolved", nullable = false)
    @Builder.Default
    private Boolean isResolved = false;

    @Column(name = "is_pinned", nullable = false)
    @Builder.Default
    private Boolean isPinned = false;               // Admin can pin important discussions

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;                // Soft delete

    // ----------------------------------------------------------------
    // Engagement stats (cached to avoid heavy JOIN queries)
    // ----------------------------------------------------------------
    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Integer viewCount = 0;

    @Column(name = "answer_count", nullable = false)
    @Builder.Default
    private Integer answerCount = 0;                // Incremented by ForumService on new answer

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

    /** Student who posted the question */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    // ----------------------------------------------------------------
    // Relationships — One-to-Many
    // ----------------------------------------------------------------

    /** All answers submitted to this post.
     *  ON DELETE CASCADE — deleting a post removes all its answers. */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ForumAnswer> answers = new ArrayList<>();
}