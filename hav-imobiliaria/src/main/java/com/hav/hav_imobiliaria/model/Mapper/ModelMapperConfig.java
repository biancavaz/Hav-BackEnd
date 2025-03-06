package com.hav.hav_imobiliaria.model.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Habilitar correspondência de campos e acesso a campos privados
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true) // Habilita correspondência de campos
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE) // Acessa campos privados
                .setSkipNullEnabled(true);  //pular valores nulos

        return modelMapper;
    }
}
