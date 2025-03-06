package com.hav.hav_imobiliaria.model.DTO.Custumer;

<<<<<<<< HEAD:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/DTO/Custumer/CustumerPutRequestDTO.java
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import jakarta.validation.constraints.NotBlank;

public record CustumerPutRequestDTO(
========
import com.hav.hav_imobiliaria.model.entity.CustomerOwner;
import jakarta.validation.constraints.NotBlank;

public record CustomerOwnerPutRequestDTO(
>>>>>>>> main:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/DTO/Customer/CustomerOwnerPutRequestDTO.java
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String celphone) {

<<<<<<<< HEAD:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/DTO/Customer/CustumerPutRequestDTO.java
    public Customer convert(){
        return Customer.builder()
========
    public CustomerOwner convert(){
        return CustomerOwner.builder()
>>>>>>>> main:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/DTO/ Customer /CustomerOwnerPutRequestDTO.java
                .email(email)
                .password(password)
                .celphone(celphone)
                .build();
    }
}
