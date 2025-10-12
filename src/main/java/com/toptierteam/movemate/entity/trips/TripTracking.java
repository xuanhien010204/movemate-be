package com.toptierteam.movemate.entity.trips;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trip_trackings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TripTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    private Double latitude;
    private Double longitude;
    private Double speed; // km/h
    private Double heading; // degrees

    private LocalDateTime recordedAt;
}
