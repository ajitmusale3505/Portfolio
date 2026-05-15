package com.edunest.entity;

import com.edunest.util.CustomIdGenerator;
import com.edunest.util.IdPrefix;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

/**
 * A 1–5 star rating left by a student on a resource.
 * One rating allowed per user per resource (enforced via unique constraint).
 *
 * When a rating is saved/updated, ResourceService recomputes
 * Resource.averageRating and Resource.ratingCount.
 *
 * Custom ID format:  RATING1000, RATING1001 ...
 */
@IdPrefix(prefix = "RATING", tableName = "resource_ratings", columnName = "id")
@Entity
@Table(
    name = "resource_ratings",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_rating_user_resource",
            columnNames = { "user_id", "resource_id" }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceRating {

    // ----------------------------------------------------------------
    // Primary Key
    // ----------------------------------------------------------------
    @Id
    @GeneratedValue(generator = "rating-id-gen")
    @GenericGenerator(name = "rating-id-gen", type = CustomIdGenerator.class)
    @Column(name = "id", updatable = false, nullable = false, length = 20)
    private String id;

    // ----------------------------------------------------------------
    // Rating data
    // ----------------------------------------------------------------
    @Column(name = "rating", nullable = false)
    private Integer rating;                         // 1 – 5

    @Column(name = "comment", length = 500)
    private String comment;                         // Optional short review

    // ----------------------------------------------------------------
    // Timestamps
    // ----------------------------------------------------------------
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ----------------------------------------------------------------
    // Relationships — Many-to-One (owning side)
    // ----------------------------------------------------------------

    /** The resource being rated */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false)
    private Resource resource;

    /** The student who left the rating */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}