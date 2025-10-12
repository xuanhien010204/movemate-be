package com.toptierteam.movemate.entity;

import com.toptierteam.movemate.entity.users.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String type; // TRIP_UPDATE, PAYMENT, PROMOTION, SYSTEM
    private String referenceId; // Trip ID, Payment ID, etc.

    @Builder.Default
    private boolean isRead = false;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime readAt;
}
