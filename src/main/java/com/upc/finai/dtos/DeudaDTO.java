package com.upc.finai.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class DeudaDTO {

    @NotBlank(message = "El nombre del banco o acreedor no puede estar vacío")
    private String acreedor;

    @NotNull(message = "El monto total es obligatorio")
    @Min(value = 1, message = "El monto de la deuda debe ser mayor a 0")
    private Double montoTotal;

    @NotNull(message = "La tasa de interés es obligatoria")
    @Min(value = 0, message = "La tasa de interés no puede ser negativa")
    private Double tasaInteres;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate fechaVencimiento;


    public String getAcreedor() { return acreedor; }
    public void setAcreedor(String acreedor) { this.acreedor = acreedor; }
    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }
    public Double getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(Double tasaInteres) { this.tasaInteres = tasaInteres; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}