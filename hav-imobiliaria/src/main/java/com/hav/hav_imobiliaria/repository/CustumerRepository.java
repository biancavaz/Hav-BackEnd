package com.hav.hav_imobiliaria.repository;

<<<<<<<< HEAD:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/repository/CustomerOwnerRepository.java
import com.hav.hav_imobiliaria.model.entity.CustomerOwner;
========
import com.hav.hav_imobiliaria.model.entity.Users.Custumer;
>>>>>>>> branch_bia:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/repository/CustumerRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<<< HEAD:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/repository/CustomerOwnerRepository.java
public interface CustomerOwnerRepository extends JpaRepository<CustomerOwner, Integer> {
========
public interface CustumerRepository extends JpaRepository<Custumer, Integer> {
>>>>>>>> branch_bia:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/repository/CustumerRepository.java

}
