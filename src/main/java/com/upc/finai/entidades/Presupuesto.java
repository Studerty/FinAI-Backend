package com.upc.finai.entidades;

import com.upc.finai.seguridad.entidades.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "presupuestos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Presupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombrePresupuesto;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montoPromedioMensual;

    @Column(nullable = false)
    private LocalDateTime creadoEn;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
