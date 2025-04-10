package com.hav.hav_imobiliaria.security.controllerSecurity;

import com.hav.hav_imobiliaria.security.ResponseSecurity.ApiResponse;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.repositorySecurity.UserRepositorySecurity;
import com.hav.hav_imobiliaria.security.requestSecurity.UpdateUserSecurityRequest;
import com.hav.hav_imobiliaria.security.serviceSecurity.UserSecurityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@RestController
@RequestMapping("/api/usersecurity")
@AllArgsConstructor
public class UserSecurityController {

    private final UserSecurityService userSecurityService;

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserSecurity getUserProfileHandler(@RequestHeader("Authorization") String token)
            throws UserException {
        return userSecurityService.findUserProfile(token);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<UserSecurity> searchUserSecurityHandler(@RequestParam("query") String query)
            throws UserException {
        return userSecurityService.searchUserSecurity(query);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse updateUserSecurityHandler(
            @RequestBody UpdateUserSecurityRequest req,
            @RequestHeader("Authorization") String token, SessionStatus sessionStatus)
            throws UserException {

        UserSecurity userSecurity = userSecurityService.findUserProfile(token);

        userSecurityService.updateUserSecurity(userSecurity.getId(), req);

        return new ApiResponse("User updated successfully", true);
    }
}
