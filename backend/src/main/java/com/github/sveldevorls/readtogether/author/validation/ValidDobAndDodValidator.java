package com.github.sveldevorls.readtogether.author.validation;

import java.time.LocalDate;

import com.github.sveldevorls.readtogether.author.entity.AuthorData;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDobAndDodValidator implements ConstraintValidator<ValidDobAndDod, AuthorData> {

    @Override
    public boolean isValid(AuthorData data, ConstraintValidatorContext context) {
        if (data.getDateOfBirth() == null || data.getDateOfDeath() == null)
            return true;

        boolean isValid = true;
        LocalDate dateOfBirth = data.getDateOfBirth();
        LocalDate dateOfDeath = data.getDateOfDeath();

        if (dateOfBirth.isAfter(dateOfDeath)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Date of birth cannot be after date of death")
                   .addPropertyNode("dateOfBirth")
                   .addConstraintViolation();
            context.buildConstraintViolationWithTemplate("Date of death cannot be before date of birth")
                   .addPropertyNode("dateOfDeath")
                   .addConstraintViolation();

            isValid = false;
        }

        return isValid;
    }

}
