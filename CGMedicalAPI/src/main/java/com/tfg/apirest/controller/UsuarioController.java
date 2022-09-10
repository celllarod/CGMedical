package com.tfg.apirest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de Usuarios
 *
 * @author celllarod
 */


@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/")
public class UsuarioController {

}
