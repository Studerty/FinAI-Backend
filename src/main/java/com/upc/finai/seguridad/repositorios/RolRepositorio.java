package com.upc.finai.seguridad.repositorios;

import com.upc.finai.seguridad.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepositorio extends JpaRepository<Rol, Long> {
}
