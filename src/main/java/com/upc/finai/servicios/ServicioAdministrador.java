package com.upc.finai.servicios;

import com.upc.finai.dtos.PanelAdministradorDTO;
import com.upc.finai.dtos.RespuestaTicketSoporteDTO;
import com.upc.finai.dtos.TicketSoporteDTO;
import com.upc.finai.dtos.UsuarioRecienteDTO;
import com.upc.finai.entidades.TicketSoporte;
import com.upc.finai.repositorios.MetaAhorroRepositorio;
import com.upc.finai.repositorios.PresupuestoRepositorio;
import com.upc.finai.repositorios.TicketSoporteRepositorio;
import com.upc.finai.seguridad.entidades.Usuario;
import com.upc.finai.seguridad.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioAdministrador {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private TicketSoporteRepositorio ticketSoporteRepositorio;
    @Autowired
    private PresupuestoRepositorio presupuestoRepositorio;
    @Autowired
    private MetaAhorroRepositorio metaAhorroRepositorio;

    public PanelAdministradorDTO obtenerPanel() {
        List<UsuarioRecienteDTO> usuariosRecientes = usuarioRepositorio.findTop5ByOrderByCreadoEnDesc()
                .stream()
                .map(usuario -> new UsuarioRecienteDTO(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getCorreo(),
                        usuario.getCreadoEn()))
                .toList();

        return new PanelAdministradorDTO(
                usuarioRepositorio.count(),
                ticketSoporteRepositorio.countByEstado("ABIERTO"),
                presupuestoRepositorio.count(),
                metaAhorroRepositorio.count(),
                usuariosRecientes
        );
    }

    public List<TicketSoporteDTO> listarTickets() {
        return ticketSoporteRepositorio.findAllByOrderByCreadoEnDesc()
                .stream()
                .map(this::mapearTicket)
                .toList();
    }

    @Transactional
    public TicketSoporteDTO responderTicket(Long idTicket, String correoAdministrador,
                                            RespuestaTicketSoporteDTO respuestaTicketSoporteDTO) {
        TicketSoporte ticketSoporte = ticketSoporteRepositorio.findById(idTicket)
                .orElseThrow(() -> new RuntimeException("No se encontró el ticket de soporte"));

        Usuario administrador = usuarioRepositorio.findByCorreo(correoAdministrador)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario administrador"));

        ticketSoporte.setMensajeRespuesta(respuestaTicketSoporteDTO.getMensajeRespuesta());
        ticketSoporte.setEstado("RESPONDIDO");
        ticketSoporte.setRespondidoEn(LocalDateTime.now());
        ticketSoporte.setRespondidoPor(administrador);
        ticketSoporte = ticketSoporteRepositorio.save(ticketSoporte);
        return mapearTicket(ticketSoporte);
    }

    private TicketSoporteDTO mapearTicket(TicketSoporte ticketSoporte) {
        return new TicketSoporteDTO(
                ticketSoporte.getId(),
                ticketSoporte.getAsunto(),
                ticketSoporte.getMensaje(),
                ticketSoporte.getEstado(),
                ticketSoporte.getMensajeRespuesta(),
                ticketSoporte.getCreadoEn(),
                ticketSoporte.getRespondidoEn(),
                ticketSoporte.getUsuario().getId(),
                ticketSoporte.getUsuario().getCorreo(),
                ticketSoporte.getRespondidoPor() != null ? ticketSoporte.getRespondidoPor().getCorreo() : null
        );
    }
}
