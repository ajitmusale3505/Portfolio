package com.edunest.entity;

 

import com.edunest.enums.VoteTargetType;
import com.edunest.enums.VoteType;
import com.edunest.util.CustomIdGenerator;
import com.edunest.util.IdPrefix;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

/**
 * Records every vote a user casts on a forum post or answer.
 *
 * Prevents double-voting: unique constraint on (user_id, target_id, target_type).
 * A user can change UPVOTE → DOWNVOTE by updating the existing row (same unique key).
 *
 * When a vote is saved, ForumService updates the upvotes/downvotes counters
 * on the relevant ForumPost or ForumAnswer.
 *
 * Custom ID format:  VOTE1000, VOTE1001 ...
 */
@IdPrefix(prefix = "VOTE", tableName = "forum_votes", columnName = "id")
@Entity
@Table(
    name = "forum_votes",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_vote_user_target",
            columnNames = { "user_id", "target_id", "target_type" }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForumVote {

    // ----------------------------------------------------------------
    // Primary Key
    // ----------------------------------------------------------------
    @Id
    @GeneratedValue(generator = "vote-id-gen")
    @GenericGenerator(name = "vote-id-gen", type = CustomIdGenerator.class)
    @Column(name = "id", updatable = false, nullable = false, length = 20)
    private String id;

    // ----------------------------------------------------------------
    // Vote data
    // ----------------------------------------------------------------

    /** ID of the ForumPost or ForumAnswer being voted on */
    @Column(name = "target_id", nullable = false, length = 20)
    private String targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 10)
    private VoteTargetType targetType;              // POST or ANSWER

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type", nullable = false, length = 10)
    private VoteType voteType;                      // UPVOTE or DOWNVOTE

    // ----------------------------------------------------------------
    // Timestamps
    // ----------------------------------------------------------------
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ----------------------------------------------------------------
    // Relationships — Many-to-One (owning side)
    // ----------------------------------------------------------------

    /** The student who cast this vote */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}