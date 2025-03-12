package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.Properties.Taxes;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustumerRepository extends JpaRepository<Customer, Integer> {
    void deleteByIdIn(List<Integer> ids);

}

