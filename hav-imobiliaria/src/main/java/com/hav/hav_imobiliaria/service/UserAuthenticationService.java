package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.repository.UserAuthenticationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserAuthenticationService implements UserDetailsService {

    private final UserAuthenticationRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
