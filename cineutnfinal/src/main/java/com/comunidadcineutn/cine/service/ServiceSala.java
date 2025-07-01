/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunidadcineutn.cine.model.Sala;
import com.comunidadcineutn.cine.repository.InterfaceSalaRepository;

/**
 *
 * @author santi
 */

@Service
public class ServiceSala implements InterfaceServiceSala{

  @Autowired
  private InterfaceSalaRepository repositoriosala;

  @Override
  public List<Sala> getAllSalas() {
    // TODO Auto-generated method stub
    return  repositoriosala.findAll();
  }

  @Override
  public Sala addSala(Sala s) {
    // TODO Auto-generated method stub
    repositoriosala.save(s);
    return repositoriosala.findById(s.getIdSala()).get();
  }

  @Override
  public void deleteSalaPorId(Integer id) {
    // TODO Auto-generated method stub
    repositoriosala.deleteById(id);
  }

  @Override
  public Optional<Sala> findSalaPorId(Integer id) {
    // TODO Auto-generated method stub
    return repositoriosala.findById(id);
  }

  @Override
  public Sala editSala(Sala s) {
    // TODO Auto-generated method stub
    repositoriosala.save(s);
    return findSalaPorId(s.getIdSala()).get();
  }

  @Override
  public boolean existSalaById(Integer id) {
    // TODO Auto-generated method stub
    return repositoriosala.existsById(id);
  }

  @Override
  public boolean capacitySala(Integer id , int cantidadButacasReservar) {
    Sala salaAux = findSalaPorId(id).get();
    int disponibles =  salaAux.getCantDeButacas() - salaAux.getCantDeButacasReservadas();
    return cantidadButacasReservar < disponibles;
    
  }
    
}
