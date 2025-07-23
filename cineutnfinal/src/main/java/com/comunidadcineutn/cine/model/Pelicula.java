/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.model;

/**
 *
 * @author santi
 */
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
@Entity
@Table(name = "Pelicula")
public class Pelicula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idPelicula;
    @NotNull (message = "es obligatorio la duracion de la peliculas y en minutos")
    private int duracionMin;
    @NotBlank
    private String nombre;

    private String sinopsis;

    private String calif;
    //el dato fechaEstreno no refiere a la fecha cunado se estrena en el cine, cino cuando se estreno 
    //la pelicula ya que son peliculas ya estrenadas.

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEstreno;

    private String director;

   private boolean cartelera;
   
   @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL , orphanRemoval = true)
   private List<Funcion> funcionesAsociadas;
   
    public Pelicula() {
        this( 0, "", "", "APTA", LocalDate.now().toString(), "", false);
    }

    public Pelicula(int id) {
        this.idPelicula = id;
    }

    public Pelicula( int duracionMin, String nombre_pelicula, String sinopsis, String cal,
            String fechaDeEstreno, String director, boolean bol) {
       
        this.duracionMin = duracionMin;
        this.nombre = nombre_pelicula;
        this.sinopsis = sinopsis;
        this.calif = cal;
        setFechaDeEstreno(fechaDeEstreno);
        this.director = director;
        this.cartelera = bol;

    }
    
    public void setFechaDeEstreno(String fechaDeEstreno){
        fechaEstreno = LocalDate.parse(fechaDeEstreno);
    }
    
}