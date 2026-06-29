package com.upc.finai.controladores;

import com.upc.finai.dtos.PanelAdministradorDTO;
import com.upc.finai.dtos.RespuestaTicketSoporteDTO;
import com.upc.finai.dtos.TicketSoporteDTO;
import com.upc.finai.servicios.ServicioAdministrador;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrador")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ADMIN')")
public class ControladorAdministrador {

    @Autowired
    private ServicioAdministrador servicioAdministrador;


    @GetMapping("/panel")
    public PanelAdministradorDTO obtenerPanel() {
        return servicioAdministrador.obtenerPanel();
    }


    @GetMapping("/tickets")
    public List<TicketSoporteDTO> listarTickets() {
        return servicioAdministrador.listarTickets();
    }


    @PutMapping("/tickets/{idTicket}/respuesta")
    public TicketSoporteDTO responderTicket(@PathVariable Long idTicket,
                                            @Valid @RequestBody RespuestaTicketSoporteDTO respuestaTicketSoporteDTO,
                                            Authentication authentication) {

        return servicioAdministrador.responderTicket(idTicket, authentication.getName(), respuestaTicketSoporteDTO);
    }

    @GetMapping("/tickets/{idTicket}")
    public TicketSoporteDTO obtenerTicket(@PathVariable Long idTicket) {

        return servicioAdministrador.obtenerTicket(idTicket);

    }
}