package com.upc.finai.controladores;

import com.upc.finai.dtos.AlertaDTO;
import com.upc.finai.dtos.ReporteFinancieroDTO;
import com.upc.finai.servicios.ServicioAnaliticas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analiticas")
public class ControladorAnaliticas {

    @Autowired
    private ServicioAnaliticas servicio;

    @GetMapping("/reporte")
    public ResponseEntity<ReporteFinancieroDTO> reporte(Authentication auth) {

        return ResponseEntity.ok(servicio.obtenerReporte(auth.getName()));
    }

    @GetMapping("/alertas")
    public ResponseEntity<List<AlertaDTO>> alertas(Authentication auth) {

        return ResponseEntity.ok(servicio.generarAlertas(auth.getName()));
    }
}