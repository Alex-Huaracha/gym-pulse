package com.gympulse.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "check_ins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "check_in_time", nullable = false)
    private OffsetDateTime checkInTime;

    @Column(name = "is_valid", nullable = false)
    private Boolean isValid;

    @PrePersist
    protected void onCreate() {
        this.checkInTime = OffsetDateTime.now();
    }
}
