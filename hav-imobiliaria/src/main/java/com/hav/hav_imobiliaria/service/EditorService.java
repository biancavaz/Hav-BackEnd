package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Editor;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import com.hav.hav_imobiliaria.repository.EditorRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EditorService {

    private final EditorRepository repository;
    private final ModelMapper modelMapper;


    public EditorPostRequestDTO createEditor(
            @Valid EditorPostRequestDTO editorPostDTO) {

        System.out.println("Recebido no DTO: " + editorPostDTO);

        // Mapeamento do DTO para entidade usando o ModelMapper
        Editor editor = modelMapper.map(editorPostDTO, Editor.class);

        // Salvar a entidade e retornar a resposta
        Editor savededitor = repository.save(editor);

        //testando só
        System.out.println("Convertido para entidade: "
                + savededitor
                + editor.getName()
                + editor.getEmail()
                + editor.getPassword()
                + editor.getCelphone()
                +editor.getCpf());

        return editorPostDTO.convertToDTO(savededitor);
    }
}
