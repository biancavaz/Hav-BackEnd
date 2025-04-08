package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.Properties.ImageProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagePropertyRepository extends JpaRepository<ImageProperty, Integer> {
    List<ImageProperty> findByProperty_Id(Integer propertyId);
}

