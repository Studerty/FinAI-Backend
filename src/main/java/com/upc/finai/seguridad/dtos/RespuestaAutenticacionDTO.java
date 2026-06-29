package com.upc.finai.seguridad.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaAutenticacionDTO {

    private String token;
    private Long idUsuario;
    private String rol;
    private String mensaje;

}