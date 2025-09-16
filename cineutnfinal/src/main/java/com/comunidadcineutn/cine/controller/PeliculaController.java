/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.controller;

import com.comunidadcineutn.cine.dto.PeliculaEdicionDTO;
import com.comunidadcineutn.cine.exception.ExceptionNotFound;
import com.comunidadcineutn.cine.model.CalificacionPelicula;
import com.comunidadcineutn.cine.model.Pelicula;
import com.comunidadcineutn.cine.service.InterfaceServicePelicula;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author santi
 */
@Tag(name = "Películas", description = "API para gestión de películas")
@RequestMapping("/cineutn/pelicula")
@Controller
public class PeliculaController {

  @Autowired
  private InterfaceServicePelicula peliculaService;



  @GetMapping("/buscar/{id}")
  @Operation(summary = "Obtener película por ID")
  public String buscarPeliculaPorId(@PathVariable Integer id, Model m) {
    try {
      PeliculaEdicionDTO p = peliculaService.getPeliculaEdicion(id);
      m.addAttribute("pelicula", p);
      System.out.println("nombre :" + p.getNombre());

    } catch (ExceptionNotFound ex) {
      m.addAttribute("error", ex.getErrorMensaje());
      return "peliculas/crudpelicula"; // reenvio a la pagina crud de peliculas
    }
    return "peliculas/revision-pelicula";
  }

  @GetMapping("/agregar")
  @Operation(summary = "Obtengo el formulario")
  public String vistaAgregarPeliculas(Model m) {
    m.addAttribute("pelicula", new Pelicula());
    m.addAttribute("clasificaciones", CalificacionPelicula.values());
    return "peliculas/formulariodealta";
  }

  @GetMapping("/crudpeliculas")
  @Operation(summary = "obtener crud de las peliculas")
  public String mostrarCRUDPeliculas(Model model) {
    // Asegúrate de agregar los datos necesarios al modelo
    model.addAttribute("listaPeliculas", peliculaService.getAll());
    // System.out.println("hola " + peliculaService.getAll());
    return "peliculas/crudpelicula";
  }

  @GetMapping("/editar/{id}")
  @Operation(summary = "obtengo formulario para la edicion de la pelicula")
  public String obtenerFormularioEdicion(@PathVariable("id") int idPelicula, Model m) {
    try {
      PeliculaEdicionDTO peliculaEditada = peliculaService.getPeliculaEdicion(idPelicula);
      m.addAttribute("pelicula", peliculaEditada);
      m.addAttribute("clasificaciones", CalificacionPelicula.values());
      return "peliculas/formulariodeedicion";
    } catch (ExceptionNotFound ex) {
      m.addAttribute("error", ex.getErrorMensaje());
      return "peliculas/crudpelicula"; // reenvio a la pagina crud de peliculas
    }
  }

  @GetMapping("/eliminar/{id}")
  @Operation(summary = "obtengo vista de eliminacion de pelicula")
  public String eliminarPelicula(@PathVariable("id") int idPelicula, Model m) {

    try {
      PeliculaEdicionDTO peliculaEliminada = peliculaService.getPeliculaEdicion(idPelicula);
      m.addAttribute("pelicula", peliculaEliminada);
      return "peliculas/eliminarpelicula";
    } catch (ExceptionNotFound ex) {
      m.addAttribute("error", ex.getErrorMensaje());
      return "peliculas/crudpelicula"; // reenvio a la pagina crud de peliculas
    }
  }

  @PostMapping("/agregar")
  @Operation(summary = "Agregar pelicula")
  public String agregarPelicula(
      @Valid @ModelAttribute("pelicula") Pelicula p,
      BindingResult bindingResult,
      RedirectAttributes ra) {

    if (bindingResult.hasErrors()) {
      // Mantiene los errores en el formulario
      return "peliculas/formulariodealta";
    }

    Pelicula agregada = peliculaService.addPelicula(p);
    ra.addFlashAttribute("mensaje", "Película " + agregada.getNombre() + " guardada con éxito!");

    return "redirect:/cineutn/pelicula/crudpeliculas";
  }

  @DeleteMapping("/eliminar")
  @Operation(summary = "Eliminar película por ID")
  public String eliminarPelicula(@RequestParam(required = true, name = "id") Integer id,
      RedirectAttributes ra) {
    Pelicula p = peliculaService.findPeliculaPorId(id);    
    peliculaService.deletePeliculaPorId(id);
    ra.addFlashAttribute("mensaje", "Película " + p.getNombre() + " eliminada con éxito!");

    return "redirect:/cineutn/pelicula/crudpeliculas";
  }

  @PutMapping("/editar")
  @Operation(summary = "Editar película")
  public String edicionPeliculaPost(@Valid @ModelAttribute("pelicula") PeliculaEdicionDTO p,
      BindingResult bindingResult,
      RedirectAttributes ra) {
    if (bindingResult.hasErrors()) {
      // Mantiene los errores en el formulario
      return "peliculas/agregar";
    }
    System.out.println("nueva edicion de pelicula " + p.toString());
    Pelicula editada = peliculaService.actualizarPelicula(p.getId(), p);
    ra.addFlashAttribute("mensaje", "Película " + editada.getNombre() + " editada con éxito!");

    return "redirect:/cineutn/pelicula/crudpeliculas";
  }

  @GetMapping("/cartelera")
  @Operation(summary = "Listado de peliculas en cartelera")
  public ResponseEntity<List<Pelicula>> peliculasEnCartelera() {
    List<Pelicula> lista = peliculaService.peliculasEnCartelera();
    return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
  }

  @GetMapping("/estreno")
  @Operation(summary = "Listado de peliculas a estrenar")
  public ResponseEntity<List<Pelicula>> peliculasPorEstrenar() {
    List<Pelicula> lista = peliculaService.peliculasEstreno();
    return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
  }
}
