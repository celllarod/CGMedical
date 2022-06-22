package com.tfg.apirest.validation;

import com.tfg.apirest.security.dto.SignUpRequest;
import com.tfg.apirest.service.UsuariosService;
import com.tfg.apirest.validation.group.annotation.SignUpRequestValidationAnnotation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@RequiredArgsConstructor
@Component
public class SignUpRequestValidation
        implements ConstraintValidator<SignUpRequestValidationAnnotation, SignUpRequest> {

    /** Repositorio de Usuario */
    private final UsuariosService usuariosService;

    @Override
    public boolean isValid(SignUpRequest signUpRequest, ConstraintValidatorContext context) {
        var result = true;
        context.disableDefaultConstraintViolation();
        result = isValidEmail(signUpRequest, context) && result;
        return result;
    }

    private boolean isValidEmail(SignUpRequest signUpRequest, ConstraintValidatorContext context) {
        var result = true;
        if (usuariosService.existsUsuarioByEmail(signUpRequest.getEmail().toLowerCase())) {
            error(context,"Email ya existente en el sistema: " + signUpRequest.getEmail(), SignUpRequest.Fields.email);
            result = false;
        }
        return  result;
    }

    private void error(ConstraintValidatorContext context, String template, String node) {
        context.buildConstraintViolationWithTemplate(template).addPropertyNode(node).addConstraintViolation();
    }

}



