package com.tfg.apirest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "r_farmaco_propiedad")
public class FarmacoPropiedad {
    @EmbeddedId
    private FarmacoPropiedadId id;

    @MapsId("idPropiedad")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cd_propiedad", nullable = false)
    private Propiedad propiedad;

    @MapsId("idFarmaco")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cd_farmaco", nullable = false)
    private Farmaco farmaco;

    @Column(name = "nu_valor", nullable = false)
    private Double valor;

    @Column(name = "cd_unidad", nullable = false, length = 10)
    private String unidad;

}