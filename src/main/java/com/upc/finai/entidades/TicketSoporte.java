package com.upc.finai.entidades;

import com.upc.finai.seguridad.entidades.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets_soporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketSoporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String asunto;

    @Column(nullable = false, length = 800)
    private String mensaje;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(length = 800)
    private String mensajeRespuesta;

    @Column(nullable = false)
    private LocalDateTime creadoEn;

    private LocalDateTime respondidoEn;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "respondido_por_id")
    private Usuario respondidoPor;
}
