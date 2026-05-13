package com.upc.finai.entidades;

import com.upc.finai.seguridad.entidades.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Esto te ayudará mucho para crear movimientos en los tests
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String tipo; // "INGRESO" o "GASTO"

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false, length = 50) // <-- Esta es la que faltaba
    private String categoria;

    @Column(length = 250)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime creadoEn;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Método para asegurar que la fecha se ponga sola si te olvidas
    @PrePersist
    protected void onCreate() {
        if (this.creadoEn == null) {
            this.creadoEn = LocalDateTime.now();
        }
    }
}