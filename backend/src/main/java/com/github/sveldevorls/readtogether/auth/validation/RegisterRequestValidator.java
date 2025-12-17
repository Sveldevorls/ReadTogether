package com.github.sveldevorls.readtogether.auth.validation;

import com.github.sveldevorls.readtogether.auth.dto.RegisterRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RegisterRequestValidator implements ConstraintValidator<PasswordsMatch, RegisterRequest> {

    @Override
    public boolean isValid(RegisterRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        String password = request.password();
        String passwordConfirm = request.passwordConfirm();

        boolean isValid = password != null && password.equals(passwordConfirm);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                   .addPropertyNode("passwordConfirm")
                   .addConstraintViolation();
        }

        return isValid;
    }
}