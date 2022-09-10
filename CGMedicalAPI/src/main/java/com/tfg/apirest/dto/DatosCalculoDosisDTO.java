package com.tfg.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class DatosCalculoDosisDTO {

    @JsonProperty("dosisActualFd")
    @Valid
    @NotNull(message = "El parámetro 'dosisActualFd' es obligatorio.")
    private DosisDTO dosisActualFd;

    @JsonProperty("dosisNuevaFd")
    @Valid
    private DosisDTO dosisNuevaFd;

    @JsonProperty("dosisActualFs")
    @Valid
    @NotNull(message = "El parámetro 'dosisActualFs' es obligatorio.")
    private DosisDTO dosisActualFs;

    @JsonProperty("dosisNuevaFs")
    @Valid
    private DosisDTO dosisNuevaFs;

}
