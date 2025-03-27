package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import jakarta.validation.constraints.Future;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedules, Integer> {
    List<Schedules> findByRealtorId(Integer id);

    List<Schedules> findByRealtorIdAndDayGreaterThanEqual(Integer realtorId, LocalDate today);
}
