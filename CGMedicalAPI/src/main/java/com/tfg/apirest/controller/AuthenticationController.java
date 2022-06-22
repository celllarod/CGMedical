package com.tfg.apirest.controller;

import javax.validation.Valid;
import com.tfg.apirest.security.dto.*;
import com.tfg.apirest.service.AuthenticationService;
import com.tfg.apirest.service.HospitalesService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/api/v1/auth",  produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    /** Servicio de Autenticación */
    private final AuthenticationService authenticationService;

    /** Servicio de Hospital */
    private final HospitalesService hospitalesService;

    /**
     * Permite obtener el listado con los nombres de todos los hospitales existentes en el sistema
     *
     * @result listado hospitales
     */
    @GetMapping("hospitales")
    @ResponseStatus(HttpStatus.OK)
    List<String> findAllFarmacosByUserHospital (){
        return hospitalesService.findAllNombresHospitales();
    }

    /**
     * Permite autenticar un usuario y obtener su token JWT
     *
     * @param loginRequest Información de inicio de sesión del usuario
     * @return token si es autorizado, 413 si no
     */
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JwtResponse authenticateUser(@Valid @RequestBody @NotNull LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        return authenticationService.autenticar(authentication);
    }

    /**
     * Permite el registro de un usuario
     *
     * @param signUpRequest Información de registro del usuario
     * @return token si es autorizado, 413 si no
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        System.out.println("AQUI");
        return authenticationService.registrar(signUpRequest);
    }
}