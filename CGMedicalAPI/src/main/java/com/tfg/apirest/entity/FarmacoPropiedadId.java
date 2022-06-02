package com.tfg.apirest.entity;

import lombok.Getter;
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
public class FarmacoPropiedadId implements Serializable {
    private static final long serialVersionUID = 65188043622788740L;
    @Column(name = "cd_propiedad", nullable = false)
    private UUID cdPropiedad;

    @Column(name = "cd_farmaco", nullable = false)
    private UUID cdFarmaco;

    @Column(name = "nu_valor", nullable = false)
    private Double valor;

    @Column(name = "cd_unidad", nullable = false, length = 10)
    private String unidad;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FarmacoPropiedadId entity = (FarmacoPropiedadId) o;
        return Objects.equals(this.valor, entity.valor) &&
                Objects.equals(this.cdPropiedad, entity.cdPropiedad) &&
                Objects.equals(this.cdFarmaco, entity.cdFarmaco) &&
                Objects.equals(this.unidad, entity.unidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor, cdPropiedad, cdFarmaco, unidad);
    }

}