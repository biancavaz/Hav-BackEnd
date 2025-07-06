package com.hav.hav_imobiliaria.security.serviceSecurity;

import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.repositorySecurity.UserRepositorySecurity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositorySecurity userRepositorySecurity;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserSecurity userSecurity = userRepositorySecurity.findUserSecurityByEmail(username);

        if (userSecurity == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.
                User(userSecurity.getEmail(), userSecurity.getPassword(), authorities);
    }
}
