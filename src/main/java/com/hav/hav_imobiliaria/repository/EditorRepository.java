package com.hav.hav_imobiliaria.repository;

import com.hav.hav_imobiliaria.model.entity.Users.Editor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EditorRepository extends JpaRepository<Editor, Integer> {
    void deleteByIdIn(List<Integer> ids);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
