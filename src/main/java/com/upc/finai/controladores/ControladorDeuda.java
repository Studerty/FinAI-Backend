package com.upc.finai.controladores;

import com.upc.finai.dtos.DeudaDTO;
import com.upc.finai.entidades.Deuda;
import com.upc.finai.servicios.ServicioDeuda;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deudas")
@CrossOrigin(origins = "*")
public class ControladorDeuda {

    @Autowired private ServicioDeuda servicioDeuda;

    @PostMapping
    public ResponseEntity<Deuda> registrarDeuda(@Valid @RequestBody DeudaDTO deudaDTO, Authentication authentication) {
        String correoUsuario = authentication.getName();
        return ResponseEntity.ok(servicioDeuda.registrarDeuda(deudaDTO, correoUsuario));
    }

    @GetMapping
    public ResponseEntity<List<Deuda>> listarMisDeudas(Authentication authentication) {
        String correoUsuario = authentication.getName();
        return ResponseEntity.ok(servicioDeuda.listarDeudas(correoUsuario));
    }

    @GetMapping("/simulador")
    public ResponseEntity<?> simularPrestamo(
            @RequestParam Double monto,
            @RequestParam Double tasaAnual,
            @RequestParam Integer meses) {
        return ResponseEntity.ok(servicioDeuda.simularPrestamo(monto, tasaAnual, meses));
    }
}