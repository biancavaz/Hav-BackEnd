package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import jakarta.validation.constraints.Future;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedules, Integer> {
    List<Schedules> findByRealtorId(Integer id);

    Page<Schedules> findByCustomerIdAndDayLessThanAndCustomerIsNotNullAndPropertyIsNotNull(
            Integer id, LocalDate today, Pageable pageable);
    Page<Schedules> findByRealtorIdAndDayLessThanAndCustomerIsNotNullAndPropertyIsNotNull(
            Integer id, LocalDate today, Pageable pageable);

    List<Schedules> findByRealtorIdAndDayGreaterThanEqual(Integer realtorId, LocalDate today);
    @Query("SELECT s FROM Schedules s WHERE s.day = :day AND s.start_hour = :start_hour")
    List<Schedules> findByDayAndStartHour(@Param("day") LocalDate day,
                                         @Param("start_hour") LocalTime startHour);

    List<Schedules> findByRealtorIdAndPropertyIdAndDayGreaterThanEqual(Integer realtorId, Integer propertyId, LocalDate now);
}
