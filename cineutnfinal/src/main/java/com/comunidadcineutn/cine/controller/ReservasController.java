package com.comunidadcineutn.cine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.comunidadcineutn.cine.dto.ReservaFormDTO;
import com.comunidadcineutn.cine.exception.ExceptionNotFound;
import com.comunidadcineutn.cine.model.Funcion;
import com.comunidadcineutn.cine.model.Pelicula;
import com.comunidadcineutn.cine.model.Reserva;
import com.comunidadcineutn.cine.model.Usuario;
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

  @GetMapping("/funcionesPelicula/{id}")
  @Operation(summary = "Obtengo el listado de funciones para reservar")
  public String vistaFuncionesPelicula(@PathVariable("id") Integer id, Model m) {
    List<Funcion> listaFunciones = funcionService.getListaFuncionesPorIdPelicula(id);
    System.out.println(listaFunciones);
    m.addAttribute("funciones", listaFunciones);
    return "reservas/user-reserva";
  }

  @PostMapping("/comprar")
  @Operation(summary = "Obtengo el formulario para realizar la reserva")
  public String vistaComprarEntrada(@RequestParam("idFuncion") Integer id, Model m) {
    Funcion f = funcionService.findFuncionPorId(id);
    ReservaFormDTO r = new ReservaFormDTO();
    r.setIdFuncion(id);
    System.out.println("la funcion seleccionada es : " + f.toString());
    m.addAttribute("funcion", f);
    m.addAttribute("reservaForm", r);
    return "reservas/formulario-reserva";
  }

  @PostMapping("/confirmacionCompra")
  @Operation(summary = "confirmar la reservar")
  public String reservaEntrada(
      @Valid @ModelAttribute("reserva") ReservaFormDTO reserva,
      BindingResult bindingResult,
      @AuthenticationPrincipal Usuario actual,
      Model model,
      RedirectAttributes ra) {

    if (bindingResult.hasErrors()) {
      Funcion funcion = funcionService.findFuncionPorId(reserva.getIdFuncion());
      model.addAttribute("funcion", funcion);
      model.addAttribute("reserva", reserva);
      return "reservas/formulario-reserva";
    }

    try {
      // Convertir DTO a Entidad
      Reserva creada = reservaService.crearReservaFromDTO(actual, reserva);
      reservaService.addReserva(creada);
      ra.addFlashAttribute("mensajeExito", "Reserva confirmada exitosamente");
      ra.addFlashAttribute("reserva", creada);
      return "redirect:/cineutn/reserva/exito";

    } catch (Exception e) {
      // También en el catch hay que agregar el DTO
      model.addAttribute("error", "Error al procesar la reserva: " + e.getMessage());
      Funcion funcion = funcionService.findFuncionPorId(reserva.getIdFuncion());
      model.addAttribute("funcion", funcion);
      model.addAttribute("reserva", reserva); // ← Agregar el DTO aquí también
      return "reservas/formulario-reserva";
    }
  }

  @GetMapping("/exito")
  @Operation(summary = "obtener crud de las peliculas")
  public String exitoReservaVista(Model m) {
    if (!m.containsAttribute("reserva")) {
      // Redirigir o mostrar mensaje de error
      return "redirect:/cineutn/reserva/error?msg=no-reserva";
    }
    Reserva r = (Reserva) m.getAttribute("reserva");
    m.addAttribute("reserva", r);
    return "reservas/exito-reserva";
  }

  // DE ACA EN ADELANTE FALTA TERMINAR LAS RUTAS DEL CONTROLADOR
  @GetMapping("/misReservas")
  @Operation(summary = "Obtener las reservas del usuario")
  public String buscarPeliculaPorId(Model m,@AuthenticationPrincipal Usuario actual) {
   
      List<Reserva> reservas = reservaService.getRerservasPorIdUsario(actual.getId());
      m.addAttribute("reservas", reservas);
      return "reservas/lista-reservas-hechas";
 
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
