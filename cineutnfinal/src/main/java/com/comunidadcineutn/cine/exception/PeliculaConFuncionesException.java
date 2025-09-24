package com.comunidadcineutn.cine.exception;

import lombok.Getter;
@Getter

public class PeliculaConFuncionesException extends RuntimeException{
  
  
  private final String errorMensaje;

  public PeliculaConFuncionesException (final String error) {
    this.errorMensaje = error;
  }
}
