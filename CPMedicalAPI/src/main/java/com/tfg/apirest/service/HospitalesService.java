package com.tfg.apirest.service;

import com.tfg.apirest.entity.Hospital;
import com.tfg.apirest.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class HospitalesService {
    /** Repositorio de Hospital */
    private final HospitalRepository hospitalRepository;

    /**
     * Permite obtener un hospital a través de su nombre
     *
     * @param nombre Nombre del hospital
     * @return true si existe
     */
    public Optional<Hospital> findHospitalByNombre(String nombre) {
        return this.findByNombre(nombre);
    }

    /**
     * Permite obtener el listado con los nombres de todos los hospitales existentes en el sistema
     *
     * @result listado hospitales
     */
    public List<String> findAllNombresHospitales(){
        return this.findAllNombres();

    }

    /**
     * Permite crear un nuevo hospital
     *
     * @param hospital Hospital a registrar
     * @return hospital creado
     */
    @Transactional
    public Hospital registrar(Hospital hospital) {
        return this.save(hospital);
    }

    /**
     * Obtiene un hospital a trvés de su nombre
     *
     * @param nombre Nombre del hospital
     * @return hospital
     */
    private Optional<Hospital> findByNombre(String nombre) {
        return hospitalRepository.findByNombre(nombre);
    }

    /**
     * Crea/Actualiza un nuevo hospital
     *
     * @param hospital Hospital a registrar
     * @return hospital creado
     */
    private Hospital save(Hospital hospital) {
        return hospitalRepository.saveAndFlush(hospital);
    }

    /**
     * Obtiene el listado de hospitales
     *
     * @return listado de hospitales
     */
    private List<String> findAllNombres(){
        return hospitalRepository.findNombres();
    }
}
