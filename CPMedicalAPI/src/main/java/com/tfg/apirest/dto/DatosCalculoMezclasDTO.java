package com.tfg.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tfg.apirest.validation.group.Crear;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class DatosCalculoMezclasDTO {

    /** Volumen de la bomba de infusión */
    @JsonProperty("volumenBomba")
    @Valid
    @NotNull(message = "El parámetro 'volumenBomba' es obligatorio.")
    private VolumenDTO volumenBomba;

    /** Fármaco dominante */
    @JsonProperty("farmacoDominante")
    @Valid
    @NotNull(message = "El parámetro 'farmacoDominante' es obligatorio.")
    private FarmacoDominanteDTO farmacoDominante;

    /** Listado de fármacos secundarios*/
    @JsonProperty("farmacosSecundarios")
    @Valid
    private List<FarmacoSecundarioDTO> farmacosSecundarios;

}
