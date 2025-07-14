/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.comunidadcineutn.cine.service;


import com.comunidadcineutn.cine.model.Reserva;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author santi
 */
public interface InterfaceServiceReserva {
    
    public List<Reserva> getAll();
    
    public Reserva addPelicula(Reserva r);
    
    public void deleteReservaPorId(Integer id);
    
    public Optional<Reserva> findReservaPorId(Integer id);
    
    public Reserva editPelicula(Reserva res);
}
