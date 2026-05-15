package com.edunest.entity;


import com.edunest.util.CustomIdGenerator;
import com.edunest.util.IdPrefix;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

/**
 * A bookmark — records that a student has saved a resource for later.
 * One save allowed per user per resource (unique constraint).
 *
 * Custom ID format:  SAVED1000, SAVED1001 ...
 */
@IdPrefix(prefix = "SAVED", tableName = "saved_resources", columnName = "id")
@Entity
@Table(
    name = "saved_resources",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_saved_user_resource",
            columnNames = { "user_id", "resource_id" }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedResource {

    // ----------------------------------------------------------------
    // Primary Key
    // ----------------------------------------------------------------
    @Id
    @GeneratedValue(generator = "saved-id-gen")
    @GenericGenerator(name = "saved-id-gen", type = CustomIdGenerator.class)
    @Column(name = "id", updatable = false, nullable = false, length = 20)
    private String id;

    // ----------------------------------------------------------------
    // Timestamps
    // ----------------------------------------------------------------
    @CreationTimestamp
    @Column(name = "saved_at", updatable = false)
    private LocalDateTime savedAt;

    // ----------------------------------------------------------------
    // Relationships — Many-to-One (owning side)
    // ----------------------------------------------------------------

    /** The student who bookmarked the resource */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    /** The bookmarked resource */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false)
    private Resource resource;
}