package com.hav.hav_imobiliaria.Exceptions.ProprietorExceptions;

import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPostDTO;
import com.hav.hav_imobiliaria.repository.ProprietorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProprietorValidator implements ConstraintValidator<ValidProprietor, ProprietorPostDTO> {

    private final ProprietorRepository repository;

    @Override
    public boolean isValid(ProprietorPostDTO proprietorDTO, ConstraintValidatorContext context) {
        if (proprietorDTO.cpf() == null && proprietorDTO.cnpj() == null) {
            context.buildConstraintViolationWithTemplate("CPF e CNPJ não podem ser ambos nulos")
                    .addPropertyNode("cpf")
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate("CPF e CNPJ não podem ser ambos nulos")
                    .addPropertyNode("cnpj")
                    .addConstraintViolation();
            return false;
        } else if (proprietorDTO.cpf() != null && proprietorDTO.cnpj() != null) {
            context.buildConstraintViolationWithTemplate("CPF e CNPJ não podem ser ambos preenchidos")
                    .addPropertyNode("cpf")
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate("CPF e CNPJ não podem ser ambos preenchidos")
                    .addPropertyNode("cnpj")
                    .addConstraintViolation();
            return false;
        } else if (proprietorDTO.cpf() != null && repository.existsByCpf(proprietorDTO.cpf())) {
            context.buildConstraintViolationWithTemplate("CPF já cadastrado")
                    .addPropertyNode("cpf")
                    .addConstraintViolation();
            return false;
        } else if (proprietorDTO.cnpj() != null && repository.existsByCnpj(proprietorDTO.cnpj())) {
            context.buildConstraintViolationWithTemplate("CNPJ já cadastrado")
                    .addPropertyNode("cnpj")
                    .addConstraintViolation();
            return false;
        } else if (proprietorDTO.email() != null && repository.existsByEmail(proprietorDTO.email())) {
            context.buildConstraintViolationWithTemplate("E-mail já cadastrado")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
