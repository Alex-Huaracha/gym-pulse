package com.gympulse.api.repositories;

import com.gympulse.api.models.CheckIn;
import com.gympulse.api.projections.HourlyInflowStats;
import com.gympulse.api.projections.WeeklyInflowStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
}