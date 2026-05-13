package com.upc.finai.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecomendacionDTO {
    private Long id;
    private String titulo;
    private String descripcionCorta;
    private String urlEnlace;
    private String categoria;
}
