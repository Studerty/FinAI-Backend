package com.upc.finai.seguridad.servicios;

import com.upc.finai.seguridad.entidades.Usuario;
import com.upc.finai.seguridad.repositorios.UsuarioRepositorio;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServicioDetallesUsuario implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    public ServicioDetallesUsuario(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario"));


        String rolLimpio = usuario.getRol().replace("ROLE_", "");
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rolLimpio);

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getCorreo())
                .password(usuario.getContrasena())
                .authorities(List.of(authority))
                .accountLocked(!usuario.getActivo())
                .build();
    }
}
