package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.CustumerOwnerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.RealtorPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.UserPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.UserPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.CustumerOwner;
import com.hav.hav_imobiliaria.model.entity.Realtor;
import com.hav.hav_imobiliaria.model.entity.User;
import com.hav.hav_imobiliaria.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService service;

    // REALTOR
    @PostMapping("/signin/realtor")
    @ResponseStatus(HttpStatus.CREATED)
    public Realtor signinRealtor(
            @RequestBody @Valid RealtorPostRequestDTO realtorPostDTO){
        return service.createRealtor(realtorPostDTO);
    }

    //fazer um post para custumerOwner
    @PostMapping("/signin/custumerOwner")
    @ResponseStatus(HttpStatus.CREATED)
    public CustumerOwner signinCustumerOwner(
            @RequestBody @Valid CustumerOwnerPostRequestDTO CustumerOwnerPostDTO){
        return service.createCustumerOwner(CustumerOwnerPostDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User editarUser(
            @PathVariable Integer id,
            @RequestBody @Valid UserPutRequestDTO userPutDTO){
        return service.editUser(id, userPutDTO);
    }


    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User alterarUsers(
            @PathVariable Integer id,
            @RequestParam Integer idUser){
        return service.alterUsers(id, idUser);
    }

//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public UserResponseDTO buscarUser(@PathVariable Integer id){
//        User user = service.searchUser(id);
//        return user.convert();
//    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<User> buscarUsers(
            @PageableDefault(
                    page = 0,
                    size = 12,
                    sort = "nome",
                    direction = Sort.Direction.ASC) Pageable pageable){

        return service.searchUsers(pageable);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerUser(@PathVariable Integer id){
        service.remove(id);
    }

}
