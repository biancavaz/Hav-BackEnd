package com.hav.hav_imobiliaria.Exceptions.CustomerExceptions;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomerValidator.class)
public @interface ValidCustomer {
    String message() default "Erro de validação de cliente";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
