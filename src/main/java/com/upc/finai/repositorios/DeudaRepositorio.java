package com.upc.finai.repositorios;

import com.upc.finai.entidades.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeudaRepositorio extends JpaRepository<Deuda, Long> {
    List<Deuda> findByUsuarioId(Long usuarioId);
}