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
public class UsuarioRecienteDTO {
    private Long id;
    private String nombresCompletos;
    private String correo;
    private LocalDateTime creadoEn;
}
