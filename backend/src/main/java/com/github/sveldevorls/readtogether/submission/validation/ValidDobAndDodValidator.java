package com.github.sveldevorls.readtogether.submission.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.github.sveldevorls.readtogether.submission.dto.NewAuthorSubmissionDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDobAndDodValidator implements ConstraintValidator<ValidDobAndDod, NewAuthorSubmissionDTO> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean isValid(NewAuthorSubmissionDTO dto, ConstraintValidatorContext context) {
        if (dto.dateOfBirth() == null || dto.dateOfDeath() == null) return true; 

        boolean isValid = true;
        LocalDate dateOfBirth = LocalDate.parse(dto.dateOfBirth(), formatter);
        LocalDate dateOfDeath = LocalDate.parse(dto.dateOfDeath(), formatter);

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
