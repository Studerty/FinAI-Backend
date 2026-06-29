package com.upc.finai.servicios;

import com.upc.finai.dtos.*;
import com.upc.finai.entidades.Cuenta;
import com.upc.finai.entidades.MetaAhorro;
import com.upc.finai.entidades.Movimiento;
import com.upc.finai.entidades.Presupuesto;
import com.upc.finai.entidades.TicketSoporte;
import com.upc.finai.repositorios.*;
import com.upc.finai.seguridad.entidades.Usuario;
import com.upc.finai.seguridad.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioFinanzasUsuario {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private CuentaRepositorio cuentaRepositorio;

    @Autowired
    private MovimientoRepositorio movimientoRepositorio;

    @Autowired
    private PresupuestoRepositorio presupuestoRepositorio;

    @Autowired
    private MetaAhorroRepositorio metaAhorroRepositorio;

    @Autowired
    private TicketSoporteRepositorio ticketSoporteRepositorio;

    @Autowired
    private ModelMapper modelMapper;



    public ResumenFinancieroDTO obtenerResumen(Long idUsuario) {

        Usuario usuario = obtenerUsuario(idUsuario);

        Cuenta cuenta = cuentaRepositorio.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new RuntimeException("No se encontró la cuenta del usuario"));

        BigDecimal totalIngresos = movimientoRepositorio.sumarPorUsuarioYTipo(idUsuario, "INGRESO");
        BigDecimal totalGastos = movimientoRepositorio.sumarPorUsuarioYTipo(idUsuario, "GASTO");

        List<MovimientoDTO> movimientos = movimientoRepositorio
                .findByUsuarioIdOrderByCreadoEnDesc(idUsuario)
                .stream()
                .map(movimiento -> modelMapper.map(movimiento, MovimientoDTO.class))
                .toList();

        return new ResumenFinancieroDTO(
                usuario.getId(),
                usuario.getNombre(),
                cuenta.getSaldo(),
                totalIngresos,
                totalGastos,
                movimientos
        );
    }


    @Transactional
    public MovimientoDTO crearMovimiento(Long idUsuario, CrearMovimientoDTO crearMovimientoDTO) {

        Usuario usuario = obtenerUsuario(idUsuario);

        Cuenta cuenta = cuentaRepositorio.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new RuntimeException("No se encontró la cuenta del usuario"));

        String tipoMovimiento = normalizarTipo(crearMovimientoDTO.getTipo());

        if (tipoMovimiento.equals("GASTO")
                && cuenta.getSaldo().compareTo(crearMovimientoDTO.getMonto()) < 0) {
            throw new RuntimeException("Saldo insuficiente para registrar el gasto");
        }

        Movimiento movimiento = new Movimiento();

        movimiento.setTipo(tipoMovimiento);
        movimiento.setMonto(crearMovimientoDTO.getMonto());

        String categoria = crearMovimientoDTO.getCategoria();

        if (categoria == null || categoria.isBlank()) {
            if (tipoMovimiento.equals("INGRESO")) {
                categoria = "INGRESO";
            } else {
                categoria = "GENERAL";
            }
        }

        movimiento.setCategoria(categoria.trim().toUpperCase());

        movimiento.setDescripcion(crearMovimientoDTO.getDescripcion());
        movimiento.setCreadoEn(LocalDateTime.now());
        movimiento.setUsuario(usuario);

        if (tipoMovimiento.equals("INGRESO")) {
            cuenta.setSaldo(cuenta.getSaldo().add(crearMovimientoDTO.getMonto()));
        } else {
            cuenta.setSaldo(cuenta.getSaldo().subtract(crearMovimientoDTO.getMonto()));
        }

        cuentaRepositorio.save(cuenta);

        movimiento = movimientoRepositorio.save(movimiento);

        return modelMapper.map(movimiento, MovimientoDTO.class);
    }

    public List<MovimientoDTO> listarMovimientosPorTipo(Long idUsuario, String tipo) {

        obtenerUsuario(idUsuario);

        return movimientoRepositorio
                .findByUsuarioIdAndTipoOrderByCreadoEnDesc(idUsuario, normalizarTipo(tipo))
                .stream()
                .map(movimiento -> modelMapper.map(movimiento, MovimientoDTO.class))
                .toList();
    }



    @Transactional
    public PresupuestoDTO crearPresupuesto(Long idUsuario, PresupuestoDTO presupuestoDTO) {

        Usuario usuario = obtenerUsuario(idUsuario);

        Presupuesto presupuesto = new Presupuesto();

        presupuesto.setNombrePresupuesto(
                presupuestoDTO.getNombrePresupuesto()
        );

        presupuesto.setMontoPromedioMensual(
                presupuestoDTO.getMontoPromedioMensual()
        );

        // NUEVOS CAMPOS
        presupuesto.setCategoria(
                presupuestoDTO.getCategoria()
        );

        presupuesto.setTipo(
                presupuestoDTO.getTipo()
        );

        presupuesto.setCreadoEn(LocalDateTime.now());

        presupuesto.setUsuario(usuario);

        presupuesto = presupuestoRepositorio.save(presupuesto);

        return modelMapper.map(presupuesto, PresupuestoDTO.class);
    }

    public List<PresupuestoDTO> listarPresupuestos(Long idUsuario) {

        obtenerUsuario(idUsuario);

        return presupuestoRepositorio
                .findByUsuarioIdOrderByCreadoEnDesc(idUsuario)
                .stream()
                .map(presupuesto -> modelMapper.map(presupuesto, PresupuestoDTO.class))
                .toList();
    }

    @Transactional
    public PresupuestoDTO editarPresupuesto(Long idPresupuesto, PresupuestoDTO presupuestoDTO) {
        Presupuesto presupuesto = presupuestoRepositorio.findById(idPresupuesto)
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado con ID: " + idPresupuesto));

        presupuesto.setNombrePresupuesto(presupuestoDTO.getNombrePresupuesto());
        presupuesto.setMontoPromedioMensual(presupuestoDTO.getMontoPromedioMensual());
        presupuesto.setCategoria(presupuestoDTO.getCategoria());
        presupuesto.setTipo(presupuestoDTO.getTipo());

        presupuesto = presupuestoRepositorio.save(presupuesto);
        return modelMapper.map(presupuesto, PresupuestoDTO.class);
    }

    public void eliminarPresupuesto(Long idPresupuesto) {
        if (!presupuestoRepositorio.existsById(idPresupuesto)) {
            throw new RuntimeException("Presupuesto no encontrado con ID: " + idPresupuesto);
        }
        presupuestoRepositorio.deleteById(idPresupuesto);
    }


    @Transactional
    public MetaAhorroDTO crearMeta(Long idUsuario, MetaAhorroDTO metaAhorroDTO) {

        Usuario usuario = obtenerUsuario(idUsuario);

        MetaAhorro metaAhorro = new MetaAhorro();

        metaAhorro.setNombreMeta(metaAhorroDTO.getNombreMeta());
        metaAhorro.setMontoObjetivo(metaAhorroDTO.getMontoObjetivo());

        // Si no envían montoActual, inicia en 0
        if (metaAhorroDTO.getMontoActual() == null) {
            metaAhorro.setMontoActual(BigDecimal.ZERO);
        } else {
            metaAhorro.setMontoActual(metaAhorroDTO.getMontoActual());
        }

        metaAhorro.setFechaLimite(metaAhorroDTO.getFechaLimite());
        metaAhorro.setDescripcion(metaAhorroDTO.getDescripcion());

        // Estado automático
        if (metaAhorro.getMontoActual().compareTo(metaAhorro.getMontoObjetivo()) >= 0) {
            metaAhorro.setEstado("COMPLETADA");
        } else {
            metaAhorro.setEstado("ACTIVA");
        }

        metaAhorro.setUsuario(usuario);

        metaAhorro = metaAhorroRepositorio.save(metaAhorro);

        return modelMapper.map(metaAhorro, MetaAhorroDTO.class);
    }

    public List<MetaAhorroDTO> listarMetas(Long idUsuario) {

        obtenerUsuario(idUsuario);

        return metaAhorroRepositorio
                .findByUsuarioIdOrderByFechaLimiteAsc(idUsuario)
                .stream()
                .map(meta -> modelMapper.map(meta, MetaAhorroDTO.class))
                .toList();
    }


    @Transactional
    public MetaAhorroDTO editarMeta(Long idMeta, MetaAhorroDTO metaAhorroDTO) {
        MetaAhorro meta = metaAhorroRepositorio.findById(idMeta)
                .orElseThrow(() -> new RuntimeException("Meta no encontrada con ID: " + idMeta));

        meta.setNombreMeta(metaAhorroDTO.getNombreMeta());
        meta.setMontoObjetivo(metaAhorroDTO.getMontoObjetivo());
        if (metaAhorroDTO.getMontoActual() != null) {
            meta.setMontoActual(metaAhorroDTO.getMontoActual());
        }
        meta.setFechaLimite(metaAhorroDTO.getFechaLimite());
        meta.setDescripcion(metaAhorroDTO.getDescripcion());

        if (meta.getMontoActual().compareTo(meta.getMontoObjetivo()) >= 0) {
            meta.setEstado("COMPLETADA");
        } else {
            meta.setEstado("ACTIVA");
        }

        meta = metaAhorroRepositorio.save(meta);
        return modelMapper.map(meta, MetaAhorroDTO.class);
    }

    public void eliminarMeta(Long idMeta) {
        if (!metaAhorroRepositorio.existsById(idMeta)) {
            throw new RuntimeException("Meta no encontrada con ID: " + idMeta);
        }
        metaAhorroRepositorio.deleteById(idMeta);
    }






    @Transactional
    public TicketSoporteDTO crearTicketSoporte(Long idUsuario,
                                               CrearTicketSoporteDTO crearTicketSoporteDTO) {

        Usuario usuario = obtenerUsuario(idUsuario);

        TicketSoporte ticketSoporte = new TicketSoporte();

        ticketSoporte.setAsunto(crearTicketSoporteDTO.getAsunto());
        ticketSoporte.setMensaje(crearTicketSoporteDTO.getMensaje());
        ticketSoporte.setEstado("ABIERTO");
        ticketSoporte.setCreadoEn(LocalDateTime.now());
        ticketSoporte.setUsuario(usuario);

        ticketSoporte = ticketSoporteRepositorio.save(ticketSoporte);

        return mapearTicket(ticketSoporte);
    }

    public List<TicketSoporteDTO> listarTicketsUsuario(Long idUsuario) {

        obtenerUsuario(idUsuario);

        return ticketSoporteRepositorio
                .findByUsuarioIdOrderByCreadoEnDesc(idUsuario)
                .stream()
                .map(this::mapearTicket)
                .toList();
    }


    private Usuario obtenerUsuario(Long idUsuario) {

        return usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario"));
    }

    private String normalizarTipo(String tipoRecibido) {

        if (tipoRecibido == null) {
            throw new RuntimeException("El tipo de movimiento es obligatorio");
        }

        String tipoNormalizado = tipoRecibido.trim().toUpperCase();

        if (tipoNormalizado.equals("INGRESO") || tipoNormalizado.equals("INCOME")) {
            return "INGRESO";
        }

        if (tipoNormalizado.equals("GASTO") || tipoNormalizado.equals("EXPENSE")) {
            return "GASTO";
        }

        throw new RuntimeException("El tipo de movimiento debe ser ingreso o gasto");
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
                ticketSoporte.getRespondidoPor() != null
                        ? ticketSoporte.getRespondidoPor().getCorreo()
                        : null
        );
    }
}