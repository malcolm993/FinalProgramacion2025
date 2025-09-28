package com.comunidadcineutn.cine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.comunidadcineutn.cine.dto.ReservaFormDTO;
import com.comunidadcineutn.cine.exception.ExceptionNotFound;
import com.comunidadcineutn.cine.model.Funcion;
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

  @Secured({"ROLE_ADMIN", "ROLE_CLIENTE"})
  @GetMapping("/funcionesPelicula/{id}")
  @Operation(summary = "Obtengo el listado de funciones para reservar")
  public String vistaFuncionesPelicula(@PathVariable("id") Integer id, Model m) {
    List<Funcion> listaFunciones = funcionService.getListaFuncionesPorIdPelicula(id);
    System.out.println(listaFunciones);
    m.addAttribute("funciones", listaFunciones);
    return "reservas/user-reserva";
  }

   @Secured({"ROLE_ADMIN", "ROLE_CLIENTE"})
  @PostMapping("/comprar")
  @Operation(summary = "Obtengo el formulario para realizar la reserva")
  public String vistaComprarEntrada(@RequestParam(name = "idFuncion", required = true) Integer id,
      Model m,
      RedirectAttributes ra) {
    String destino = null;
    try {
      Funcion f = funcionService.findFuncionPorId(id);
      ReservaFormDTO r = new ReservaFormDTO();
      r.setIdFuncion(id);
      m.addAttribute("funcion", f);
      m.addAttribute("reservaForm", r);
      destino = "reservas/formulario-reserva";
    } catch (ExceptionNotFound e) {
      ra.addFlashAttribute("error", e.getMessage());
      destino = "redirect:/cineutn/inicio";
    }

    return destino;
  }

   @Secured({"ROLE_ADMIN", "ROLE_CLIENTE"})
  @PostMapping("/confirmacionCompra")
  @Operation(summary = "confirmar la reservar")
  public String reservaEntrada(
      @Valid @ModelAttribute("reserva") ReservaFormDTO reserva,
      BindingResult bindingResult,
      @AuthenticationPrincipal Usuario actual,
      Model model,
      RedirectAttributes ra) {
    String destino = null;
    if (bindingResult.hasErrors()) {
      Funcion funcion = funcionService.findFuncionPorId(reserva.getIdFuncion());
      model.addAttribute("funcion", funcion);
      model.addAttribute("reserva", reserva);
      destino = "reservas/formulario-reserva";
    } else {

      try {
        // Convertir DTO a Entidad
        Reserva creada = reservaService.crearReservaFromDTO(actual, reserva);
        reservaService.addReserva(creada);
        ra.addFlashAttribute("mensajeExito", "Reserva confirmada exitosamente");
        ra.addFlashAttribute("reserva", creada);
        destino = "redirect:/cineutn/reserva/exito";

      } catch (Exception e) {
        // También en el catch hay que agregar el DTO
        model.addAttribute("error", "Error al procesar la reserva: " + e.getMessage());
        Funcion funcion = funcionService.findFuncionPorId(reserva.getIdFuncion());
        model.addAttribute("funcion", funcion);
        model.addAttribute("reserva", reserva); //
        destino = "reservas/formulario-reserva";
      }
    }
    return destino;
  }

   @Secured({"ROLE_ADMIN", "ROLE_CLIENTE"})
  @GetMapping("/exito")
  @Operation(summary = "obtener vista Exito en generacion de reserva")
  public String exitoReservaVista(Model m, RedirectAttributes ra) {
    String destino = "null";
    if (!m.containsAttribute("reserva")) {
      ra.addFlashAttribute("error", "RESERVE UNA FUNCION ");
      destino = "redirect:/cineutn/inicio";
    } else {
      Reserva r = (Reserva) m.getAttribute("reserva");
      m.addAttribute("reserva", r);
      destino = "reservas/exito-reserva";

    }
    return destino;
  }

  @Secured({"ROLE_ADMIN", "ROLE_CLIENTE"})
  @GetMapping("/misReservas")
  @Operation(summary = "Obtener las reservas del usuario")
  public String buscarPeliculaPorId(Model m, @AuthenticationPrincipal Usuario actual) {

    List<Reserva> reservas = reservaService.getRerservasPorIdUsario(actual.getId());
    m.addAttribute("reservas", reservas);
    return "reservas/lista-reservas-usuario";

  }

   @Secured({"ROLE_ADMIN"})
  @GetMapping("/reservasAll")
  @Operation(summary = "obtener todas las reservas de los usuarios")
  public String mostrarTodasReservas(Model model) {
    List<Reserva> listaReservas = reservaService.getAll();
    model.addAttribute("listaReservas", listaReservas);
    return "reservas/reservas-all";
  }

  // DE ACA EN ADELANTE FALTA TERMINAR LAS RUTAS DEL CONTROLADOR

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

}
