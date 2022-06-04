package com.tfg.apirest.service;

import com.tfg.apirest.entity.TipoPropiedad;
import com.tfg.apirest.repository.TipoPropiedadRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Validated
public class TipoPropiedadesService {
    /** Repositorio de Tipo Propiedades */
    private final TipoPropiedadRepository tipoPropiedadRepository;

    /**
     * Permite obtener un tipo de propiedad a partir de su código
     * @param codigo Código de la propiedad
     * @return tipoPropiedad
     */
    public TipoPropiedad obtenerPropiedad(String codigo) {
        return this.getTipoPropiedadByCodigo(codigo);
    }

    /**
     * Permite obtener la propiedad del repositorio
     * @param codigo Código de la propiedad
     * @return tipoPropiedad o excepcion
     */
    private TipoPropiedad getTipoPropiedadByCodigo(String codigo) {
        return tipoPropiedadRepository.getTipoPropiedadByCodigo(codigo) //
         .orElseThrow(() -> new NoSuchElementException("[ERROR] No se ha encontrado el tipo de propiedad con el Código especificado: " + codigo));
    }
}
