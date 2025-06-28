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
    @Column(name = "id_pelicula")
    private int idPelicula;
    @Column(name = "duracion_min")
    private int duracion_min;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "sinopsis")
    private String sinopsis;
    @Column(name = "apto_publico")
    private String calif;
    @Column(name = "fecha_estreno")
    private LocalDate fecha_estreno;
    @Column(name = "director")
    private String director;
    
   @Column(name = "is_cartelera")
   private boolean is_cartelera;
   
    public Pelicula() {
        this(0, 0, "", "", "APTA", LocalDate.now().toString(), "", false);
    }

    public Pelicula(int id) {
        this.idPelicula = id;
    }

    public Pelicula(int id, int duracion_min, String nombre_pelicula, String sinopsis, String cal,
            String fechaDeEstreno, String director, boolean bol) {
        this.idPelicula = id;
        this.duracion_min = duracion_min;
        this.nombre = nombre_pelicula;
        this.sinopsis = sinopsis;
        this.calif = cal;
        setFechaDeEstreno(fechaDeEstreno);
        this.director = director;

    }
    
    public void setFechaDeEstreno(String fechaDeEstreno) {
        this.fecha_estreno = LocalDate.parse(fechaDeEstreno);
    }
    
}