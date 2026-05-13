package com.upc.finai.controladores;

import com.upc.finai.dtos.AlertaDTO;
import com.upc.finai.dtos.ReporteFinancieroDTO;
import com.upc.finai.servicios.ServicioAnaliticas;
import com.upc.finai.servicios.ServicioDeuda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analisis")
@PreAuthorize("hasAuthority('ROLE_USER')") 
public class ControladorAnalisisFinanciero {

    @Autowired
    private ServicioAnaliticas servicioAnaliticas;

    @Autowired
    private ServicioDeuda servicioDeuda;

    @GetMapping("/reporte-completo")
    public ResponseEntity<ReporteFinancieroDTO> obtenerReporte(Authentication auth) {
        return ResponseEntity.ok(servicioAnaliticas.obtenerReporte(auth.getName()));
    }

    @GetMapping("/alertas")
    public ResponseEntity<List<AlertaDTO>> obtenerAlertas(Authentication auth) {
        return ResponseEntity.ok(servicioAnaliticas.generarAlertas(auth.getName()));
    }

    @PostMapping("/calculadora-prestamos")
    public ResponseEntity<Map<String, Object>> simularPrestamo(@RequestBody Map<String, Double> datos) {
        Double monto = datos.get("monto");
        Double tasa = datos.get("tasa");
        Integer meses = datos.get("meses").intValue();

        return ResponseEntity.ok(servicioDeuda.simularPrestamo(monto, tasa, meses));
    }

    @GetMapping("/salud-crediticia")
    public ResponseEntity<Map<String, Object>> analizarCredito(Authentication auth) {
        return ResponseEntity.ok(servicioDeuda.obtenerSaludCrediticia(auth.getName()));
    }
}
