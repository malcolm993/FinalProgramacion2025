package com.comunidadcineutn.cine.exception;

import lombok.Getter;

@Getter
public class ButacasInsuficientesException extends RuntimeException {

  private final String errorMensaje;

  public ButacasInsuficientesException(final String error) {
    super(error);
    this.errorMensaje = error;
  }
}
