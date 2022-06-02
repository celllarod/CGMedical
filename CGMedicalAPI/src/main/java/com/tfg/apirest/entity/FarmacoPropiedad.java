package com.tfg.apirest.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "r_farmaco_propiedad")
public class FarmacoPropiedad {
    @EmbeddedId
    private FarmacoPropiedadId id;

    @MapsId("cdPropiedad")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cd_propiedad", nullable = false)
    private Propiedad propiedad;

    @MapsId("cdFarmaco")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cd_farmaco", nullable = false)
    private Farmaco farmaco;
}