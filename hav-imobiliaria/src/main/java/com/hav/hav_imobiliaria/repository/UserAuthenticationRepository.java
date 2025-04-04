package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.Users.UsersDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthenticationRepository extends JpaRepository<UsersDetails, Long> {

    Optional<UsersDetails> findByUsername(String username);
}
