package com.tfg.apirest.validation.group.annotation;

import com.tfg.apirest.validation.SignUpRequestValidation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Constraint(validatedBy = SignUpRequestValidation.class)
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface SignUpRequestValidationAnnotation {

    /** Mensaje del validador. */
    String message() default "";

    /** Permite agrupar validaciones. */
    Class<?>[] groups() default {};

    /** Permite inicializar la validacion. */
    Class<? extends Payload>[] payload() default {};
}
