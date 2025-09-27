package com.comunidadcineutn.cine.exception;

import lombok.Getter;

@Getter
public class ExceptionNotFound extends RuntimeException {
  
  private final String errorMensaje;

  public ExceptionNotFound (final String error){
    super(error);
    this.errorMensaje=error;
  }
}
