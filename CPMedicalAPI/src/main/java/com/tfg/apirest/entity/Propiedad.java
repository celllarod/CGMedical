package com.tfg.apirest.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@IdClass(PropiedadId.class)
@Table(name = "r_farmaco_propiedad")
public class Propiedad implements Serializable {
    @Id
    @Column(name = "cd_farmaco", nullable = false)
    private UUID cdFarmaco;

    @Id
    @Column(name = "cd_propiedad", nullable = false, length = 10)
    private String cdPropiedad;

    @Id
    @GeneratedValue
    @Column(name = "id_relacion", nullable = false, length = 10)
    private UUID id;

    @Column(name = "nu_valor", nullable = false)
    private Double valor;

    @Column(name = "cd_unidad", nullable = false, length = 10)
    private String unidad;

    @MapsId("cdFarmaco")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cd_farmaco", nullable = false)
    private Farmaco farmaco;

    @MapsId("cdPropiedad")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cd_propiedad", nullable = false)
    private TipoPropiedad TipoPropiedad;
}