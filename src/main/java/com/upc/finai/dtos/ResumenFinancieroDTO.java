package com.upc.finai.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumenFinancieroDTO {
    private Long idUsuario;
    private String nombreUsuario;
    private BigDecimal saldoActual;
    private BigDecimal totalIngresos;
    private BigDecimal totalGastos;
    private List<MovimientoDTO> movimientos;
}
