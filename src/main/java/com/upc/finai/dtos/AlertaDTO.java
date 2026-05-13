package com.upc.finai.dtos;


public class AlertaDTO {

    private String tipo;
    private String mensaje;
    private String nivelGravedad;


    public AlertaDTO(String tipo, String mensaje, String nivelGravedad) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.nivelGravedad = nivelGravedad;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNivelGravedad() {
        return nivelGravedad;
    }

    public void setNivelGravedad(String nivelGravedad) {
        this.nivelGravedad = nivelGravedad;
    }
}