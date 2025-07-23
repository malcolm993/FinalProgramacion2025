package com.comunidadcineutn.cine.exception;

import lombok.Getter;

@Getter
public class ExceptionPeliculas extends RuntimeException {
  
  private final String errorMensaje;

  public ExceptionPeliculas (final String error){
    this.errorMensaje=error;
  }
}
