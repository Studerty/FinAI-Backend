package com.upc.finai.repositorios;

import com.upc.finai.entidades.MetaAhorro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetaAhorroRepositorio extends JpaRepository<MetaAhorro, Long> {
    List<MetaAhorro> findByUsuarioIdOrderByFechaLimiteAsc(Long idUsuario);
}
