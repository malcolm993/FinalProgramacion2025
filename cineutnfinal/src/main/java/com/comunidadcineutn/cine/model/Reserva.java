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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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
@Table(name = "reservas")
public class Reserva {

    private final int CANTIDAD_DIAS_LIMITE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Funcion funcionReservada;

    @ManyToOne (fetch = FetchType.EAGER)
    @NotNull
    private Usuario usuarioComprador;
    private int costoReserva;

    @Positive(message = "La cantidad debe ser mayor a 0")
    private int cantidadEntradas;

    private LocalDateTime fechaHoraCreacion;

    public Reserva() {
    }

    public Reserva(Funcion funcionReservada, Usuario usuarioComprador, int costoReserva, int cantidadEntradas) {

        this.funcionReservada = funcionReservada;
        this.usuarioComprador = usuarioComprador;
        this.costoReserva = costoReserva;
        this.cantidadEntradas = cantidadEntradas;
    }
    @PrePersist
    protected void onCreate() {
        if (fechaHoraCreacion == null) {
            fechaHoraCreacion = LocalDateTime.now();
        }
    }
    
    @Transient
    public boolean isCancelable() {
        return funcionReservada != null && 
        LocalDate.now().isBefore(funcionReservada.getHoraInicio().toLocalDate().minusDays(CANTIDAD_DIAS_LIMITE));
    }

}
