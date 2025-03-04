package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.User.UserPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.model.entity.User;
import com.hav.hav_imobiliaria.repository.AddressRepository;
import com.hav.hav_imobiliaria.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final AddressRepository addressRepository;

    public User create(@NotNull @Valid UserPostRequestDTO userDTO) {
        Address address = addressRepository.save(userDTO.addressDTO().convert());

        User user = userDTO.convert();

        user.setAddress(address);

        return repository.save(user);
    }


//    public User editUser(
//            @NotNull @Positive Integer id,
//            @Valid UserPutRequestDTO userPutDTO) {
//
//        if(repository.existsById(id)){
//            User user = userPutDTO.convert();
//            user.setId(id);
//            return repository.save(user);
//        }
//        throw new NoSuchElementException();
//    }
//
//    public User alterUsers(
//            @NotNull @Positive Integer id,
//            @NotNull @Positive Integer idUser) {
//
//        //essa tem que pensar
//        return null;
//    }
//
//    public Page<User> searchUsers(Pageable pageable) {
//        return repository.findAll(pageable);
//    }
//
//    public User searchUser(Integer id) {
//        return repository.findById(id).orElseThrow(NoSuchElementException::new);
//    }
//
//    public void remove(Integer id) {
//        repository.deleteById(id);
//    }

}
