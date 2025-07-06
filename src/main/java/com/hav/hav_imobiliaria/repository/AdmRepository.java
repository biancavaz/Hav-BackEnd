package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.Users.Adm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdmRepository extends JpaRepository<Adm, Integer> {
    void deleteByIdIn(List<Integer> ids);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
