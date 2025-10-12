package com.toptierteam.movemate.entity;

import com.toptierteam.movemate.entity.users.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "support_messages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SupportMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "support_ticket_id")
    private SupportTicket supportTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    private String messageType; // TEXT, IMAGE, FILE
    private String attachmentUrl;

    @Builder.Default
    private boolean isFromCustomer = true;
    @Builder.Default
    private boolean isRead = false;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime readAt;
}
