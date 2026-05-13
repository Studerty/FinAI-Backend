package com.upc.finai.controladores;

import com.upc.finai.entidades.Movimiento;
import com.upc.finai.repositorios.MovimientoRepositorio;
import com.upc.finai.seguridad.entidades.Usuario;
import com.upc.finai.seguridad.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class ControladorMovimiento {

    @Autowired
    private MovimientoRepositorio movimientoRepo;

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @GetMapping
    public ResponseEntity<?> listarMovimientos(Authentication auth) {
   
        Usuario usuario = usuarioRepo.findByCorreo(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

     
        List<Movimiento> misMovimientos = movimientoRepo.findByUsuarioIdOrderByCreadoEnDesc(usuario.getId());
        return ResponseEntity.ok(misMovimientos);
    }

    @PostMapping
    public ResponseEntity<?> registrarMovimiento(@RequestBody Movimiento nuevoMovimiento, Authentication auth) {
     
        if (nuevoMovimiento.getMonto() == null || nuevoMovimiento.getTipo() == null) {
            return ResponseEntity.badRequest().body("Monto y Tipo (INGRESO/EGRESO) son obligatorios.");
        }

     
        Usuario usuario = usuarioRepo.findByCorreo(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    
        nuevoMovimiento.setUsuario(usuario);

      
        Movimiento guardado = movimientoRepo.save(nuevoMovimiento);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
}
