package com.upc.finai.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketSoporteDTO {
    private Long id;
    private String asunto;
    private String mensaje;
    private String estado;
    private String mensajeRespuesta;
    private LocalDateTime creadoEn;
    private LocalDateTime respondidoEn;
    private Long idUsuario;
    private String correoUsuario;
    private String correoAdministrador;
}
