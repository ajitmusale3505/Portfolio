package com.edunest.entity;

 
import com.edunest.enums.SubjectType;
import com.edunest.util.CustomIdGenerator;
import com.edunest.util.IdPrefix;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * One subject entry in the university curriculum.
 * Multiple entries per (university + branch + semester) form a full syllabus.
 *
 * The `topics` field stores the list of topics for the subject.
 * Recommended format: JSON array string, e.g.:
 *   ["Arrays", "Linked Lists", "Stacks", "Queues", "Trees", "Graphs"]
 *
 * Custom ID format:  CUR1000, CUR1001 ...
 */
@IdPrefix(prefix = "CUR", tableName = "curriculum", columnName = "id")
@Entity
@Table(
    name = "curriculum",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_curriculum_subject",
            columnNames = { "university", "branch", "semester", "subject_code" }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curriculum {

    // ----------------------------------------------------------------
    // Primary Key
    // ----------------------------------------------------------------
    @Id
    @GeneratedValue(generator = "curriculum-id-gen")
    @GenericGenerator(name = "curriculum-id-gen", type = CustomIdGenerator.class)
    @Column(name = "id", updatable = false, nullable = false, length = 20)
    private String id;

    // ----------------------------------------------------------------
    // Academic identifiers
    // ----------------------------------------------------------------
    @Column(name = "university", nullable = false, length = 150)
    private String university;

    @Column(name = "branch", nullable = false, length = 100)
    private String branch;

    @Column(name = "semester", nullable = false)
    private Integer semester;                       // 1 – 8

    // ----------------------------------------------------------------
    // Subject details
    // ----------------------------------------------------------------
    @Column(name = "subject_name", nullable = false, length = 150)
    private String subjectName;

    @Column(name = "subject_code", length = 50)
    private String subjectCode;                     // e.g. "CS301"

    @Column(name = "topics", columnDefinition = "TEXT")
    private String topics;
    // Recommended: store as JSON array string — ["Topic 1", "Topic 2", ...]
    // Parse in service layer with ObjectMapper if needed.

    @Column(name = "credits")
    private Integer credits;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_type", length = 20)
    private SubjectType subjectType;                // THEORY / LAB / ELECTIVE / PROJECT

    // ----------------------------------------------------------------
    // Visibility
    // ----------------------------------------------------------------
    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;                // Soft delete

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

    /** Admin who added this curriculum entry */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;
}