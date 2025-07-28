/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.dto.PeliculaAltaFuncionDTO;
import com.comunidadcineutn.cine.dto.PeliculaEdicionDTO;
import com.comunidadcineutn.cine.model.Pelicula;
import java.util.List;

/**
 *
 * @author santi
 */
public interface InterfaceServicePelicula  {
    
    public List<Pelicula> getAll();
    
    public Pelicula addPelicula(Pelicula p);
    
    public void deletePeliculaPorId(Integer id);
    
    public Pelicula findPeliculaPorId(Integer id);
    
    public Pelicula editPelicula(Pelicula peli);
    
    public boolean existePeliculaById (Integer id);
    
    public List<Pelicula> peliculasEnCartelera();

    public List<Pelicula> peliculasEstreno();

    public PeliculaEdicionDTO getPeliculaEdicion(Integer id);

    public List<PeliculaAltaFuncionDTO> getListadoPeliculasAltaFuncion();
}
