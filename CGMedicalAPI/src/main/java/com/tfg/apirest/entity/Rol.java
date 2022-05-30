package com.tfg.apirest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_rol")
public class Rol {
    @Id
    @Column(name = "id_rol", nullable = false)
    private UUID id;

    @Column(name = "cd_rol", nullable = false, length = 20)
    private String codigo;

    @Column(name = "ds_rol", nullable = false, length = 100)
    private String descripcion;

}