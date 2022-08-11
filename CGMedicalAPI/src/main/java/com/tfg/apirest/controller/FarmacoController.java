package com.tfg.apirest.controller;

import com.tfg.apirest.dto.DatosFarmacoCrearDTO;
import com.tfg.apirest.view.FarmacoDetalleView;
import com.tfg.apirest.view.FarmacoResumenView;
import com.tfg.apirest.dto.PropiedadesDTO;
import com.tfg.apirest.entity.Farmaco;
import com.tfg.apirest.function.PropiedadesDTOToPropiedadesSetFunction;
import com.tfg.apirest.service.FarmacosService;
import com.tfg.apirest.validation.group.Crear;
import com.tfg.apirest.validation.group.Modificar;
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
    @PreAuthorize("hasAuthority('USER') or hasAuthority('GESTOR')")
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
    @PreAuthorize("hasAuthority('USER') or hasAuthority('GESTOR')")
    @ResponseStatus(HttpStatus.OK)
     public FarmacoDetalleView getFarmacoById(@NotNull @PathVariable(value = "id", required = false) UUID idFarmaco){
         return farmacosService.obtenerFarmaco(idFarmaco);
     }

    /**
     * Permite añadir un nuevo fármaco en el hospital asociado al usuario que realiza la petición
     * @param datosCrearFarmacoDTO Datos para crear el fármaco
     * @return fármaco creado
     */
    @PostMapping("farmacos")
    @PreAuthorize("hasAuthority('GESTOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public FarmacoDetalleView createFarmaco(@Validated({Crear.class}) @RequestBody DatosFarmacoCrearDTO datosCrearFarmacoDTO){

        var farmaco = new Farmaco();
        farmaco.setNombre(datosCrearFarmacoDTO.getNombre());
        var propiedades = new PropiedadesDTOToPropiedadesSetFunction().apply(datosCrearFarmacoDTO.getPropiedades());
        return farmacosService.crearFarmaco(farmaco, propiedades);
    }

    /**
     * Permite actualizar un fármaco existente mediante su identificador. Solo se podrán actualizar sus propiedades, no su nombre
     * @param idFarmaco Identificador del fármaco a actualizar
     * @param datosModificarFarmacoDTO Datos para modificar el fármaco
     * @return fármaco actualizado
     */
    @PutMapping("farmacos/{id}")
    @PreAuthorize("hasAuthority('GESTOR')")
    @ResponseStatus(HttpStatus.OK)
    public FarmacoDetalleView updateFarmaco(
            @NotNull @PathVariable(value = "id", required = false) UUID idFarmaco,
            @Validated({Modificar.class}) @RequestBody PropiedadesDTO datosModificarFarmacoDTO){

        // Comprobamos si existe el fármaco
        var toUpdate = farmacosService.existeFarmaco(idFarmaco);

        var farmaco = new Farmaco();
        farmaco.setId(toUpdate.getId());
        var propiedades = new PropiedadesDTOToPropiedadesSetFunction().apply(datosModificarFarmacoDTO);
        return farmacosService.actualizarFarmaco(farmaco, propiedades);

    }

    /**
     * Permite eliminar un fármaco mediante su identificador.
     *
     * @param idFarmaco Identificador del fármaco
     */
    @DeleteMapping("farmacos/{id}")
    @PreAuthorize("hasAuthority('GESTOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFarmaco(
            @NotNull @PathVariable(value = "id", required = false) UUID idFarmaco) {

        // Comprobamos si existe el fármaco
        var farmaco = farmacosService.existeFarmaco(idFarmaco);

        farmacosService.eliminarFarmaco(farmaco);
    }
}