/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.model.Pelicula;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author santi
 */
public interface InterfaceServicePelicula  {
    
    public List<Pelicula> getAll();
    
    public Pelicula addPelicula(Pelicula p);
    
    public void deletePeliculaPorId(Integer id);
    
    public Optional<Pelicula> findPeliculaPorId(Integer id);
    
    public Pelicula editPelicula(Pelicula peli);
    
    public boolean existePeliculaById (Integer id);
    
    public List<Pelicula> findIsPeliculasCartelera();
}
