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
public class CrearTicketSoporteDTO {
    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 120, message = "El asunto debe tener como máximo 120 caracteres")
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(max = 800, message = "El mensaje debe tener como máximo 800 caracteres")
    private String mensaje;
}
