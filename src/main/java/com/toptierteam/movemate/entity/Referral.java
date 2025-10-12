package com.toptierteam.movemate.entity;

import com.toptierteam.movemate.entity.users.User;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "referrals")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Referral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referrer_id")
    private User referrer; // Referrer user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referred_id")
    private User referred; // referred user

    @Column(unique = true, nullable = false)
    private String referralCode;

    private BigDecimal referrerReward; // Referral Rewards
    private BigDecimal referredReward; // Referral Rewards

    @Builder.Default
    private boolean isReferrerRewarded = false;
    @Builder.Default
    private boolean isReferredRewarded = false;

    private LocalDateTime rewardedAt;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
