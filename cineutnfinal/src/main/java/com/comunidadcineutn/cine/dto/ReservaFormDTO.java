package com.comunidadcineutn.cine.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReservaFormDTO {

    @NotNull(message = "El ID de función es requerido")
    @Positive(message = "El ID de función debe ser válido")
    private Integer idFuncion;

    @NotNull(message = "La cantidad de entradas es requerida")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidadEntradas;

    // Constructores
    public ReservaFormDTO() {
    }

    public ReservaFormDTO(Integer idFuncion, Integer cantidadEntradas) {
        this.idFuncion = idFuncion;
        this.cantidadEntradas = cantidadEntradas;
    }

    // Getters y Setters
    public Integer getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(Integer idFuncion) {
        this.idFuncion = idFuncion;
    }

    public Integer getCantidadEntradas() {
        return cantidadEntradas;
    }

    public void setCantidadEntradas(Integer cantidadEntradas) {
        this.cantidadEntradas = cantidadEntradas;
    }

    // Métodos auxiliares
    @Override
    public String toString() {
        return "ReservaFormDTO{" +
                "idFuncion=" + idFuncion +
                ", cantidadEntradas=" + cantidadEntradas +
                '}';
    }
}