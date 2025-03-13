package com.hav.hav_imobiliaria.Exceptions.AdmExceptions;

import com.hav.hav_imobiliaria.Exceptions.EditorExceptions.ValidEditor;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPostRequestDTO;
import com.hav.hav_imobiliaria.repository.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AdmValidator implements ConstraintValidator<ValidAdm, AdmPostRequestDTO> {

    private final RealtorRepository realtorRepository;
    private final ProprietorRepository proprietorRepository;
    private final CustumerRepository customerRepository;
    private final EditorRepository editorRepository;
    private final AdmRepository admRepository;

    @Override
    public boolean isValid(AdmPostRequestDTO admDTO, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (admDTO.cpf() != null &&
                (realtorRepository.existsByCpf(admDTO.cpf()) ||
                        proprietorRepository.existsByCpf(admDTO.cpf()) ||
                        customerRepository.existsByCpf(admDTO.cpf()) ||
                        editorRepository.existsByCpf(admDTO.cpf()) ||
                        admRepository.existsByCpf(admDTO.cpf()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("CPF já cadastrado")
                    .addPropertyNode("cpf")
                    .addConstraintViolation();
            isValid = false;
        }

        if (admDTO.email() != null &&
                (realtorRepository.existsByEmail(admDTO.email()) ||
                        proprietorRepository.existsByEmail(admDTO.email()) ||
                        customerRepository.existsByEmail(admDTO.email()) ||
                        editorRepository.existsByEmail(admDTO.email()) ||
                        admRepository.existsByEmail(admDTO.email()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("E-mail já cadastrado")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
