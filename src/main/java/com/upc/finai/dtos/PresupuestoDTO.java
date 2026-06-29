package com.upc.finai.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class PresupuestoDTO {

    private Long id;

    @NotBlank(message = "El nombre del presupuesto es obligatorio")
    @Size(max = 50, message = "El nombre del presupuesto debe tener como máximo 50 caracteres")
    private String nombrePresupuesto;

    @NotNull(message = "El monto promedio mensual es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto promedio mensual debe ser mayor que cero")
    private BigDecimal montoPromedioMensual;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 50, message = "La categoría debe tener como máximo 50 caracteres")
    private String categoria;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 20, message = "El tipo debe tener como máximo 20 caracteres")
    private String tipo;

    private LocalDateTime creadoEn;
}