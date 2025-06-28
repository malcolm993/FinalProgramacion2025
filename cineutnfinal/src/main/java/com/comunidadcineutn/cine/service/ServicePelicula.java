/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.model.Pelicula;
import com.comunidadcineutn.cine.repository.InterfacePeliculaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author santi
 */
@Service
public class ServicePelicula implements InterfaceServicePelicula {
    @Autowired
    private InterfacePeliculaRepository repositorioPelicula;
    
    @Override
    public List<Pelicula> getAll() {
        return repositorioPelicula.findAll();   
    }

    @Override
    public Pelicula addPelicula(Pelicula p) {
        repositorioPelicula.save(p);
        return findPeliculaPorId(p.getIdPelicula()).get();
    }

    @Override
    public void deletePeliculaPorId(Integer id) {
      repositorioPelicula.deleteById(id);
    }

    @Override
    public Optional<Pelicula> findPeliculaPorId(Integer id) {
       return repositorioPelicula.findById(id);
    }

    @Override
    public Pelicula editPelicula(Pelicula peli) {
        repositorioPelicula.save(peli);
        return peli;
    }
    
 

    @Override
    public boolean existePeliculaById(Integer id) {
     return repositorioPelicula.existsById(id);
    }

    @Override
    public List<Pelicula> peliculasEnCartelera() {
     return repositorioPelicula.findByIs_carteleraTrue();
    }  
}
