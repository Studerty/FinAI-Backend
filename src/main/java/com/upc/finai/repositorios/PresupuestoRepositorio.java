package com.upc.finai.repositorios;

import com.upc.finai.entidades.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PresupuestoRepositorio extends JpaRepository<Presupuesto, Long> {
    List<Presupuesto> findByUsuarioIdOrderByCreadoEnDesc(Long idUsuario);
}
