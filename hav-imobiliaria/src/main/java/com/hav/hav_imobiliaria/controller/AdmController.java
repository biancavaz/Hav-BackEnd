package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Adm.AdmFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Adm;
import com.hav.hav_imobiliaria.service.AdmService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/adm")
@AllArgsConstructor
public class AdmController {

    private final AdmService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AdmPostRequestDTO createAdm(
            @RequestPart("adm") @Valid AdmPostRequestDTO admPostDTO,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return service.createAdm(admPostDTO, image);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Adm editAdm(
            @PathVariable Integer id,
            @RequestPart("adm") @Valid AdmPutRequestDTO admPutDTO,
            @RequestParam(value = "deletedImageId", required = false) @Positive Integer deletedImageId,
            @RequestPart(value = "newImage", required = false) MultipartFile newImage
    ) {
        System.out.println(admPutDTO);
        return service.updateAdm(id, admPutDTO, deletedImageId, newImage);
    }

    @PostMapping("/filter")
    public Page<AdmListGetResponseDTO> findByFilter(@RequestBody AdmFilterPostResponseDTO admDto, Pageable pageable) {
        return service.findAllByFilter(pageable, admDto);
    }

    @PatchMapping("/changeArchiveStatus")
    public void changeArchiveStatus(@RequestBody List<Integer> admIds) {
        service.changeArchiveStatus(admIds);
    }

//    @GetMapping
//    public Page<Adm> searchAdm(
//            @PageableDefault(
//                    size = 10, //quantidade de itens por página
//                    sort = "saldo", //o que vai ser listado
//                    direction= Sort.Direction.DESC, // tipo da ordem que vai ser mostrado
//                    page= 0 //começa mostrando a página 0
//            ) Pageable pageable){
//        return service.searchAdm(pageable);
//    };

//    @GetMapping
//   @RequestMapping("/{id}")
//   public RealtorGetResponseDTO searchRealtor(
//            @PathVariable Integer id){
//        Realtor realtor = service.searchRealtor(id);

    //      return realtor.convert();
    //  }

//    @DeleteMapping
//    @RequestMapping("/{id}")
//    public void removeAdm(@PathVariable Integer id){
//        service.remove(id);
//    }
    @DeleteMapping
    @RequestMapping
    public void removeAdmList(@RequestBody List<Integer> idList) {
        service.removeList(idList);
    }

    @GetMapping
    @RequestMapping("{id}")
    public AdmPutRequestDTO getAdm(
            @PathVariable Integer id) {
        AdmPutRequestDTO admDTO = service.findAdmById(id);
        return admDTO;
    }
}
