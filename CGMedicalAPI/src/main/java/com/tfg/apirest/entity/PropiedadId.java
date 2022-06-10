package com.tfg.apirest.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PropiedadId implements Serializable {
    @Serial
    private static final long serialVersionUID = -8103626973778467926L;

    private UUID cdFarmaco;

    private String cdPropiedad;

    private UUID id;
}