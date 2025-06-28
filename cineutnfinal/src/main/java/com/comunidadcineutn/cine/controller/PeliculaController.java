/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.controller;

import com.comunidadcineutn.cine.model.Pelicula;
import com.comunidadcineutn.cine.service.InterfaceServicePelicula;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author santi
 */
@Tag(name = "Películas", description = "API para gestión de películas")
@RequestMapping("/pelicula")
@RestController
public class PeliculaController {

    @Autowired
    private InterfaceServicePelicula peliculaService;

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Obtener película por ID")
    public ResponseEntity<Pelicula> buscarPeliculaPorId(@PathVariable Integer id) {
        return peliculaService.findPeliculaPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/todas")
    @Operation(summary = "Obtener todas las peliculas")
    public ResponseEntity<List<Pelicula>> getPeliculas() {
        List listaPeliculas = peliculaService.getAll();
        return new ResponseEntity<>(listaPeliculas, HttpStatus.ACCEPTED);
    }

    @PostMapping("/agregar")
    @Operation(summary = "Agregar pelicula")
    public ResponseEntity<Pelicula> agregarPelicula(@RequestBody Pelicula p) {
        Pelicula nueva = peliculaService.addPelicula(p);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar")
    @Operation(summary = "Eliminar película por ID")
    public ResponseEntity<?> eliminarPelicula(@RequestParam(required = true, name= "id") Integer id) {
        return peliculaService.findPeliculaPorId(id)
                .map(pelicula -> {
                    peliculaService.deletePeliculaPorId(id);
                    return ResponseEntity.ok().body("Película '" + pelicula.getNombre() + "' eliminada correctamente");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/editar/{id}")
    @Operation(summary = "Editar película")
    public ResponseEntity<Pelicula> actualizarPelicula(
            @PathVariable Integer id,
            @Valid
            @RequestBody Pelicula pelicula) {

        if (!peliculaService.existePeliculaById(id)) {
            return ResponseEntity.notFound().build();
        }

        pelicula.setIdPelicula(id); // Asegurar que se actualice la película correcta
        Pelicula peliculaActualizada = peliculaService.editPelicula(pelicula);
        return ResponseEntity.ok(peliculaActualizada);
    }

    @GetMapping("/cartelera")
    @Operation (summary = "Listado de peliculas en cartelera")
    public ResponseEntity <List<Pelicula>> peliculasEnCartelera(){
        List<Pelicula> lista = peliculaService.findIsPeliculasCartelera();
        return new ResponseEntity<>(lista,HttpStatus.ACCEPTED);
    }
}
