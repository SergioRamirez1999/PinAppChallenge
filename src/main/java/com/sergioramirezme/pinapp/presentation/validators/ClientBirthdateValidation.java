package com.sergioramirezme.pinapp.presentation.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClientBirthdateValidator.class)
@Documented
public @interface ClientBirthdateValidation {

    String message() default "{client.age_birthdate.mismatch}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
