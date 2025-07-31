
package com.comunidadcineutn.cine.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.comunidadcineutn.cine.model.Pelicula;
import com.comunidadcineutn.cine.model.Sala;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionAltaDTO {

  Integer id;

  @NotNull
  private Sala sala;
  @NotNull
  private Pelicula pelicula;
  @NotNull
  private LocalDate fechaFuncion;
  @NotNull
  private LocalTime horaFuncion;
  
  public FuncionAltaDTO(){}

  public FuncionAltaDTO(Integer id, Sala sala, Pelicula pelicula, LocalDate fecha , LocalTime horario){
    this.id = id;
    this.sala= sala;
    this.pelicula = pelicula;
    this.fechaFuncion = fecha;

  }
}