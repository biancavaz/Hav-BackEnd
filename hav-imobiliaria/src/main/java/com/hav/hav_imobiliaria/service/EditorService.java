package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyListGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.model.entity.Users.Editor;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import com.hav.hav_imobiliaria.repository.EditorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EditorService {

    private final EditorRepository repository;
    private final ModelMapper modelMapper;


    //certo
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
                + editor.getCelphone()
                +editor.getCpf());

        return editorPostDTO.convertToDTO(savededitor);
    }

    //certo
    public Editor editEditor(
            @Positive @NotNull Integer id, @Valid EditorPutRequestDTO editorPutDTO) {

        Editor existingEditor = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Editor com o ID " + id + " não encontrado."));

        // Atualiza apenas os campos que vieram no DTO (mantendo os valores existentes)
        modelMapper.map(editorPutDTO, existingEditor);

        return repository.save(existingEditor);
    }

    public Page<EditorListGetResponseDTO> findAllByFilter(Pageable pageable, EditorFilterPostResponseDTO editorDto) {
        Editor editor = modelMapper.map(editorDto, Editor.class);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Editor> example = Example.of(editor, matcher);

        Page<Editor> editorList = repository.findAll(example, pageable);



        Page<EditorListGetResponseDTO> editorListGetResponseDtos = editorList.map(editorx ->
                modelMapper.map(editorx, EditorListGetResponseDTO.class)
        );

        return editorListGetResponseDtos;

    }
}
