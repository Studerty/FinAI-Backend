package com.upc.finai.entidades;

import com.upc.finai.seguridad.entidades.Usuario;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "deudas")
public class Deuda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String acreedor;
    private Double montoTotal;
    private Double tasaInteres;
    private LocalDate fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAcreedor() { return acreedor; }
    public void setAcreedor(String acreedor) { this.acreedor = acreedor; }
    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }
    public Double getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(Double tasaInteres) { this.tasaInteres = tasaInteres; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}