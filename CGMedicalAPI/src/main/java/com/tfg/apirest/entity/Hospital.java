package com.tfg.apirest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "d_hospital")
public class Hospital {
    @Id
    @Column(name = "id_hospital", nullable = false)
    private UUID id;

    @Column(name = "ds_nombre", nullable = false, length = 100)
    private String nombre;

    @OneToMany(mappedBy = "hospital")
    private Set<Usuario> usuarios = new LinkedHashSet<>();

    @OneToMany(mappedBy = "hospital")
    private Set<Farmaco> farmacos = new LinkedHashSet<>();

}