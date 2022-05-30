package com.tfg.apirest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmacoPropiedadId implements Serializable {
    private static final long serialVersionUID = -7880509939049414934L;
    @Column(name = "cd_propiedad", nullable = false)
    private UUID idPropiedad;

    @Column(name = "cd_farmaco", nullable = false)
    private UUID idFarmaco;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FarmacoPropiedadId entity = (FarmacoPropiedadId) o;
        return Objects.equals(this.idPropiedad, entity.idPropiedad) &&
                Objects.equals(this.idFarmaco, entity.idFarmaco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPropiedad, idFarmaco);
    }

}