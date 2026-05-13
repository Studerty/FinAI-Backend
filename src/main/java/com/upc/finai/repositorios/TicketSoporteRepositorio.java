package com.upc.finai.repositorios;

import com.upc.finai.entidades.TicketSoporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketSoporteRepositorio extends JpaRepository<TicketSoporte, Long> {
    List<TicketSoporte> findByUsuarioIdOrderByCreadoEnDesc(Long idUsuario);

    List<TicketSoporte> findAllByOrderByCreadoEnDesc();

    long countByEstado(String estado);
}
