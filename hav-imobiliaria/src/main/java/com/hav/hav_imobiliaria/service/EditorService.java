package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Editor.EditorFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.*;
import com.hav.hav_imobiliaria.repository.EditorRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final PasswordGeneratorService passwordGeneratorService;

    public EditorPostRequestDTO createEditor(
            @Valid EditorPostRequestDTO editorPostDTO,
            MultipartFile image) {

        Editor editor = modelMapper.map(editorPostDTO, Editor.class);

        //setando o userDetails na mão pq ja esta pronta esta api e teria
        // que mudar todas as dtos e front end para adicionar o user_details
        UsersDetails userDetails = new UsersDetails(
                editorPostDTO.email(),
                passwordGeneratorService.generateSecurePassword(),
                true, true, true, true
        );

        userDetails.setUser(editor);
        editor.setUserDetails(userDetails);

        Editor savedEditor = repository.save(editor);

        if (image != null) {
            imageService.uploadUserImage(savedEditor.getId(), image);
        }

        return editorPostDTO.convertToDTO(savedEditor);
    }

    public Editor updateEditor(
            @Positive @NotNull Integer id,
            @Valid EditorPutRequestDTO editorPutDTO,
            @Positive @NotNull Integer deletedImageId,
            MultipartFile newImage) {

        Editor editor = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Editor com o ID " + id + " não encontrado."));

        modelMapper.map(editorPutDTO, editor);

        if (deletedImageId != null) {
            imageService.deleteUserImage(deletedImageId);
        }

        if (newImage != null) {
            imageService.uploadUserImage(id, newImage);
        }

        return repository.save(editor);
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

    public EditorPutRequestDTO findEditorById(Integer id) {
        Editor editor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Realtor not found"));

        // Converte a entidade Realtor para o DTO
        return modelMapper.map(editor, EditorPutRequestDTO.class);
    }
}
