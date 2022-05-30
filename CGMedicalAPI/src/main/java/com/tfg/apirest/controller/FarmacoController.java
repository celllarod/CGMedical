package com.tfg.apirest.controller;

import com.tfg.apirest.dto.FarmacoView;
import com.tfg.apirest.dto.UsuarioView;
import com.tfg.apirest.service.FarmacosService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador de Fármacos
 *
 * @author celllarod
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class FarmacoController {

    private final FarmacosService farmacosService;

    @GetMapping("testfarmaco")
    public UsuarioView getUsuarioTest () {
        var user = new UsuarioView("Celia", "Llanes");
        return user;
    }

    // Obtener todos los fármacos (TODO: de un hospital)
    @GetMapping("farmacos")
    List<FarmacoView> obtenerTodos (){

        return farmacosService.findAllFarmacos();
    }
}