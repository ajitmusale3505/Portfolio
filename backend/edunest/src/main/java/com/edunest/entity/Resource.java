 
package com.edunest.entity;

import com.edunest.enums.ResourceType;
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
 * A study material uploaded to EduNest.
 * Covers PYQs, books, notes, practical manuals, LaTeX templates, etc.
 *
 * Workflow:  upload  →  isApproved=false  →  admin approves  →  visible to all students
 *
 * Custom ID format:  RES1000, RES1001, RES1002 ...
 */
@IdPrefix(prefix = "RES", tableName = "resources", columnName = "id")
@Entity
@Table(name = "resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resource {

    // ----------------------------------------------------------------
    // Primary Key
    // ----------------------------------------------------------------
    @Id
    @GeneratedValue(generator = "resource-id-gen")
    @GenericGenerator(name = "resource-id-gen", type = CustomIdGenerator.class)
    @Column(name = "id", updatable = false, nullable = false, length = 20)
    private String id;

    // ----------------------------------------------------------------
    // Content info
    // ----------------------------------------------------------------
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type", nullable = false, length = 50)
    private ResourceType resourceType;

    // ----------------------------------------------------------------
    // File metadata
    // ----------------------------------------------------------------
    @Column(name = "file_url", nullable = false, length = 500)
    private String fileUrl;                         // Local path or S3 URL

    @Column(name = "file_name", length = 255)
    private String fileName;                        // Original uploaded filename

    @Column(name = "file_type", length = 20)
    private String fileType;                        // pdf, zip, tex, java, etc.

    @Column(name = "file_size_bytes")
    private Long fileSizeBytes;

    // ----------------------------------------------------------------
    // Academic filters
    // ----------------------------------------------------------------
    @Column(name = "subject", length = 100)
    private String subject;                         // e.g. "Data Structures"

    @Column(name = "subject_code", length = 50)
    private String subjectCode;                     // e.g. "CS301"

    @Column(name = "branch", length = 100)
    private String branch;

    @Column(name = "semester")
    private Integer semester;

    @Column(name = "year")
    private Integer year;                           // Academic year e.g. 2023

    @Column(name = "university", length = 150)
    private String university;

    @Column(name = "tags", length = 500)
    private String tags;                            // Comma-separated searchable keywords

    // ----------------------------------------------------------------
    // Moderation & visibility
    // ----------------------------------------------------------------
    @Column(name = "is_approved", nullable = false)
    @Builder.Default
    private Boolean isApproved = false;             // Admin must approve before students see it

    @Column(name = "is_featured", nullable = false)
    @Builder.Default
    private Boolean isFeatured = false;             // Admin highlights top resources

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;                // Soft delete

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    // ----------------------------------------------------------------
    // Engagement stats
    // ----------------------------------------------------------------
    @Column(name = "download_count", nullable = false)
    @Builder.Default
    private Integer downloadCount = 0;

    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Integer viewCount = 0;

    @Column(name = "average_rating", nullable = false)
    @Builder.Default
    private Double averageRating = 0.0;             // Computed from ResourceRating rows

    @Column(name = "rating_count", nullable = false)
    @Builder.Default
    private Integer ratingCount = 0;

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

    /** Student or admin who uploaded this resource */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", referencedColumnName = "id")
    private User uploadedBy;

    /** Admin who approved this resource */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by", referencedColumnName = "id")
    private User approvedBy;

    // ----------------------------------------------------------------
    // Relationships — One-to-Many (child side)
    // ----------------------------------------------------------------

    /** All ratings given to this resource */
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ResourceRating> ratings = new ArrayList<>();

    /** All users who have bookmarked this resource */
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<SavedResource> savedByUsers = new ArrayList<>();
}