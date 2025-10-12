package com.toptierteam.movemate.entity;

import com.toptierteam.movemate.entity.trips.Trip;
import com.toptierteam.movemate.entity.users.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id")
    private User reviewee;

    @Column(nullable = false)
    private Integer rating; // 1-5 stars

    @Column(columnDefinition = "TEXT")
    private String comment;

    private String reviewType; // CUSTOMER_TO_DRIVER, DRIVER_TO_CUSTOMER

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
