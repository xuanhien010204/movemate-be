package com.toptierteam.movemate.entity.users;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_devices")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String deviceToken;

    private String deviceType; // iOS, Android, Web
    private String deviceModel;
    private String appVersion;

    @Builder.Default
    private boolean isActive = true;
    @Builder.Default
    private LocalDateTime lastLoginAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}
