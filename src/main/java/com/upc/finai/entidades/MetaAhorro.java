package com.upc.finai.entidades;

import com.upc.finai.seguridad.entidades.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "metas_ahorro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetaAhorro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nombreMeta;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montoObjetivo;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montoActual = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDate fechaLimite;

    @Column(nullable = false, length = 250)
    private String descripcion;

    @Column(nullable = false, length = 20)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
