package com.upc.finai.servicios;

import com.upc.finai.dtos.RecomendacionDTO;
import com.upc.finai.entidades.ArticuloRecomendacion;
import com.upc.finai.repositorios.ArticuloRecomendacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioRecomendacion {

    @Autowired
    private ArticuloRecomendacionRepositorio articuloRecomendacionRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    // US04 - Recomendaciones Fiscales
    public List<RecomendacionDTO> listarRecomendacionesFiscales() {
        return articuloRecomendacionRepositorio.findByCategoriaOrderByIdAsc("FISCAL")
                .stream()
                .map(articulo -> modelMapper.map(articulo, RecomendacionDTO.class))
                .toList();
    }

    // US08 - Recursos de Inversión (Añadido)
    public List<RecomendacionDTO> listarRecursosInversion() {
        return articuloRecomendacionRepositorio.findByCategoriaOrderByIdAsc("INVERSION")
                .stream()
                .map(articulo -> modelMapper.map(articulo, RecomendacionDTO.class))
                .toList();
    }
}
