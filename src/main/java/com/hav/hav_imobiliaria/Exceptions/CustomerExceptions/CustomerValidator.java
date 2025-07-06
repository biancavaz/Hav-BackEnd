package com.hav.hav_imobiliaria.Exceptions.CustomerExceptions;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerPostRequestDTO;
import com.hav.hav_imobiliaria.repository.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerValidator implements ConstraintValidator<ValidCustomer, CustomerPostRequestDTO> {

    private final RealtorRepository realtorRepository;
    private final ProprietorRepository proprietorRepository;
    private final CustumerRepository customerRepository;
    private final EditorRepository editorRepository;
    private final AdmRepository admRepository;

    @Override
    public boolean isValid(CustomerPostRequestDTO customerDTO, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (customerDTO.cpf() != null &&
                (realtorRepository.existsByCpf(customerDTO.cpf()) ||
                        proprietorRepository.existsByCpf(customerDTO.cpf()) ||
                        customerRepository.existsByCpf(customerDTO.cpf()) ||
                        editorRepository.existsByCpf(customerDTO.cpf()) ||
                        admRepository.existsByCpf(customerDTO.cpf()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("CPF já cadastrado")
                    .addPropertyNode("cpf")
                    .addConstraintViolation();
            isValid = false;
        }

        if (customerDTO.email() != null &&
                (realtorRepository.existsByEmail(customerDTO.email()) ||
                        proprietorRepository.existsByEmail(customerDTO.email()) ||
                        customerRepository.existsByEmail(customerDTO.email()) ||
                        editorRepository.existsByEmail(customerDTO.email()) ||
                        admRepository.existsByEmail(customerDTO.email()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("E-mail já cadastrado")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
