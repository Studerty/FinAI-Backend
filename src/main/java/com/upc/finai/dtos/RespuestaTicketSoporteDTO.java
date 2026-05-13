package com.upc.finai.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaTicketSoporteDTO {
    @NotBlank(message = "La respuesta es obligatoria")
    @Size(max = 800, message = "La respuesta debe tener como máximo 800 caracteres")
    private String mensajeRespuesta;
}
