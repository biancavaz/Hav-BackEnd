package com.hav.hav_imobiliaria.Exceptions.AdmExceptions;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdmValidator.class)
public @interface ValidAdm {
    String message() default "Erro de validação de Administrador";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
