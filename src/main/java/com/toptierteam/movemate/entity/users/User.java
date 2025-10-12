package com.toptierteam.movemate.entity.users;
import com.toptierteam.movemate.entity.AddressBook;
import com.toptierteam.movemate.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String fullName; // Fixed typo from fulName

    @Column(unique = true, nullable = false)
    private String email;

    private String phoneNumber;
    private String address;

    @Column(nullable = false)
    private String password; // Changed from passwordHash to password

    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private RoleType role; // Added role field

    @Builder.Default
    private boolean active = true; // Added active field

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now(); // Added updatedAt field

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<UserRole> roles = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private DriverProfile driverProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<AddressBook> addresses = new ArrayList<>();
}
