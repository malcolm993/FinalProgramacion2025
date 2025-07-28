package com.comunidadcineutn.cine.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PeliculaAltaFuncionDTO {

  private Integer id;

  @NotBlank
  private String nombre;

  public PeliculaAltaFuncionDTO() {
  }

  public PeliculaAltaFuncionDTO(Integer id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

}
