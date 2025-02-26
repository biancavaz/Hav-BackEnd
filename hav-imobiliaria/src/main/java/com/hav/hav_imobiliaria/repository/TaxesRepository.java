package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.TaxesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxesRepository extends JpaRepository<TaxesModel, Integer> {
}
