package com.hav.hav_imobiliaria.Exceptions.RealtorExceptions;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RealtorValidator.class)
public @interface ValidRealtor {
    String message() default "Erro de validação do corretor";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
