package com.upc.finai.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetaAhorroDTO {
    private Long id;

    @NotBlank(message = "El nombre de la meta es obligatorio")
    private String nombreMeta;

    @NotNull(message = "El monto objetivo es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto objetivo debe ser mayor que cero")
    private BigDecimal montoObjetivo;

    private BigDecimal montoActual;

    @NotNull(message = "La fecha límite es obligatoria")
    @FutureOrPresent(message = "La fecha límite debe ser una fecha actual o futura")
    private LocalDate fechaLimite;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    private String estado;
}
