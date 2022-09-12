package com.tfg.apirest.controller;

import com.tfg.apirest.service.HospitalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;


/**
 * Controlador de Hospitales
 *
 * @author celllarod
 */


@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/api/v1/",  produces = MediaType.APPLICATION_JSON_VALUE)
public class HospitalController {

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

}
