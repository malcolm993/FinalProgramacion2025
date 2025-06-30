/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.model.Funcion;
import com.comunidadcineutn.cine.model.TipoDeSala;
import com.comunidadcineutn.cine.repository.InterfaceFuncionRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author santi
 */

 @Service
public class ServiceFuncion implements InterfaceServiceFuncion{

    @Autowired
    private InterfaceFuncionRepository repositoriofuncion;

    @Override
    public List<Funcion> getAllFuncion() {
        return repositoriofuncion.findAll();
    }

    @Override
    public Funcion addFuncion(Funcion f) {
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
        return repositoriofuncion.findAllByIdSala(idSalaConsultada);
    }




}
