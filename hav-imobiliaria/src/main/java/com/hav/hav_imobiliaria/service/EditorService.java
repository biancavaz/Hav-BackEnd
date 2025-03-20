package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Editor.EditorFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.*;
import com.hav.hav_imobiliaria.repository.EditorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class EditorService {

    private final EditorRepository repository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;

    public EditorPostRequestDTO createEditor(
            @Valid EditorPostRequestDTO editorPostDTO,
            MultipartFile image) {

        // Mapeamento do DTO para entidade usando o ModelMapper
        Editor editor = modelMapper.map(editorPostDTO, Editor.class);

        // Salvar a entidade e retornar a resposta
        Editor savededitor = repository.save(editor);

        if (image != null) {
            imageService.uploadImages(savededitor.getId(), image);
        }

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

    @Transactional
    public void removeList(List<Integer> idList) {
        repository.deleteByIdIn(idList);

    }
    public void changeArchiveStatus(List<Integer> editorIds) {
        List<Editor> editors = repository.findAllById(editorIds);
        editors.forEach(User::changeArchiveStatus);
        repository.saveAll(editors);
    }
}
