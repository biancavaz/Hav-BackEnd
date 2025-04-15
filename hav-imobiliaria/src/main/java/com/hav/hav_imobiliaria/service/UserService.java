package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Users.UserConfigurationDto;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.UserRepository;
import com.hav.hav_imobiliaria.security.configSecurity.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final ModelMapper modelMapper;

    public UserConfigurationDto findUserByJwt(String authorizationHeader) {
        String email = tokenProvider.getEmailFromToken(authorizationHeader);
        User user = userRepository.findByEmail(email).get();

        UserConfigurationDto userConfigurationDto = modelMapper.map(user, UserConfigurationDto.class);

        try{
            userConfigurationDto.setS3key(user.getImageUser().getS3Key());
        }catch (NullPointerException e){
        }
        return userConfigurationDto;
    }
}
