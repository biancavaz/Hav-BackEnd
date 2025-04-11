package com.hav.hav_imobiliaria.security.controllerSecurity;

import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.repository.CustumerRepository;
import com.hav.hav_imobiliaria.security.configSecurity.TokenProvider;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.AuthResponse;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.Role;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.repositorySecurity.UserRepositorySecurity;
import com.hav.hav_imobiliaria.security.requestSecurity.LoginRequest;
import com.hav.hav_imobiliaria.security.serviceSecurity.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRepositorySecurity userRepositorySecurity;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustumerRepository customerReporitory;
    private final ModelMapper modelMapper;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse createUserHandler(@Valid @RequestBody UserSecurity userSec) throws UserException {

        String email = userSec.getEmail();
        String password = userSec.getPassword();
        String full_name = userSec.getName();


        UserSecurity isEmailExist = userRepositorySecurity.findUserSecurityByEmail(email);
        if (isEmailExist != null) {
            System.out.println("-------- exist" + isEmailExist.getEmail());
            throw new UserException("Email already exist with another account");
        }

        UserSecurity newUserSec = new UserSecurity();

        newUserSec.setEmail(email);
        newUserSec.setPassword(passwordEncoder.encode(password));
        newUserSec.setName(full_name);
        newUserSec.setRole(Role.valueOf("CUSTOMER"));


        userRepositorySecurity.save(newUserSec);

        Customer customer = modelMapper.map(userSec, Customer.class);
        customer.setUserSecurity(newUserSec);
        customerReporitory.save(customer);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setStatus(true);
        authResponse.setJwt(token);

        return authResponse;
    }

    @PostMapping("/singin")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse createUserHandler(@RequestBody LoginRequest req) {

        String username = req.getEmail();
        String password = req.getPassword();

        System.out.println(username + " ------ " + password);

        Authentication authentication = (Authentication) authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        // Pegando o UserDetails (User logado)
        UserSecurity user = userRepositorySecurity.findUserSecurityByEmail(username);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setStatus(true);
        authResponse.setJwt(token);
        authResponse.setUser(user);

        return authResponse;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        System.out.println("Sing in UserDetail ---" + userDetails);

        if (userDetails == null) {
            System.out.println("Sing in UserDetail ---" + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("Sing in UserDetail ---" + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
