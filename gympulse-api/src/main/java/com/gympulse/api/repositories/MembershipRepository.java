package com.gympulse.api.repositories;

import com.gympulse.api.models.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Integer> {
    List<Membership> findByMemberId(Integer memberId);
    // Method to find an active membership by user ID
    Optional<Membership> findByMemberIdAndIsPaidTrue(Integer memberId);

    @Query("SELECT m FROM Membership m WHERE m.isPaid = true AND m.endDate >= CURRENT_DATE")
    List<Membership> findAllActiveMemberships();

    @Query("SELECT COALESCE(SUM(p.price), 0) FROM Membership m " +
            "JOIN m.plan p " +
            "WHERE EXTRACT(MONTH FROM m.startDate) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND EXTRACT(YEAR FROM m.startDate) = EXTRACT(YEAR FROM CURRENT_DATE)")
    BigDecimal sumMonthlyRevenue();
}