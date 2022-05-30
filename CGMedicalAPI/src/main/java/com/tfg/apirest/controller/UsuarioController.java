package com.tfg.apirest.controller;

import com.tfg.apirest.dto.UsuarioView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de Usuarios
 *
 * @author celllarod
 */


@RestController
@RequestMapping("/api/v1/")
public class UsuarioController {

    @GetMapping("test")
    public UsuarioView getUsuarioTest () {
        var user = new UsuarioView("Celia", "Llanes");
        return user;
    }
}
