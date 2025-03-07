package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.User.User;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprietorRepository extends JpaRepository<Proprietor, Integer> {
}
