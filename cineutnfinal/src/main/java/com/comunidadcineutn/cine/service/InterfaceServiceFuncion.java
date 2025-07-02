/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.comunidadcineutn.cine.service;


import com.comunidadcineutn.cine.model.Funcion;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author santi
 */
public interface InterfaceServiceFuncion {
    
    public List<Funcion> getAllFuncion();
    
    public Funcion addFuncion(Funcion f);
    
    public void deleteFuncionPorId(Integer id);
    
    public Optional<Funcion> findFuncionPorId(Integer id);
    
    public Funcion editFuncion(Funcion f);
    
    public boolean existFuncionById (Integer id);
    
    public List<Funcion> funcionesSegunSala(Integer idSala);

    public List<LocalDate> fechasHabilitadas();

    public List<Funcion> getFuncionHabilitada();

}
