package com.toptierteam.movemate.entity.users;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_verifications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String emailVerificationToken;
    @Builder.Default
    private boolean isEmailVerified = false;
    private LocalDateTime emailVerifiedAt;

    private String phoneVerificationCode;
    @Builder.Default
    private boolean isPhoneVerified = false;
    private LocalDateTime phoneVerifiedAt;

    private String identityCardNumber;
    private String identityCardFrontImage;
    private String identityCardBackImage;
    @Builder.Default
    private boolean isIdentityVerified = false;
    private LocalDateTime identityVerifiedAt;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}
