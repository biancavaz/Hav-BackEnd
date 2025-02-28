package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.CustumerOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustumerOwnerRepository extends JpaRepository<CustumerOwner, Integer> {

}
