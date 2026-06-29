package com.upc.finai.seguridad.controladores;
import com.upc.finai.seguridad.dtos.RespuestaAutenticacionDTO;
import com.upc.finai.entidades.Cuenta;
import com.upc.finai.repositorios.CuentaRepositorio;
import com.upc.finai.seguridad.dtos.SolicitudAutenticacionDTO;
import com.upc.finai.seguridad.dtos.SolicitudRegistroDTO;
import com.upc.finai.seguridad.entidades.Usuario;
import com.upc.finai.seguridad.repositorios.UsuarioRepositorio;
import com.upc.finai.seguridad.util.UtilJwt;
import com.upc.finai.seguridad.servicios.ServicioDetallesUsuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/autenticacion")
@CrossOrigin(origins = "*")
public class ControladorAutenticacion {

    private final AuthenticationManager authenticationManager;
    private final UtilJwt utilJwt;
    private final ServicioDetallesUsuario servicioDetallesUsuario;
    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final CuentaRepositorio cuentaRepositorio; // <--- AGREGADO

    public ControladorAutenticacion(AuthenticationManager authenticationManager,
                                    UtilJwt utilJwt,
                                    ServicioDetallesUsuario servicioDetallesUsuario,
                                    UsuarioRepositorio usuarioRepositorio,
                                    PasswordEncoder passwordEncoder,
                                    CuentaRepositorio cuentaRepositorio) { // <--- AGREGADO AL CONSTRUCTOR
        this.authenticationManager = authenticationManager;
        this.utilJwt = utilJwt;
        this.servicioDetallesUsuario = servicioDetallesUsuario;
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
        this.cuentaRepositorio = cuentaRepositorio; // <--- INYECTADO
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody SolicitudRegistroDTO dto) {
        if (usuarioRepositorio.findByCorreo(dto.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: El correo ya existe.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setRol("ROLE_USER");
        usuario.setActivo(true);
        Usuario usuarioGuardado = usuarioRepositorio.save(usuario);

        // 2. CREAR CUENTA AUTOMÁTICAMENTE (Esto arregla tu error 400)
        Cuenta nuevaCuenta = new Cuenta();
        nuevaCuenta.setSaldo(BigDecimal.ZERO);
        nuevaCuenta.setUsuario(usuarioGuardado);
        cuentaRepositorio.save(nuevaCuenta);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con éxito");
    }

    @PostMapping("/registrar-admin")
    public ResponseEntity<?> registrarAdmin(@RequestBody SolicitudRegistroDTO dto) {
        if (usuarioRepositorio.findByCorreo(dto.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: El correo ya existe.");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setRol("ROLE_ADMIN");
        usuario.setActivo(true);
        Usuario adminGuardado = usuarioRepositorio.save(usuario);

        // También le creamos cuenta al admin por si acaso
        Cuenta cuentaAdmin = new Cuenta();
        cuentaAdmin.setSaldo(BigDecimal.ZERO);
        cuentaAdmin.setUsuario(adminGuardado);
        cuentaRepositorio.save(cuentaAdmin);

        return ResponseEntity.status(HttpStatus.CREATED).body("Administrador registrado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SolicitudAutenticacionDTO solicitud) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            solicitud.getCorreo(),
                            solicitud.getContrasena()
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");

        }

        UserDetails userDetails =
                servicioDetallesUsuario.loadUserByUsername(
                        solicitud.getCorreo()
                );

        String token = utilJwt.generarToken(userDetails);

        Usuario usuario =
                usuarioRepositorio.findByCorreo(solicitud.getCorreo())
                        .orElseThrow();

        RespuestaAutenticacionDTO respuesta =
                new RespuestaAutenticacionDTO();

        respuesta.setToken(token);
        respuesta.setIdUsuario(usuario.getId());
        respuesta.setRol(usuario.getRol());
        respuesta.setMensaje("Inicio de sesión exitoso");

        return ResponseEntity.ok(respuesta);

    }
}