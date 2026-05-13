package com.upc.finai.excepciones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RespuestaError {
    private LocalDateTime fechaHora;
    private int estado;
    private String mensaje;
    private List<String> detalles;
}
