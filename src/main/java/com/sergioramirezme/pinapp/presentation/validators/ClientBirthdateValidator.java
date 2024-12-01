package com.sergioramirezme.pinapp.presentation.validators;

import com.sergioramirezme.pinapp.presentation.dtos.ClientCreateReqDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class ClientBirthdateValidator implements ConstraintValidator<ClientBirthdateValidation, ClientCreateReqDTO> {

    @Override
    public void initialize(ClientBirthdateValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ClientCreateReqDTO dto, ConstraintValidatorContext context) {
        LocalDate birthdate = dto.getBirthdate();
        LocalDate today = LocalDate.now();
        int calculatedAge = Period.between(birthdate, today).getYears();

        if (calculatedAge != dto.getAge()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{client.age_birthdate.mismatch}")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

}