package com.comunidadcineutn.cine.exception;

import lombok.Getter;

@Getter
public class FechaHorarioNoDisponible extends RuntimeException{
  
  private final String errorMensaje;

  public FechaHorarioNoDisponible(final String error) {
    super(error);
    this.errorMensaje = error;
  }
}
