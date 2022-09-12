package com.tfg.apirest.controller;

import com.tfg.apirest.dto.*;
import com.tfg.apirest.service.CalculadoraService;
import com.tfg.apirest.view.CargaView;
import com.tfg.apirest.view.PropiedadSimpleView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador de Fármacos
 *
 * @author celllarod
 */
@RestController
@RequestMapping(value = {"/api/v1/"},  produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class CalculadoraController {
    private final CalculadoraService calculadoraService;

    /**
     * Permite calcular cuál debe ser la mezcla que se debe añadir a la bomba de infusión
     *
     * @param datosCalculoMezclas
     * @return lista de fármacos
     */
    @PostMapping("calculo/mezclas")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('GESTOR')")
    @ResponseStatus(HttpStatus.OK)
    List<CargaView> calcularMezclas(@Validated @RequestBody DatosCalculoMezclasDTO datosCalculoMezclas) {

        return calculadoraService.calcularMezclas(datosCalculoMezclas);
    }

    /**
     * Permite calcular cuál debe ser la nueva dosis de la bomba de infusión
     * @param
     */
    @PostMapping("calculo/dosis")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('GESTOR')")
    @ResponseStatus(HttpStatus.OK)
    PropiedadSimpleView calcularDosis(@Validated @RequestBody DatosCalculoDosisDTO datosCalculoDosisDTO){
        return calculadoraService.calcularDosis(datosCalculoDosisDTO);
    }

}