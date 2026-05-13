package com.upc.finai.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PanelAdministradorDTO {
    private long totalUsuarios;
    private long totalTicketsAbiertos;
    private long totalPresupuestos;
    private long totalMetas;
    private List<UsuarioRecienteDTO> usuariosRecientes;
}
