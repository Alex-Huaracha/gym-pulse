package com.gympulse.api.repositories;

import com.gympulse.api.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    // Custom query method to find a member by DNI
    Optional<Member> findByDni(String dni);

    // Custom query method to count active members
    @Query("SELECT COUNT(m) FROM Member m WHERE m.status = 'ACTIVE'")
    Long countActiveMembers();
}
