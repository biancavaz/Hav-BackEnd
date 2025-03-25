package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedules, Integer> {
    List<Schedules> findByRealtorId(Integer id);
}
