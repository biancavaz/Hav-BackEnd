package com.hav.hav_imobiliaria.controller;


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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/editor")
@AllArgsConstructor
public class EditorController {

    private final EditorService service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Editor createProprietor(
            @RequestBody @Valid EditorPostDTO editorPostDTO){
        return service.createEditor(editorPostDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Editor editEditor(
            @PathVariable Integer id,
            @RequestBody @Valid EditorPutRequestDTO editorPutDTO){
        return service.editEditor(id, editorPutDTO);
    }


    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public Editor alterarEditor(
            @PathVariable Integer id,
            @RequestParam Integer idEditor){
        return service.alterEditor(id, idEditor);
    }


    @GetMapping
    public Page<Editor> searchEditor(
            @PageableDefault(
                    size = 10, //quantidade de itens por página
                    sort = "saldo", //o que vai ser listado
                    direction= Sort.Direction.DESC, // tipo da ordem que vai ser mostrado
                    page= 0 //começa mostrando a página 0
            ) Pageable pageable){
        return service.searchEditor(pageable);
    };


    //    @GetMapping
//    @RequestMapping("/{id}")
//    public RealtorGetResponseDTO searchRealtor(
////            @PathVariable Integer id){
////        Realtor realtor = service.searchRealtor(id);
//        return realtor.convert();
//    }

    @DeleteMapping
    @RequestMapping("/{id}")
    public void removeEditor(@PathVariable Integer id){
        service.remove(id);
    }




}
