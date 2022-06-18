package com.tfg.apirest.service;

import com.tfg.apirest.entity.TipoRol;
import com.tfg.apirest.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class TipoRolesService {
    /** Repositorio de Rol */
    private final RolRepository rolRepository;


    /**
     * Permite obtener un tipo de rol por su código
     *
     * @param codigo Código del rol
     * @result código
     */
    public TipoRol findRolByCodigo(String codigo){
        return this.findByCodigo(codigo).orElseThrow(() -> new RuntimeException("Rol no encontrado: " + codigo));
    }

    /**
     * Obtiene un tipo de rol por su código
     *
     * @param codigo Código del rol
     * @result código
     */
    private Optional<TipoRol> findByCodigo(String codigo){
        return rolRepository.findByCodigo(codigo);
    }
}
