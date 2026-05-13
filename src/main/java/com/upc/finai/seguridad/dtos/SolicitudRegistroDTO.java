package com.upc.finai.seguridad.dtos;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class SolicitudRegistroDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    private String telefono;
}