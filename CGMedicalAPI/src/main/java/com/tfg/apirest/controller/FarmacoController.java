package com.tfg.apirest.controller;

import com.tfg.apirest.dto.FarmacoView;
import com.tfg.apirest.dto.UsuarioView;
import com.tfg.apirest.service.FarmacosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

/**
 * Controlador de Fármacos
 *
 * @author celllarod
 */
@RestController
@RequestMapping(value = {"/api/v1/"},  produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class FarmacoController {
    private final FarmacosService farmacosService;

    @GetMapping("testfarmaco")
    public UsuarioView getUsuarioTest () {
        var user = new UsuarioView("Celia", "Llanes");
        return user;
    }

    // Obtener todos los fármacos del hospital al que pertenece el usuario que realiza la petición
    @GetMapping("farmacos")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    List<FarmacoView> findAllFarmacosByUserHospital (){
        return farmacosService.findAllFarmacos();
    }
}