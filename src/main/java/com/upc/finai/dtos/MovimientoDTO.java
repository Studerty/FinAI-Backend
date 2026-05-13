package com.upc.finai.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDTO {
    private Long id;
    private String tipo;
    private BigDecimal monto;
    private String descripcion;
    private LocalDateTime creadoEn;
}
