package com.upc.finai.controladores;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/presupuestos")
public class ControladorPresupuesto {


    private static List<Map<String, Object>> presupuestosDB = new ArrayList<>();

    @GetMapping
    public ResponseEntity<?> listarPresupuestos() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = auth.getName();

        List<Map<String, Object>> misPresupuestos = presupuestosDB.stream()
                .filter(p -> p.get("usuarioOwner").equals(correoUsuario))
                .collect(Collectors.toList());

        return ResponseEntity.ok(misPresupuestos);
    }

    @PostMapping
    public ResponseEntity<?> crearPresupuesto(@RequestBody Map<String, Object> nuevoPresupuesto) {

        if (nuevoPresupuesto.get("monto") == null) {
            return ResponseEntity.badRequest().body("El monto es obligatorio.");
        }

        try {
            Double monto = Double.valueOf(nuevoPresupuesto.get("monto").toString());
            if (monto <= 0) {
                return ResponseEntity.badRequest().body("El monto debe ser mayor a cero.");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("El monto debe ser un número válido.");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = auth.getName();

        nuevoPresupuesto.put("id", presupuestosDB.size() + 1);
        nuevoPresupuesto.put("usuarioOwner", correoUsuario);

        presupuestosDB.add(nuevoPresupuesto);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPresupuesto);
    }
}