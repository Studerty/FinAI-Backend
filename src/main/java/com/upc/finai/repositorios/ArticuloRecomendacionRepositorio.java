package com.upc.finai.repositorios;

import com.upc.finai.entidades.ArticuloRecomendacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticuloRecomendacionRepositorio extends JpaRepository<ArticuloRecomendacion, Long> {
    List<ArticuloRecomendacion> findByCategoriaOrderByIdAsc(String categoria);
}

