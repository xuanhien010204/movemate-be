package com.toptierteam.movemate.entity;

import com.toptierteam.movemate.entity.users.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "address_books")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String addressName; // Home, Office, etc.

    @Column(nullable = false)
    private String fullAddress;

    private String district;
    private String city;
    private String country;
    private String postalCode;

    private Double latitude;
    private Double longitude;

    @Builder.Default
    private boolean isDefault = false;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}
