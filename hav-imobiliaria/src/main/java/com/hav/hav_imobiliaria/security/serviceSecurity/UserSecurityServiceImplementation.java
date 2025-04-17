package com.hav.hav_imobiliaria.security.serviceSecurity;

import com.hav.hav_imobiliaria.security.configSecurity.TokenProvider;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.repositorySecurity.UserRepositorySecurity;
import com.hav.hav_imobiliaria.security.requestSecurity.UpdateUserSecurityRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSecurityServiceImplementation implements UserSecurityService {

    private final UserRepositorySecurity userRepositorySecurity;
    private final TokenProvider tokenProvider;

    @Override
    public UserSecurity findUserProfile(String jwt) throws UserException {
        String email = tokenProvider.getEmailFromToken(jwt);

        if (email == null) {
            throw new BadCredentialsException("recieved invalid JWT token---");
        }

        UserSecurity userSecurity = userRepositorySecurity.findUserSecurityByEmail(email);

        if (userSecurity == null) {
            throw new UserException("user not found with email " + email);
        }

        return userSecurity;
    }

    @Override
    public UserSecurity updateUserSecurity(Integer userSecurityId, UpdateUserSecurityRequest req) throws UserException {
        UserSecurity userSecurity = findUserSecurityById(userSecurityId);

        if (userSecurity.getName() != null) {
            userSecurity.setName(userSecurity.getName());
        }

        if (userSecurity.getProfile_picture() != null) {
            userSecurity.setProfile_picture(userSecurity.getProfile_picture());
        }

        return userRepositorySecurity.save(userSecurity);
    }

    @Override
    public UserSecurity findUserSecurityById(Integer id) throws UserException {
        Optional<UserSecurity> userSecurity = userRepositorySecurity.findById(id);

        if (userSecurity.isPresent()) {
            return userSecurity.get();
        }

        throw new UserException("User not found with id " + id);
    }

    @Override
    public List<UserSecurity> searchUserSecurity(String name) {
        return userRepositorySecurity.searchUserSecurities(name);
    }
}
