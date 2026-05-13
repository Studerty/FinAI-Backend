package com.upc.finai.controladores;

import com.upc.finai.dtos.RecomendacionDTO;
import com.upc.finai.servicios.ServicioRecomendacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recomendaciones")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class ControladorRecomendacion {

    @Autowired
    private ServicioRecomendacion servicioRecomendacion;


    @GetMapping("/fiscales")
    public List<RecomendacionDTO> listarRecomendacionesFiscales() {
        return servicioRecomendacion.listarRecomendacionesFiscales();
    }

    
    @GetMapping("/inversion")
    public List<RecomendacionDTO> listarRecursosInversion() {
        return servicioRecomendacion.listarRecursosInversion();
    }
}
