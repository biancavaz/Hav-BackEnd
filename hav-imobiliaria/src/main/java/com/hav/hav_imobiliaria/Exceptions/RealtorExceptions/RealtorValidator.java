package com.hav.hav_imobiliaria.Exceptions.RealtorExceptions;

import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPostRequestDTO;
import com.hav.hav_imobiliaria.repository.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RealtorValidator implements ConstraintValidator<ValidRealtor, RealtorPostRequestDTO> {

    private final RealtorRepository realtorRepository;
    private final ProprietorRepository proprietorRepository;
    private final CustumerRepository customerRepository;
    private final EditorRepository editorRepository;
    private final AdmRepository admRepository;

    @Override
    public boolean isValid(RealtorPostRequestDTO realtorDTO, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (realtorDTO.cpf() != null &&
                (realtorRepository.existsByCpf(realtorDTO.cpf()) ||
                        proprietorRepository.existsByCpf(realtorDTO.cpf()) ||
                        customerRepository.existsByCpf(realtorDTO.cpf()) ||
                        editorRepository.existsByCpf(realtorDTO.cpf()) ||
                        admRepository.existsByCpf(realtorDTO.cpf()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("CPF já cadastrado")
                    .addPropertyNode("cpf")
                    .addConstraintViolation();
            isValid = false;
        }

        if (realtorDTO.email() != null &&
                (realtorRepository.existsByEmail(realtorDTO.email()) ||
                        proprietorRepository.existsByEmail(realtorDTO.email()) ||
                        customerRepository.existsByEmail(realtorDTO.email()) ||
                        editorRepository.existsByEmail(realtorDTO.email()) ||
                        admRepository.existsByEmail(realtorDTO.email()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("E-mail já cadastrado")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            isValid = false;
        }

        if (realtorDTO.creci() != null && realtorRepository.existsByCreci(realtorDTO.creci())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("CRECI já cadastrado")
                    .addPropertyNode("creci")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
