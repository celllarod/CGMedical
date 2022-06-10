package com.tfg.apirest.controller;

import com.tfg.apirest.dto.UsuarioView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controlador de Hospitales
 *
 * @author celllarod
 */


@RestController
@RequestMapping("/api/v1/")
public class HospitalController {

    @GetMapping("testhospital")
    public UsuarioView getUsuarioTest () {
        return new UsuarioView("Celia", "Llanes");
    }
}
