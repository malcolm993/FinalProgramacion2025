/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.exception.ExceptionNotFound;
import com.comunidadcineutn.cine.model.Reserva;
import com.comunidadcineutn.cine.repository.InterfaceReservaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author santi
 */

@Service
public class ServiceReserva implements InterfaceServiceReserva {
    @Autowired
    private InterfaceReservaRepository repositorioReservas;

    @Override
    public List<Reserva> getAll() {
        return repositorioReservas.findAll();
    }

    @Override
    public Reserva addPelicula(Reserva r) {
        return repositorioReservas.save(r);
    }

    @Override
    public void deleteReservaPorId(Integer id) {
        repositorioReservas.deleteById(id);
    }

    @Override
    public Reserva findReservaPorId(Integer id) {
        return repositorioReservas.findById(id).orElseThrow(
            () -> new ExceptionNotFound("No existe reserva que tengan el Id ingresado"));
    }

    @Override
    public Reserva editPelicula(Reserva res) {
        return repositorioReservas.save(res);

    }

}
