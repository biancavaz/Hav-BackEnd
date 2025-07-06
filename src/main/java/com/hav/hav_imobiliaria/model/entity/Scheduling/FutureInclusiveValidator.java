package com.hav.hav_imobiliaria.model.entity.Scheduling;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FutureInclusiveValidator implements ConstraintValidator<FutureInclusive, LocalDate> {

    @Override
    public void initialize(FutureInclusive constraintAnnotation) {
        // Inicialização se necessário (não é obrigatório)
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;  // Se for nulo, a anotação @NotNull deverá ser usada
        }
        return !value.isBefore(LocalDate.now());  // Verifica se a data não é antes de hoje
    }
}