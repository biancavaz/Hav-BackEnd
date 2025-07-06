package com.hav.hav_imobiliaria.Exceptions.ProprietorExceptions;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProprietorValidator.class)
public @interface ValidProprietor {
    String message() default "Erro de validação do proprietário";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
