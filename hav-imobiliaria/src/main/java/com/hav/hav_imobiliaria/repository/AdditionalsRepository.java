package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.Properties.Additionals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalsRepository extends JpaRepository<Additionals, Integer> {
}
