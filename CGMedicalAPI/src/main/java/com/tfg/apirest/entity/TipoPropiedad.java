package com.tfg.apirest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_propiedad")
public class TipoPropiedad {
    @Id
    @Column(name = "id_propiedad", nullable = false, length = 10)
    private String codigo;

    @Column(name = "ds_tipo", nullable = false, length = 100)
    private String descripcion;

}