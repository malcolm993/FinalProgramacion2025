/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.service;
import com.comunidadcineutn.cine.dto.PeliculaAltaFuncionDTO;
import com.comunidadcineutn.cine.dto.PeliculaEdicionDTO;
import com.comunidadcineutn.cine.exception.ExceptionNotFound;

import com.comunidadcineutn.cine.model.Pelicula;
import com.comunidadcineutn.cine.repository.InterfacePeliculaRepository;

import java.util.ArrayList;
import java.util.List;

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
        return repositorioPelicula.save(p);
    }

    @Override
    public void deletePeliculaPorId(Integer id) {
        repositorioPelicula.deleteById(id);
    }

    @Override
    public Pelicula findPeliculaPorId(Integer id) {
        return repositorioPelicula.findById(id).orElseThrow(
                () -> new ExceptionNotFound("No existe pelicula con el Id ingresado"));
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
        return repositorioPelicula.findByCarteleraTrue();
    }

    @Override
    public List<Pelicula> peliculasEstreno() {
        return repositorioPelicula.findByCarteleraFalse();
    }

    @Override
    public PeliculaEdicionDTO getPeliculaEdicion(Integer id) {
        Pelicula p = findPeliculaPorId(id);
        return conversionPeliculaDTO(p);

    }

    private PeliculaEdicionDTO conversionPeliculaDTO(Pelicula p) {
        PeliculaEdicionDTO pdto = new PeliculaEdicionDTO();
        pdto.setId(p.getIdPelicula());
        pdto.setNombre(p.getNombre());
        pdto.setDuracionMin(p.getDuracionMin());
        pdto.setDirector(p.getDirector());
        pdto.setCalif(p.getCalif());
        pdto.setFechaEstreno(p.getFechaEstreno());
        pdto.setCartelera(p.isCartelera());
        pdto.setSinopsis(p.getSinopsis());
        pdto.setCantidadDeFuncionesAsociadas(p.getFuncionesAsociadas().size());
        return pdto;

    }

    @Override
    public List<PeliculaAltaFuncionDTO> getListadoPeliculasAltaFuncion() {
        List<Pelicula> todas = getAll();
        List<PeliculaAltaFuncionDTO> listaDTO = new ArrayList<>();
        for (Pelicula p : todas) {
            listaDTO.add(conversionPeliculaFuncionDTO(p));
        }

        return listaDTO;
    }

    private PeliculaAltaFuncionDTO conversionPeliculaFuncionDTO(Pelicula p) {
        PeliculaAltaFuncionDTO pdto = new PeliculaAltaFuncionDTO();
        pdto.setId(p.getIdPelicula());
        pdto.setNombre(p.getNombre());

        return pdto;
    }

    @Override
    public Pelicula actualizarPelicula(Integer id, PeliculaEdicionDTO p) {
        Pelicula sinActualizar = findPeliculaPorId(id);
        mapeoPeliculaDtoToPelicula(sinActualizar, p);
        return repositorioPelicula.save(sinActualizar);
    }

    private void mapeoPeliculaDtoToPelicula(Pelicula sinActualizar, PeliculaEdicionDTO dtoP) {
        sinActualizar.setIdPelicula(dtoP.getId());
        sinActualizar.setDuracionMin(dtoP.getDuracionMin());
        sinActualizar.setNombre(dtoP.getNombre());
        sinActualizar.setSinopsis(dtoP.getSinopsis());
        sinActualizar.setFechaEstreno(dtoP.getFechaEstreno());
        sinActualizar.setDirector(dtoP.getDirector());
        sinActualizar.setCalif(dtoP.getCalif());
    }

    @Override
    public void cambioPeliculaToCartelera(Integer id) {
        Pelicula p = findPeliculaPorId(id);
        p.setCartelera(true);
        repositorioPelicula.save(p);
    }

}
