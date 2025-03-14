package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    boolean existsByPropertyCode(String propertyCode);
    void deleteByPropertyCode(String propertyCode);

    List<Property> findByPropertyCodeIn(List<String> propertyCodes);
    void deleteByPropertyCodeIn(List<String> propertyCodes);

    void deleteByIdIn(List<Integer> ids);

}
