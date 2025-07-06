package com.hav.hav_imobiliaria.Exceptions.EditorExceptions;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EditorValidator.class)
public @interface ValidEditor {
    String message() default "Erro de validação de Editor";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
