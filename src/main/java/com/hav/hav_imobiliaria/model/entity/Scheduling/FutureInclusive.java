package com.hav.hav_imobiliaria.model.entity.Scheduling;

import com.hav.hav_imobiliaria.model.entity.Scheduling.FutureInclusiveValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FutureInclusiveValidator.class)  // Referência ao validador
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureInclusive {
    String message() default "A data deve ser no futuro ou no presente";  // Mensagem padrão
    Class<?>[] groups() default {};  // Grupos de validação
    Class<? extends Payload>[] payload() default {};  // Informações adicionais
}