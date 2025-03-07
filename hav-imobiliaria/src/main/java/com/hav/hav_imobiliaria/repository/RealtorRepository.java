package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.User.Realtor;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealtorRepository extends JpaRepository<Realtor, Integer> {

}
