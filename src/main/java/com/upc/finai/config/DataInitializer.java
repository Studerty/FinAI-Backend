package com.upc.finai.config;

import com.upc.finai.entidades.ArticuloRecomendacion;
import com.upc.finai.repositorios.ArticuloRecomendacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ArticuloRecomendacionRepositorio articuloRecomendacionRepositorio;

    @Override
    public void run(String... args) {
        cargarRecomendacionesFiscales();
        cargarRecursosInversion();
    }

    private void cargarRecomendacionesFiscales() {
        if (!articuloRecomendacionRepositorio.findByCategoriaOrderByIdAsc("FISCAL").isEmpty()) {
            return;
        }

        guardarArticulo(
                "Guía tributaria SUNAT",
                "Información general para revisar obligaciones tributarias.",
                "https://www.sunat.gob.pe",
                "FISCAL"
        );

        guardarArticulo(
                "Calendario tributario SUNAT",
                "Consulta de fechas y obligaciones tributarias.",
                "https://www.sunat.gob.pe",
                "FISCAL"
        );

        guardarArticulo(
                "Orientación tributaria SUNAT",
                "Orientación para declaraciones y pagos.",
                "https://orientacion.sunat.gob.pe",
                "FISCAL"
        );
    }

    private void cargarRecursosInversion() {
        if (!articuloRecomendacionRepositorio.findByCategoriaOrderByIdAsc("INVERSION").isEmpty()) {
            return;
        }

        guardarArticulo(
                "Conceptos básicos de inversión",
                "Recurso introductorio para aprender conceptos básicos de inversión.",
                "https://www.sbs.gob.pe",
                "INVERSION"
        );

        guardarArticulo(
                "Educación financiera",
                "Información sobre educación financiera e inversión responsable.",
                "https://www.sbs.gob.pe",
                "INVERSION"
        );

        guardarArticulo(
                "Riesgos al invertir",
                "Recurso para entender riesgos financieros antes de invertir.",
                "https://www.smv.gob.pe",
                "INVERSION"
        );
    }

    private void guardarArticulo(String titulo, String descripcionCorta, String urlEnlace, String categoria) {
        ArticuloRecomendacion articulo = new ArticuloRecomendacion();
        articulo.setTitulo(titulo);
        articulo.setDescripcionCorta(descripcionCorta);
        articulo.setUrlEnlace(urlEnlace);
        articulo.setCategoria(categoria);

        articuloRecomendacionRepositorio.save(articulo);
    }
}