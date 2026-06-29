package com.upc.finai.servicios;

import com.upc.finai.dtos.AlertaDTO;
import com.upc.finai.dtos.ReporteFinancieroDTO;
import com.upc.finai.entidades.Movimiento;
import com.upc.finai.repositorios.MovimientoRepositorio;
import com.upc.finai.seguridad.entidades.Usuario;
import com.upc.finai.seguridad.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServicioAnaliticas {

    @Autowired private MovimientoRepositorio movimientoRepo;
    @Autowired private UsuarioRepositorio usuarioRepo;

    public ReporteFinancieroDTO obtenerReporte(String correo) {
        Usuario usuario = usuarioRepo.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Movimiento> movimientos = movimientoRepo.findByUsuarioIdOrderByCreadoEnDesc(usuario.getId());

        ReporteFinancieroDTO reporte = new ReporteFinancieroDTO();

        // 1. Calculamos INGRESOS
        double ingresos = movimientos.stream()
                .filter(m -> m.getTipo() != null && "INGRESO".equalsIgnoreCase(m.getTipo().trim()))
                .mapToDouble(m -> m.getMonto().doubleValue()).sum();

        // 2. Calculamos EGRESOS
        double egresos = movimientos.stream()
                .filter(m -> m.getTipo() != null && "GASTO".equalsIgnoreCase(m.getTipo().trim()))
                .mapToDouble(m -> m.getMonto().doubleValue()).sum();

        // 3. IA - Gastos No Esenciales (Blindado contra mayúsculas/minúsculas y espacios)
        List<String> noEsenciales = List.of("STREAMING", "DELIVERY", "CINE", "JUEGOS", "DIVERSION", "DIVERSIÓN");

        double montoInnecesario = movimientos.stream()
                .filter(m -> m.getTipo() != null && "GASTO".equalsIgnoreCase(m.getTipo().trim()))
                .filter(m -> m.getCategoria() != null && noEsenciales.contains(m.getCategoria().toUpperCase().trim()))
                .mapToDouble(m -> m.getMonto().doubleValue()).sum();

        // 4. Llenamos el DTO
        reporte.setTotalMovimientos(movimientos.size());
        reporte.setTotalIngresos(ingresos);
        reporte.setTotalGastos(egresos);
        reporte.setAhorroNeto(ingresos - egresos);

        // Seteamos los valores de la "IA"
        reporte.setMontoGastosNoEsenciales(montoInnecesario);
        reporte.setAhorroPotencial(montoInnecesario * 0.8);

        // Agrupación para gráficos (Evitando nulls en categorías)
        Map<String, Double> porCat = movimientos.stream()
                .filter(m -> m.getTipo() != null && "GASTO".equalsIgnoreCase(m.getTipo().trim()))
                .filter(m -> m.getCategoria() != null)
                .collect(Collectors.groupingBy(
                        m -> m.getCategoria().trim(),
                        Collectors.summingDouble(m -> m.getMonto().doubleValue())
                ));
        reporte.setGastosPorCategoria(porCat);

        return reporte;
    }

    public List<AlertaDTO> generarAlertas(String correo) {
        Usuario usuario = usuarioRepo.findByCorreo(correo).orElseThrow();
        List<Movimiento> movimientos = movimientoRepo.findByUsuarioIdOrderByCreadoEnDesc(usuario.getId());
        List<AlertaDTO> alertas = new ArrayList<>();

        // Alerta de Gastos Hormiga
        long gastosHormiga = movimientos.stream()
                .filter(m -> m.getTipo() != null && "GASTO".equalsIgnoreCase(m.getTipo().trim()))
                .filter(m -> m.getMonto().doubleValue() < 15.0)
                .count();

        if (gastosHormiga > 5) {
            alertas.add(new AlertaDTO("GASTO_HORMIGA", "¡Alerta! Tienes muchos egresos pequeños acumulados.", "ALTO"));
        }

        return alertas;
    }
}