package com.hav.hav_imobiliaria.security.repositorySecurity;

import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositorySecurity extends JpaRepository<UserSecurity, Integer> {

    UserSecurity findUserSecurityByEmail(String email) throws UsernameNotFoundException;

    @Query("SELECT u FROM UserSecurity u WHERE u.name like %:name% or u.email like %:name%")
    List<UserSecurity> searchUserSecurities(@Param("name") String name);

    void deleteByIdIn(List<Integer> idList);
}
