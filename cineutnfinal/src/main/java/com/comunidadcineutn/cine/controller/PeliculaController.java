/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.controller;

import com.comunidadcineutn.cine.dto.PeliculaEdicionDTO;
import com.comunidadcineutn.cine.exception.ExceptionNotFound;
import com.comunidadcineutn.cine.exception.PeliculaConFuncionesException;
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
  public String buscarPeliculaPorId(@PathVariable Integer id, Model m, RedirectAttributes ra) {
    String destino = null;
    try {
      PeliculaEdicionDTO p = peliculaService.getPeliculaEdicion(id);
      m.addAttribute("pelicula", p);
      destino = "peliculas/revision-pelicula";
    } catch (ExceptionNotFound ex) {
      ra.addFlashAttribute("error", ex.getErrorMensaje());
      destino = "redirect:/cineutn/pelicula/crudpeliculas"; // reenvio a la pagina crud de peliculas
    }
    return destino;
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
    model.addAttribute("listaPeliculas", peliculaService.getAll());
    return "peliculas/crudpelicula";
  }

  @GetMapping("/editar/{id}")
  @Operation(summary = "obtengo formulario para la edicion de la pelicula")
  public String obtenerFormularioEdicion(@PathVariable("id") int idPelicula, Model m, RedirectAttributes ra) {
    String destino = null;
    try {
      PeliculaEdicionDTO peliculaEditada = peliculaService.getPeliculaEdicion(idPelicula);
      m.addAttribute("pelicula", peliculaEditada);
      m.addAttribute("clasificaciones", CalificacionPelicula.values());
      destino = "peliculas/formulariodeedicion";
    } catch (Exception ex) {
      ra.addFlashAttribute("error", ex.getMessage());
      System.out.println("error :" + ex.getMessage());
      destino = "redirect:/cineutn/pelicula/crudpeliculas"; // reenvio a la pagina crud de peliculas
    }
    return destino;
  }

  @GetMapping("/eliminar/{id}")
  @Operation(summary = "obtengo vista de eliminacion de pelicula")
  public String eliminarPelicula(@PathVariable("id") int idPelicula, Model m, RedirectAttributes ra) {
    String destino = null;
    try {
      Pelicula peliculaEliminada = peliculaService.findPeliculaPorId(idPelicula);
      m.addAttribute("pelicula", peliculaEliminada);
      destino = "peliculas/eliminarpelicula";
    } catch (ExceptionNotFound ex) {
      ra.addFlashAttribute("error", ex.getErrorMensaje());
      destino = "redirect:/cineutn/pelicula/crudpeliculas"; // reenvio a la pagina crud de peliculas
    }

    return destino;
  }

  @PostMapping("/agregar")
  @Operation(summary = "Agregar pelicula")
  public String agregarPelicula(
      @Valid @ModelAttribute("pelicula") Pelicula p,
      BindingResult bindingResult,
      RedirectAttributes ra) {
    String destino = null;    
    try {
      if(bindingResult.hasErrors()){
        destino = "peliculas/formulariodealta";
        throw new Exception("Error en la validacion de datos para alta de pelicula");
      }
      Pelicula agregada = peliculaService.addPelicula(p);
      ra.addFlashAttribute("mensaje", "Película " + agregada.getNombre() + " guardada con éxito proximo a estrenarse");
      destino = "redirect:/cineutn/pelicula/crudpeliculas";
    } catch (Exception e) {
      ra.addFlashAttribute("error", e.getMessage());
      destino = "peliculas/formulariodealta";
    }
    return destino;
  }

  @DeleteMapping("/eliminar")
  @Operation(summary = "Eliminar película por ID")
  public String eliminarPelicula(@RequestParam("idPelicula") Integer id,
      RedirectAttributes ra) {
    try {
      Pelicula p = peliculaService.findPeliculaPorId(id);
      if (p.getFuncionesAsociadas() != null && !p.getFuncionesAsociadas().isEmpty()) {
        throw new PeliculaConFuncionesException(
            "la pelicula seleccionada tiene " + p.getFuncionesAsociadas().size() + " funciones asociadas");
      }
      peliculaService.deletePeliculaPorId(id);
      ra.addFlashAttribute("mensaje", "Película " + p.getNombre() + " eliminada con éxito!");

    } catch (Exception e) {
      ra.addFlashAttribute("error",
          e.getMessage());
    }

    return "redirect:/cineutn/pelicula/crudpeliculas";
  }

  @PutMapping("/editar")
  @Operation(summary = "Editar película")
  public String edicionPeliculaPost(@Valid @ModelAttribute("pelicula") PeliculaEdicionDTO p,
      BindingResult bindingResult,
      RedirectAttributes ra) {
    String destino = null;
    try {
      if (bindingResult.hasErrors()) {
        destino = "peliculas/formulariodeedicion";
        throw new Exception(" fallas en el binding al editar la pelicula");
      }
      Pelicula editada = peliculaService.actualizarPelicula(p.getId(), p);
      ra.addFlashAttribute("mensaje", "Película " + editada.getNombre() + " editada con éxito!");
      destino = "redirect:/cineutn/pelicula/crudpeliculas";
    } catch (Exception e) {
      ra.addFlashAttribute("error", e.getMessage());
      destino = "peliculas/formulariodeedicion";
    }
    System.out.println("nueva edicion de pelicula " + p.toString());
    return destino;
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
