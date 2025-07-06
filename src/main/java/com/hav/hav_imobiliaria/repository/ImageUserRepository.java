package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.Users.ImageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageUserRepository extends JpaRepository<ImageUser, Integer> {
    Optional<ImageUser> findByUser_Id(Integer userId);
}

