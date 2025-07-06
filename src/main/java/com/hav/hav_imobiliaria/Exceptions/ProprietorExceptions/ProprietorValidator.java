package com.hav.hav_imobiliaria.Exceptions.ProprietorExceptions;

import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPostDTO;
import com.hav.hav_imobiliaria.repository.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProprietorValidator implements ConstraintValidator<ValidProprietor, ProprietorPostDTO> {

    private final RealtorRepository realtorRepository;
    private final ProprietorRepository proprietorRepository;
    private final CustumerRepository customerRepository;
    private final EditorRepository editorRepository;
    private final AdmRepository admRepository;

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
        } else if (proprietorDTO.cpf() != null &&
                (proprietorRepository.existsByCpf(proprietorDTO.cpf()) ||
                        realtorRepository.existsByCpf(proprietorDTO.cpf()) ||
                        customerRepository.existsByCpf(proprietorDTO.cpf()) ||
                        editorRepository.existsByCpf(proprietorDTO.cpf()) ||
                        admRepository.existsByCpf(proprietorDTO.cpf()))) {
            context.buildConstraintViolationWithTemplate("CPF já cadastrado")
                    .addPropertyNode("cpf")
                    .addConstraintViolation();
            return false;
        } else if (proprietorDTO.cnpj() != null && proprietorRepository.existsByCnpj(proprietorDTO.cnpj())) {
            context.buildConstraintViolationWithTemplate("CNPJ já cadastrado")
                    .addPropertyNode("cnpj")
                    .addConstraintViolation();
            return false;
        } else if (proprietorDTO.email() != null &&
                (proprietorRepository.existsByEmail(proprietorDTO.email()) ||
                        realtorRepository.existsByEmail(proprietorDTO.email()) ||
                        customerRepository.existsByEmail(proprietorDTO.email()) ||
                        editorRepository.existsByEmail(proprietorDTO.email()) ||
                        admRepository.existsByEmail(proprietorDTO.email()))) {
            context.buildConstraintViolationWithTemplate("E-mail já cadastrado")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
