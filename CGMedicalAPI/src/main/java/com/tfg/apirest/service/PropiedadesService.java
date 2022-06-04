package com.tfg.apirest.service;

import com.tfg.apirest.entity.Propiedad;
import com.tfg.apirest.repository.PropiedadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Validated
public class PropiedadesService {
    /** Repositorio de propiedades */
    private final PropiedadRepository propiedadRepository;

    /** Permite crear una asociación propiedad-fármaco
     *
     * @param propiedades Listado de propiedades a insertar
     * */
    public List<Propiedad> insertarPropiedades(Set<Propiedad> propiedades) {
         var propiedadesGuardadas = propiedadRepository.saveAllAndFlush(propiedades);
         return propiedadesGuardadas;
    }
}
