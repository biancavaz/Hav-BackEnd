package com.hav.hav_imobiliaria.controller;


import com.hav.hav_imobiliaria.model.DTO.Editor.EditorFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Editor;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import com.hav.hav_imobiliaria.service.EditorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editor")
@AllArgsConstructor

public class EditorController {

    private final EditorService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EditorPostRequestDTO createProprietor(
            @RequestBody @Valid EditorPostRequestDTO editorPostDTO) {
        return service.createEditor(editorPostDTO);
    }

    @PostMapping("/filter")
    public Page<EditorListGetResponseDTO> findByFilter(
            @RequestBody EditorFilterPostResponseDTO editorDto, Pageable pageable){
        return service.findAllByFilter(pageable, editorDto);
    }

    @PatchMapping("/changeArchiveStatus")
    public void changeArchiveStatus(@RequestBody List<Integer> editorIds) {
        service.changeArchiveStatus(editorIds);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Editor editEditor(
            @PathVariable Integer id,
            @RequestBody @Valid EditorPutRequestDTO editorPutDTO) {
        return service.editEditor(id, editorPutDTO);
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
    public ResponseEntity<EditorPutRequestDTO> getEditor(
            @PathVariable Integer id){

        EditorPutRequestDTO editorDTO = service.findEditorById(id);
        return ResponseEntity.ok(editorDTO);
    }
}
