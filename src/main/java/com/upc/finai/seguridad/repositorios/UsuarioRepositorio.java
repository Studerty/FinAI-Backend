package com.upc.finai.seguridad.repositorios;

import com.upc.finai.seguridad.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);

    List<Usuario> findTop5ByOrderByCreadoEnDesc();
}
