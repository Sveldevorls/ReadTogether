package com.github.sveldevorls.readtogether.submission.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDobAndDodValidator.class)
public @interface ValidDobAndDod {
    
    String message() default "Invalid date of birth and date of death";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
