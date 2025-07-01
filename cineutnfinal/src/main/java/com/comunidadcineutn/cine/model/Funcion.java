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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author santi
 */
@Getter
@Setter
@Entity
@Table(name = "Funcion")
public class Funcion   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFuncion;
    
    @ManyToOne
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;

    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;

    public Funcion() {
    }

    public Funcion(int idFuncion, Sala sala, Pelicula peliculaFuncion, LocalDateTime horaInicio, LocalDateTime horaFin) {
        this.idFuncion = idFuncion;
        this.sala = sala;
        this.pelicula = peliculaFuncion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

}
