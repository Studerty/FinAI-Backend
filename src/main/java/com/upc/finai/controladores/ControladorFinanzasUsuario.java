package com.upc.finai.controladores;

import com.upc.finai.dtos.*;
import com.upc.finai.servicios.ServicioFinanzasUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
public class ControladorFinanzasUsuario {

    @Autowired
    private ServicioFinanzasUsuario servicioFinanzasUsuario;

   
    @GetMapping("/{id}/resumen")
    public ResumenFinancieroDTO obtenerResumen(@PathVariable("id") Long id) {
        System.out.println("DEBUG: Entrando a resumen para ID: " + id);
        return servicioFinanzasUsuario.obtenerResumen(id);
    }

 
    @PostMapping("/{id}/movimientos")
    public MovimientoDTO crearMovimiento(@PathVariable("id") Long id,
                                         @Valid @RequestBody CrearMovimientoDTO crearMovimientoDTO) {
        return servicioFinanzasUsuario.crearMovimiento(id, crearMovimientoDTO);
    }

    @GetMapping("/{id}/movimientos")
    public List<MovimientoDTO> listarMovimientosPorTipo(@PathVariable("id") Long id,
                                                        @RequestParam("tipo") String tipo) {
        return servicioFinanzasUsuario.listarMovimientosPorTipo(id, tipo);
    }

 
    @GetMapping("/{id}/presupuestos")
    public List<PresupuestoDTO> listarPresupuestos(@PathVariable("id") Long id) {
        return servicioFinanzasUsuario.listarPresupuestos(id);
    }

    @PostMapping("/{id}/presupuestos")
    public PresupuestoDTO crearPresupuesto(@PathVariable("id") Long id,
                                           @Valid @RequestBody PresupuestoDTO presupuestoDTO) {
        return servicioFinanzasUsuario.crearPresupuesto(id, presupuestoDTO);
    }

    @PostMapping("/{id}/metas")
    public MetaAhorroDTO crearMeta(@PathVariable("id") Long id,
                                   @Valid @RequestBody MetaAhorroDTO metaAhorroDTO) {
        return servicioFinanzasUsuario.crearMeta(id, metaAhorroDTO);
    }

    @GetMapping("/{id}/metas")
    public List<MetaAhorroDTO> listarMetas(@PathVariable("id") Long id) {
        return servicioFinanzasUsuario.listarMetas(id);
    }

   
    @PostMapping("/{id}/tickets")
    public TicketSoporteDTO crearTicket(@PathVariable("id") Long id,
                                        @Valid @RequestBody CrearTicketSoporteDTO crearTicketSoporteDTO) {
        return servicioFinanzasUsuario.crearTicketSoporte(id, crearTicketSoporteDTO);
    }

    @GetMapping("/{id}/tickets")
    public List<TicketSoporteDTO> listarTickets(@PathVariable("id") Long id) {
        return servicioFinanzasUsuario.listarTicketsUsuario(id);
    }
}
