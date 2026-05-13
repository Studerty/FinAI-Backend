package com.upc.finai.repositorios;

import com.upc.finai.entidades.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByUsuarioId(Long idUsuario);
}
