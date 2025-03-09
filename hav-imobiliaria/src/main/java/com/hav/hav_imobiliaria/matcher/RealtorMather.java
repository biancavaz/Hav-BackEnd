package com.hav.hav_imobiliaria.matcher;

import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

@Component
public class RealtorMather {

    public Example<Realtor> createRealtorExample(RealtorFilterPostResponseDTO realtorDTO) {

        // Mapear os dados do DTO para a entidade Realtor
        Realtor realtor = new Realtor();

        // Preenche a entidade Realtor com os dados do DTO (supondo que você já tenha o mapeamento feito)

        // Aqui você pode adicionar os filtros e configurá-los conforme necessário
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase() // Ignora maiúsculas e minúsculas
                .withIgnoreNullValues() // Ignora valores nulos
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Filtros de string com correspondência "contendo"

        // Retorna o Example com a entidade e o matcher configurados
        return Example.of(realtor, matcher);
    }
}
