/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.dto.ReservaFormDTO;
import com.comunidadcineutn.cine.exception.ButacasInsuficientesException;
import com.comunidadcineutn.cine.exception.ExceptionNotFound;
import com.comunidadcineutn.cine.model.Funcion;
import com.comunidadcineutn.cine.model.Reserva;
import com.comunidadcineutn.cine.model.Usuario;
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

    @Autowired
    private InterfaceServiceFuncion funcionService;

    @Override
    public List<Reserva> getAll() {
        return repositorioReservas.findAll();
    }

    @Override
    public Reserva addReserva(Reserva r) {
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
    public Reserva editReserva(Reserva res) {
        return repositorioReservas.save(res);

    }

    @Override
    public Reserva crearReservaFromDTO(Usuario u, ReservaFormDTO r) {
        // TODO Auto-generated method stub
        Funcion funcion = funcionService.findFuncionPorId(r.getIdFuncion());
        int costoTotal = funcion.getPrecio() * r.getCantidadEntradas();
        Reserva toReservaEntidad = new Reserva();
        if(r.getCantidadEntradas()> funcion.getButacasDisponibles()){
            throw new ButacasInsuficientesException("No hay cantidad de entradas pedidas excede las disponibles");
        }
        
        toReservaEntidad.setFuncionReservada(funcion);
        toReservaEntidad.setUsuarioComprador(u);
        toReservaEntidad.setCantidadEntradas(r.getCantidadEntradas());
        toReservaEntidad.setCostoReserva(costoTotal);

        return toReservaEntidad;
    }

    @Override
    public List<Reserva> getRerservasPorIdUsario(Integer id) {
        return repositorioReservas.findByUsuarioCompradorId(id);
        
    }

}
