/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
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
public class Reservas {

    private final int CANTIDAD_DIAS_LIMITE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotNull
    private Funcion funcionReservada;

    @ManyToOne
    @NotNull
    private Usuario usuarioComprador;
    private int costoReserva;
    private int cantidadEntradas;

    public Reservas() {
    }

    public Reservas(Funcion funcionReservada, Usuario usuarioComprador, int costoReserva, int cantidadEntradas) {

        this.funcionReservada = funcionReservada;
        this.usuarioComprador = usuarioComprador;
        this.costoReserva = costoReserva;
        this.cantidadEntradas = cantidadEntradas;
    }
    
    @Transient
    public boolean isCancelable() {
        return funcionReservada != null && 
        LocalDate.now().isBefore(funcionReservada.getHoraInicio().toLocalDate().minusDays(CANTIDAD_DIAS_LIMITE));
    }

}
