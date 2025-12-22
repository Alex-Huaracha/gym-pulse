package com.gympulse.api.repositories;

import com.gympulse.api.models.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Integer> {
    // Find a membership plan by its name
    boolean existsByName(String name);
}
