package com.comunidadcineutn.cine.dto;

import java.time.LocalDate;


import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public class PeliculaEdicionDTO {

  private Integer id;
  @NotNull(message = "es obligatorio la duracion de la peliculas y en minutos")
  private int duracionMin;
  @NotBlank
  private String nombre;

  private String sinopsis;
  @NotBlank
  private String calif;
  // el dato fechaEstreno no refiere a la fecha cunado se estrena en el cine, cino
  // cuando se estreno
  // la pelicula ya que son peliculas ya estrenadas.
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate fechaEstreno;

  @NotBlank
  private String director;

  @NotNull
  private boolean cartelera;

  public PeliculaEdicionDTO() {
    
  }



  public PeliculaEdicionDTO(int duracionMin, String nombre_pelicula, String sinopsis, String cal,
      LocalDate fechaDeEstreno, String director, boolean bol) {

    this.duracionMin = duracionMin;
    this.nombre = nombre_pelicula;
    this.sinopsis = sinopsis;
    this.calif = cal;
    this.fechaEstreno = fechaDeEstreno;
    this.director = director;
    this.cartelera = bol;

  }

    @Override
    public String toString() {
        return "PeliculaEdicionDTO{" + "id=" + id + ", duracionMin=" + duracionMin + ", nombre=" + nombre + ", sinopsis=" + sinopsis + ", calif=" + calif + ", fechaEstreno=" + fechaEstreno + ", director=" + director + ", cartelera=" + cartelera + '}';
    }


}
