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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
@Entity
@Table(name = "Pelicula")
public class Pelicula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idPelicula;

    private int duracionMin;

    private String nombre;

    private String sinopsis;

    private String calif;

    private LocalDate fechaEstreno;

    private String director;

   private boolean cartelera;
   
    public Pelicula() {
        this(0, 0, "", "", "APTA", LocalDate.now().toString(), "", false);
    }

    public Pelicula(int id) {
        this.idPelicula = id;
    }

    public Pelicula(int id, int duracionMin, String nombre_pelicula, String sinopsis, String cal,
            String fechaDeEstreno, String director, boolean bol) {
        this.idPelicula = id;
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