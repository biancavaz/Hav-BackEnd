package com.hav.hav_imobiliaria.security.serviceSecurity;

import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.requestSecurity.UpdateUserSecurityRequest;

import java.util.List;

public interface UserSecurityService {

    UserSecurity findUserProfile(String jwt) throws UserException;
    UserSecurity updateUserSecurity(Integer userSecurityId, UpdateUserSecurityRequest req) throws UserException;
    UserSecurity findUserSecurityById(Integer id) throws UserException;
    List<UserSecurity> searchUserSecurity(String query) throws UserException;
}
