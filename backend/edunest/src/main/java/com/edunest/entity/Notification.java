package com.edunest.entity;


import com.edunest.enums.NotificationType;
import com.edunest.util.CustomIdGenerator;
import com.edunest.util.IdPrefix;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

/**
 * An in-app notification shown in the bell-icon dropdown.
 *
 * Created automatically by NotificationService when:
 *   - Someone answers your forum post        → ANSWER_RECEIVED
 *   - Your answer is accepted                → ANSWER_ACCEPTED
 *   - A new university update is posted      → UPDATE_POSTED
 *   - Your uploaded resource is approved     → RESOURCE_APPROVED
 *   - Someone replies in a thread you follow → FORUM_REPLY
 *
 * Custom ID format:  NOTIF1000, NOTIF1001 ...
 */
@IdPrefix(prefix = "NOTIF", tableName = "notifications", columnName = "id")
@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    // ----------------------------------------------------------------
    // Primary Key
    // ----------------------------------------------------------------
    @Id
    @GeneratedValue(generator = "notif-id-gen")
    @GenericGenerator(name = "notif-id-gen", type = CustomIdGenerator.class)
    @Column(name = "id", updatable = false, nullable = false, length = 20)
    private String id;

    // ----------------------------------------------------------------
    // Content
    // ----------------------------------------------------------------
    @Column(name = "message", nullable = false, length = 500)
    private String message;                         // Human-readable notification text

    @Column(name = "link", length = 500)
    private String link;                            // Frontend URL to navigate to on click
    // e.g.  /forum/POST1003  or  /resources/RES1010

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 30)
    private NotificationType type;

    // ----------------------------------------------------------------
    // Read status
    // ----------------------------------------------------------------
    @Column(name = "is_read", nullable = false)
    @Builder.Default
    private Boolean isRead = false;

    // ----------------------------------------------------------------
    // Timestamps
    // ----------------------------------------------------------------
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ----------------------------------------------------------------
    // Relationships — Many-to-One (owning side)
    // ----------------------------------------------------------------

    /** The student who should receive this notification */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}