package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.User.UserPutRequestDTO;
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


    //VER COM O PROF


//    private UserService service;
//
//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public User editarUser(
//            @PathVariable Integer id,
//            @RequestBody @Valid UserPutRequestDTO userPutDTO){
//        return service.editUser(id, userPutDTO);
//    }
//
//
//    @PatchMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public User alterarUsers(
//            @PathVariable Integer id,
//            @RequestParam Integer idUser){
//        return service.alterUsers(id, idUser);
//    }
//
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public Page<User> buscarUsers(
//            @PageableDefault(
//                    page = 0,
//                    size = 12,
//                    sort = "nome",
//                    direction = Sort.Direction.ASC) Pageable pageable){
//
//        return service.searchUsers(pageable);
//    }
//
////    @GetMapping("/{id}")
////    @ResponseStatus(HttpStatus.OK)
////    public UserResponseDTO buscarUser(@PathVariable Integer id){
////        User user = service.searchUser(id);
////        return user.convert();
////    }
//
//
//    @DeleteMapping
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void removerUser(@PathVariable Integer id){
//        service.remove(id);
//    }

}
