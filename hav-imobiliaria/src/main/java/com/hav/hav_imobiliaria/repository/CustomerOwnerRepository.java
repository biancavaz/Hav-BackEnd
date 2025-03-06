package com.hav.hav_imobiliaria.repository;

<<<<<<<< HEAD:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/repository/CustumerRepository.java
import com.hav.hav_imobiliaria.model.entity.Users.Custumer;
========
import com.hav.hav_imobiliaria.model.entity.CustomerOwner;
>>>>>>>> main:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/repository/CustomerOwnerRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<<< HEAD:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/repository/CustumerRepository.java
public interface CustumerRepository extends JpaRepository<Custumer, Integer> {
========
public interface CustomerOwnerRepository extends JpaRepository<CustomerOwner, Integer> {
>>>>>>>> main:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/repository/CustomerOwnerRepository.java

}
