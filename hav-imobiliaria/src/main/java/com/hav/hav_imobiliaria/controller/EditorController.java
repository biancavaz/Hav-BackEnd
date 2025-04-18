package com.hav.hav_imobiliaria.controller;


import com.hav.hav_imobiliaria.model.DTO.Adm.AdmPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Editor;
import com.hav.hav_imobiliaria.service.EditorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/editor")
@AllArgsConstructor
public class EditorController {

    private final EditorService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EditorPostRequestDTO createEditor(
            @RequestPart("editor") @Valid EditorPostRequestDTO editorPostDTO,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return service.createEditor(editorPostDTO, image);
    }

    @PostMapping("/filter")
    public Page<EditorListGetResponseDTO> findByFilter(
            @RequestBody EditorFilterPostResponseDTO editorDto, Pageable pageable) {
        return service.findAllByFilter(pageable, editorDto);
    }

    @PatchMapping("/changeArchiveStatus")
    public void changeArchiveStatus(@RequestBody List<Integer> editorIds) {
        service.changeArchiveStatus(editorIds);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Editor updateEditor(
            @PathVariable Integer id,
            @RequestPart("editor") @Valid EditorPutRequestDTO editorPutDTO,
            @RequestPart(value = "deletedImageId", required = false) @Positive Integer deletedImageId,
            @RequestPart(value = "newImage", required = false) MultipartFile newImage
    ) {
        return service.updateEditor(id, editorPutDTO, deletedImageId, newImage);
    }

//    @GetMapping
//    public Page<Editor> searchEditor(
//            @PageableDefault(
//                    size = 10, //quantidade de itens por página
//                    sort = "saldo", //o que vai ser listado
//                    direction= Sort.Direction.DESC, // tipo da ordem que vai ser mostrado
//                    page= 0 //começa mostrando a página 0
//            ) Pageable pageable){
//        return service.searchEditor(pageable);
//    };

    @DeleteMapping
    @RequestMapping
    public void removeEditorList(@RequestBody List<Integer> idList) {
        service.removeList(idList);
    }

    @GetMapping
    @RequestMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public EditorPutRequestDTO getEditor(
            @PathVariable Integer id) {

        return service.findEditorById(id);
    }
}
