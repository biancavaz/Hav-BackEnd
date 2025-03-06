package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    boolean existsByPropertyCode(String propertyCode);
}
