package com.upc.finai.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "articulos_recomendacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloRecomendacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 350)
    private String descripcionCorta;

    @Column(nullable = false, length = 350)
    private String urlEnlace;

    @Column(nullable = false, length = 40)
    private String categoria;
}
