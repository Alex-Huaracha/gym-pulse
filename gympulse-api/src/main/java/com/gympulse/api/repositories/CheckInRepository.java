package com.gympulse.api.repositories;

import com.gympulse.api.models.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
    @Query("SELECT COUNT(c) FROM CheckIn c WHERE c.checkInTime >= CURRENT_DATE")
    Long countCheckInsToday();

    @Query(value = "SELECT EXTRACT(HOUR FROM c.check_in_time) as hour, COUNT(c.id) as total " +
            "FROM check_ins c " +
            "WHERE DATE(c.check_in_time) = CURRENT_DATE " +
            "GROUP BY EXTRACT(HOUR FROM c.check_in_time)",
            nativeQuery = true)
    List<Object[]> countCheckInsByHourToday();

    @Query(value = "SELECT EXTRACT(ISODOW FROM c.check_in_time) as day, COUNT(c.id) as total " +
            "FROM check_ins c " +
            "WHERE c.check_in_time >= DATE_TRUNC('week', CURRENT_DATE) " + // Desde el lunes de esta semana
            "GROUP BY EXTRACT(ISODOW FROM c.check_in_time)",
            nativeQuery = true)
    List<Object[]> countCheckInsByDayOfWeek();
}