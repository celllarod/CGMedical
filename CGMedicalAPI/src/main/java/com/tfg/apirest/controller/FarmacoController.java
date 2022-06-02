package com.tfg.apirest.controller;

import com.tfg.apirest.dto.FarmacoDetalleView;
import com.tfg.apirest.dto.FarmacoResumenView;
import com.tfg.apirest.service.FarmacosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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

    /**
     * Permite obtener un listado con la información resumida de todos los fármacos asociados
     * al hospital al que pertenece el usuario que realiza la petición.
     *
     * @return lista de fármacos
     */
    @GetMapping("farmacos")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    List<FarmacoResumenView> findAllFarmacosByUserHospital (){
        return farmacosService.findAllFarmacos();
    }

    /**
     * Permite obtener la información completa de un fármaco mediante su identificador
     *
     * @param idFarmaco Identificador del fármaco
     * @return fármaco
     */
    @GetMapping("farmacos/{id}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
     public FarmacoDetalleView getFarmacoById( @NotNull @PathVariable(value = "id", required = false) UUID idFarmaco){
         return farmacosService.obtenerFarmaco(idFarmaco);
     }

    /**
     * Permite añadir un nuevo fármaco en el hospital asociado al usuario que realiza la petición
     *
     */
    // TODO: implementar

    /**
     * Permite actualizar un fármaco existente mediante su identificador
     *
     */
    // TODO: implementar

    /**
     * Permite eliminar un fármaco mediante su identificador.
     *
     */
    // TODO: implementar
}