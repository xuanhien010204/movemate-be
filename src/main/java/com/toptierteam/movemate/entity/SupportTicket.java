package com.toptierteam.movemate.entity;

import com.toptierteam.movemate.entity.users.User;
import com.toptierteam.movemate.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "support_tickets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SupportTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category; // TECHNICAL, BILLING, GENERAL, COMPLAINT
    private String priority; // LOW, MEDIUM, HIGH, CRITICAL

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo; // Staff member

    @OneToMany(mappedBy = "supportTicket", cascade = CascadeType.ALL)
    @Builder.Default
    private List<SupportMessage> messages = new ArrayList<>();

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
    private LocalDateTime resolvedAt;
}
