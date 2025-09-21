/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
public class Funcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFuncion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @NotNull(message = "sala es obligatorio")
    private Sala sala;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    @NotNull(message = "dato Pelicula es obligatoria")
    private Pelicula pelicula;
    @NotNull
    private LocalDateTime horaInicio;
    @NotNull
    private LocalDateTime horaFin;
    private boolean funcionHabilitada;
    private int cantButacasReservadas =0;
    private int precio;

    public Funcion() {
    }

    public Funcion( Integer id ,Sala sala, Pelicula peliculaFuncion
           ,LocalDateTime inicioFuncion ) {
        this.idFuncion =id;
        this.sala = sala;
        this.pelicula = peliculaFuncion;
        this.horaInicio = inicioFuncion;
        this.horaFin = getHoraInicio().plusMinutes(peliculaFuncion.getDuracionMin()+30);
        this.funcionHabilitada = true;
        
    }

    public int getButacasDisponibles(){
        return sala.getCantDeButacas()-cantButacasReservadas;
    }

    @Override
    public String toString() {
        return "Funcion{" + "idFuncion=" + idFuncion + ", sala=" + sala + ", pelicula=" + pelicula + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + ", funcionHabilitada=" + funcionHabilitada + " ,precio:"+ precio+'}';
    }

}