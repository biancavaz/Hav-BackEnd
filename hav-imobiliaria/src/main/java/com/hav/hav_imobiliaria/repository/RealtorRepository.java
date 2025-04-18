package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RealtorRepository extends JpaRepository<Realtor, Integer> {
    void deleteByIdIn(List<Integer> ids);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByCreci(String creci);

    Realtor findByEmail(String email);
}
