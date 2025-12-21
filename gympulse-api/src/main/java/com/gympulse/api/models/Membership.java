package com.gympulse.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "memberships")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne // Many membership records can belong to a Member
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne // Many records can use the same Plan (e.g., Monthly)
    @JoinColumn(name = "plan_id")
    private MembershipPlan plan;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_paid")
    private Boolean isPaid = false;
}
