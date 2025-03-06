package com.hav.hav_imobiliaria.model.DTO.Custumer;

<<<<<<<< HEAD:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/DTO/Custumer/CustomerOwnerPutRequestDTO.java
import com.hav.hav_imobiliaria.model.entity.CustomerOwner;
import jakarta.validation.constraints.NotBlank;

public record CustomerOwnerPutRequestDTO(
========
import com.hav.hav_imobiliaria.model.entity.Users.Custumer;
import jakarta.validation.constraints.NotBlank;

public record CustumerPutRequestDTO(
>>>>>>>> branch_bia:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/DTO/Custumer/CustumerPutRequestDTO.java
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String celphone) {

<<<<<<<< HEAD:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/DTO/Custumer/CustomerOwnerPutRequestDTO.java
    public CustomerOwner convert(){
        return CustomerOwner.builder()
========
    public Custumer convert(){
        return Custumer.builder()
>>>>>>>> branch_bia:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/DTO/Custumer/CustumerPutRequestDTO.java
                .email(email)
                .password(password)
                .celphone(celphone)
                .build();
    }
}
