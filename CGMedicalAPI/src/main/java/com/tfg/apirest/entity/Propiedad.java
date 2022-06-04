package com.tfg.apirest.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "r_farmaco_propiedad")
public class Propiedad {
    @EmbeddedId
    private PropiedadId id;

    @MapsId("cdPropiedad")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cd_propiedad", nullable = false)
    private TipoPropiedad tipoPropiedad;

    @MapsId("cdFarmaco")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cd_farmaco", nullable = false)
    private Farmaco farmaco;
}