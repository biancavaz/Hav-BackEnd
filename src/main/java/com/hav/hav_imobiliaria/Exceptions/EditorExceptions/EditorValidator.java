package com.hav.hav_imobiliaria.Exceptions.EditorExceptions;

import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPostRequestDTO;
import com.hav.hav_imobiliaria.repository.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EditorValidator implements ConstraintValidator<ValidEditor, EditorPostRequestDTO> {

    private final RealtorRepository realtorRepository;
    private final ProprietorRepository proprietorRepository;
    private final CustumerRepository customerRepository;
    private final EditorRepository editorRepository;
    private final AdmRepository admRepository;

    @Override
    public boolean isValid(EditorPostRequestDTO editorDTO, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (editorDTO.cpf() != null &&
                (realtorRepository.existsByCpf(editorDTO.cpf()) ||
                        proprietorRepository.existsByCpf(editorDTO.cpf()) ||
                        customerRepository.existsByCpf(editorDTO.cpf()) ||
                        editorRepository.existsByCpf(editorDTO.cpf()) ||
                        admRepository.existsByCpf(editorDTO.cpf()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("CPF já cadastrado")
                    .addPropertyNode("cpf")
                    .addConstraintViolation();
            isValid = false;
        }

        if (editorDTO.email() != null &&
                (realtorRepository.existsByEmail(editorDTO.email()) ||
                        proprietorRepository.existsByEmail(editorDTO.email()) ||
                        customerRepository.existsByEmail(editorDTO.email()) ||
                        editorRepository.existsByEmail(editorDTO.email()) ||
                        admRepository.existsByEmail(editorDTO.email()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("E-mail já cadastrado")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
