package com.comunidadcineutn.cine.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.comunidadcineutn.cine.dto.PeliculaEdicionDTO;
import com.comunidadcineutn.cine.exception.ExceptionNotFound;
import com.comunidadcineutn.cine.model.Funcion;
import com.comunidadcineutn.cine.model.Pelicula;
import com.comunidadcineutn.cine.service.InterfaceServiceFuncion;
import com.comunidadcineutn.cine.service.InterfaceServiceReserva;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Reservas", description = "API para gestión de Reservas de entradas")
@RequestMapping("/cineutn/reserva")
@Controller
  
public class ReservasController {

  @Autowired
  private InterfaceServiceReserva reservaService;

  @Autowired
  private InterfaceServiceFuncion funcionService;

  @GetMapping("/buscar/{id}")
  @Operation(summary = "Obtener reserva por ID")
  public String buscarPeliculaPorId(@PathVariable Integer id, Model m) {
    try {
      

    } catch (ExceptionNotFound ex) {
      
    }
    return "peliculas/revision-pelicula";
  }

  @GetMapping("/comprarEntrada")
  @Operation(summary = "Obtengo el listado de funciones para reservar")
  public String vistaComprarEntrada(Model m) {
    List<Funcion> listaFunciones = funcionService.getAllFuncion();
    m.addAttribute("funciones", listaFunciones);
    return "reservas/user-reserva";
  }

  @GetMapping("/crud")
  @Operation(summary = "obtener crud de las peliculas")
  public String mostrarCRUDPeliculas(Model model) {
    
    return "peliculas/crudpelicula";
  }

  @GetMapping("/editar/{id}")
  @Operation(summary = "obtengo formulario para la edicion de la pelicula")
  public String obtenerFormularioEdicion(@PathVariable("id") int idPelicula, Model m) {
    try {
      
    } catch (ExceptionNotFound ex) {
      
    }
    return "null";
  }

  @GetMapping("/eliminar/{id}")
  @Operation(summary = "obtengo vista de eliminacion de pelicula")
  public String eliminarPelicula(@PathVariable("id") int idPelicula, Model m) {

    try {
      
      
    } catch (ExceptionNotFound ex) {
      
      
    }
    return "null";
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

    return "null";
  }

  @DeleteMapping("/eliminar")
  @Operation(summary = "Eliminar película por ID")
  public String eliminarPelicula(@RequestParam(required = true, name = "id") Integer id,
      RedirectAttributes ra) {
  
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
    return "null";
  }

}


