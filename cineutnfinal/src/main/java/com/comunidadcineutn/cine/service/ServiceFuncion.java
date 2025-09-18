/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.dto.FuncionAltaDTO;
import com.comunidadcineutn.cine.exception.ExceptionNotFound;
import com.comunidadcineutn.cine.model.Funcion;
import com.comunidadcineutn.cine.model.TipoDeSala;
import com.comunidadcineutn.cine.repository.InterfaceFuncionRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author santi
 */

@Service
public class ServiceFuncion implements InterfaceServiceFuncion {

    private static final int PRECIO_SALA2D = 1000;
    private static final int PRECIO_SALA3D = 2000;
    @Autowired
    private InterfaceFuncionRepository repositoriofuncion;

    @Override
    public List<Funcion> getAllFuncion() {
        return repositoriofuncion.findAll();
    }

    @Override
    public Funcion addFuncion(Funcion f) {
       // f.setHoraFin(f.getHoraInicio().plusMinutes(f.getPelicula().getDuracionMin() + 30));

       System.out.println("booleano si hay horario disponibles "+isHorarioOcupadoFuncion(f));
        if (isHorarioOcupadoFuncion(f)) {
            throw new RuntimeException("horario ocupado");
        }
        if(f.getSala().getTipoSala().equals(TipoDeSala._2D)){
            f.setPrecio(PRECIO_SALA2D);
        }else{
            f.setPrecio(PRECIO_SALA3D);
        }
        return repositoriofuncion.save(f);
    }

    @Override
    public void deleteFuncionPorId(Integer id) {
        repositoriofuncion.deleteById(id);
    }

    @Override
    public Funcion findFuncionPorId(Integer id) {
        
        return repositoriofuncion.findById(id).orElseThrow(
            ()-> new ExceptionNotFound("No existe la funcion el Id ingresado") );
    }

    @Override
    public Funcion editFuncion(Funcion f) {
        
        repositoriofuncion.save(f);
        return findFuncionPorId(f.getIdFuncion());
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

    @Override
    public FuncionAltaDTO getFuncionEdicion(Integer id){
        Funcion f = findFuncionPorId(id);
        FuncionAltaDTO funcionE = new FuncionAltaDTO
            (f.getIdFuncion(),
            f.getSala(),
            f.getPelicula(),
            f.getHoraInicio().toLocalDate(),
            f.getHoraInicio().toLocalTime()) ;

        return funcionE;
    }

    

}
