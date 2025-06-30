/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.model;

import java.io.Serializable;
import java.time.LocalTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author santi
 */

@Getter @Setter
@Entity
@Table(name = "Funcion") 
public class Funcion implements Serializable{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idFuncion;
  @OneToOne
  @JoinColumn(name ="ID_SALA")
  private Sala sala;

  @OneToOne
  @JoinColumn(name = "ID_PELICULA")
  private Pelicula peliculaDeFuncion;
  private LocalDate fechaDeFuncion;
  private LocalTime horaDeFuncion;

    public Funcion() {
    }

    public Funcion(int idFuncion, Sala sala, Pelicula peliculaDeFuncion, LocalDate fechaDeFuncion, LocalTime horaDeFuncion) {
        this.idFuncion = idFuncion;
        this.sala = sala;
        this.peliculaDeFuncion = peliculaDeFuncion;
        this.fechaDeFuncion = fechaDeFuncion;
        this.horaDeFuncion = horaDeFuncion;
    }
  
    
}

