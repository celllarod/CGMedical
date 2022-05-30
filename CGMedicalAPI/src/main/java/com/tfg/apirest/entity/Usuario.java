package com.tfg.apirest.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "d_usuario")
public class Usuario {
    @Id
    @Column(name = "id_usuario", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_rol")
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_hospital")
    private Hospital hospital;

    @Column(name = "ds_nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "ds_apellido_1", nullable = false, length = 50)
    private String apellido1;

    @Column(name = "ds_apellido_2", length = 50)
    private String apellido2;

    @Column(name = "ds_email", nullable = false)
    private String email;

    //
    @Column(name = "hash_password", nullable = false)
    private String hashPassword;

    @Column(name = "fh_alta", nullable = false)
    private Instant fechaAlta;

}