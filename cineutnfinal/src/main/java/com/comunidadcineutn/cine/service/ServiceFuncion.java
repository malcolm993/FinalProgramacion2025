/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.model.Funcion;
import com.comunidadcineutn.cine.repository.InterfaceFuncionRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author santi
 */

@Service
public class ServiceFuncion implements InterfaceServiceFuncion {

    @Autowired
    private InterfaceFuncionRepository repositoriofuncion;

    @Override
    public List<Funcion> getAllFuncion() {
        return repositoriofuncion.findAll();
    }

    @Override
    public Funcion addFuncion(Funcion f) {
        f.setHoraFin(f.getHoraInicio().plusMinutes(f.getPelicula().getDuracionMin() + 30));
        if (isHorarioOcupadoFuncion(f)) {
            throw new RuntimeException("horario ocupado");
        }
        repositoriofuncion.save(f);
        return findFuncionPorId(f.getIdFuncion()).get();
    }

    @Override
    public void deleteFuncionPorId(Integer id) {
        repositoriofuncion.deleteById(id);
    }

    @Override
    public Optional<Funcion> findFuncionPorId(Integer id) {
        return repositoriofuncion.findById(id);
    }

    @Override
    public Funcion editFuncion(Funcion f) {

        repositoriofuncion.save(f);
        return findFuncionPorId(f.getIdFuncion()).get();
    }

    @Override
    public boolean existFuncionById(Integer id) {
        return repositoriofuncion.existsById(id);
    }

    @Override
    public List<Funcion> funcionesSegunSala(Integer idSalaConsultada) {
        return repositoriofuncion.findBySalaIdSala(idSalaConsultada);
    }

    // son la fechas que se pueden dar de alta en la aplicacion
    // solamente esta habilitado la fecha de las semana siguiente
    @Override
    public List<LocalDate> fechasHabilitadas() {
        List<LocalDate> fechas = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaProximoLunes = fechaActual.with(DayOfWeek.MONDAY).plusWeeks(1);
        for (int i = 0; i < 7; i++) {
            fechas.add(fechaProximoLunes.plusDays(i));
        }
        return fechas;
    }

    @Override
    public List<Funcion> getFuncionHabilitada() {
        return repositoriofuncion.findByFuncionHabilitadaTrue();
    }

    @Override
    public boolean isHorarioOcupadoFuncion(Funcion f) {
        int salaId = f.getSala().getIdSala();
        LocalDateTime inicio = f.getHoraInicio();
        LocalDateTime fin = f.getHoraFin();
        return repositoriofuncion.existsBySalaAndOverlappingTime(salaId, inicio, fin);
    }

}
