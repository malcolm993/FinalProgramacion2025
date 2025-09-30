/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.comunidadcineutn.cine.service;


import com.comunidadcineutn.cine.dto.FuncionAltaDTO;
import com.comunidadcineutn.cine.model.Funcion;

import java.time.LocalDate;

import java.util.List;


/**
 *
 * @author santi
 */
public interface InterfaceServiceFuncion {
    
    public List<Funcion> getAllFuncion();
    
    public Funcion addFuncion(Funcion f);
    
    public void deleteFuncionPorId(Integer id);
    
    public Funcion findFuncionPorId(Integer id);
    
    public Funcion editFuncion(Funcion f);
    
    public boolean existFuncionById (Integer id);
    
    public List<Funcion> funcionesSegunSala(Integer idSala);

    public List<LocalDate> fechasHabilitadas();

    public List<Funcion> getAllFuncionesHabilitadas();

    public boolean isHorarioOcupadoFuncion(Funcion funcionNueva);

    public FuncionAltaDTO getFuncionEdicion(Integer id);

    public List<Funcion> getListaFuncionesPorIdPelicula(Integer id);

    public Funcion toFuncionFromFuncionDTO (FuncionAltaDTO f);

    public List<Funcion> getListaFuncionesHabilitadasPorId(Integer id);
}
