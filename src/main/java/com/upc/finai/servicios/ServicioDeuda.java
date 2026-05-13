package com.upc.finai.servicios;

import com.upc.finai.dtos.DeudaDTO;
import com.upc.finai.entidades.Deuda;
import com.upc.finai.repositorios.DeudaRepositorio;
import com.upc.finai.seguridad.entidades.Usuario;
import com.upc.finai.seguridad.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ServicioDeuda {

    @Autowired private DeudaRepositorio deudaRepositorio;
    @Autowired private UsuarioRepositorio usuarioRepositorio;

   
    @Transactional
    public Deuda registrarDeuda(DeudaDTO dto, String correoUsuario) {
        Usuario usuario = usuarioRepositorio.findByCorreo(correoUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Deuda deuda = new Deuda();
        deuda.setAcreedor(dto.getAcreedor());
        deuda.setMontoTotal(dto.getMontoTotal());
        deuda.setTasaInteres(dto.getTasaInteres());
        deuda.setFechaVencimiento(dto.getFechaVencimiento());
        deuda.setUsuario(usuario);

        return deudaRepositorio.save(deuda);
    }

  
    public List<Deuda> listarDeudas(String correoUsuario) {
        Usuario usuario = usuarioRepositorio.findByCorreo(correoUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return deudaRepositorio.findByUsuarioId(usuario.getId());
    }

   
    public Map<String, Object> obtenerSaludCrediticia(String correoUsuario) {
        Usuario usuario = usuarioRepositorio.findByCorreo(correoUsuario).orElseThrow();
        List<Deuda> deudas = deudaRepositorio.findByUsuarioId(usuario.getId());
        double deudaTotal = deudas.stream().mapToDouble(d -> d.getMontoTotal().doubleValue()).sum();

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("scoreEstimado", deudaTotal > 5000 ? 600 : 750);
        resultado.put("calificacion", deudaTotal > 5000 ? "RIESGOSA" : "SALUDABLE");
        return resultado;
    }

    public Map<String, Object> simularPrestamo(Double monto, Double tasaAnual, Integer meses) {
        double tasaMensual = (tasaAnual / 100) / 12;
        double cuota = (monto * tasaMensual) / (1 - Math.pow(1 + tasaMensual, -meses));
        return Map.of(
                "cuotaMensual", Math.round(cuota * 100.0) / 100.0,
                "totalAPagar", Math.round((cuota * meses) * 100.0) / 100.0
        );
    }
}
