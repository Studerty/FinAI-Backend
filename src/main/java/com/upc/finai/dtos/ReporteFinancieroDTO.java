package com.upc.finai.dtos;

import java.util.Map;

public class ReporteFinancieroDTO {
    private Integer totalMovimientos;
    private Double totalIngresos;
    private Double totalGastos;
    private Double ahorroNeto;
    private Double montoGastosNoEsenciales;
    private Double ahorroPotencial;
    private Map<String, Double> gastosPorCategoria; // Para los gráficos de la US010

    // Constructor vacío obligatorio para Spring
    public ReporteFinancieroDTO() {
    }

    // Getters y Setters
    public Integer getTotalMovimientos() {
        return totalMovimientos;
    }

    public void setTotalMovimientos(Integer totalMovimientos) {
        this.totalMovimientos = totalMovimientos;
    }

    public Double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(Double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public Double getTotalGastos() {
        return totalGastos;
    }

    public void setTotalGastos(Double totalGastos) {
        this.totalGastos = totalGastos;
    }

    public Double getAhorroNeto() {
        return ahorroNeto;
    }

    public void setAhorroNeto(Double ahorroNeto) {
        this.ahorroNeto = ahorroNeto;
    }

    public Double getMontoGastosNoEsenciales() {
        return montoGastosNoEsenciales;
    }

    public void setMontoGastosNoEsenciales(Double montoGastosNoEsenciales) {
        this.montoGastosNoEsenciales = montoGastosNoEsenciales;
    }

    public Double getAhorroPotencial() {
        return ahorroPotencial;
    }

    public void setAhorroPotencial(Double ahorroPotencial) {
        this.ahorroPotencial = ahorroPotencial;
    }

    public Map<String, Double> getGastosPorCategoria() {
        return gastosPorCategoria;
    }

    public void setGastosPorCategoria(Map<String, Double> gastosPorCategoria) {
        this.gastosPorCategoria = gastosPorCategoria;
    }
}