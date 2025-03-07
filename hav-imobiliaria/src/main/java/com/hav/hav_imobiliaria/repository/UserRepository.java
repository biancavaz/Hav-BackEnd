package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
