package com.upc.finai.seguridad.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudAutenticacionDTO {
    private String correo;
    private String contrasena;
}
