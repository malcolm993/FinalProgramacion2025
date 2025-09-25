package com.comunidadcineutn.cine.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.comunidadcineutn.cine.dto.FuncionAltaDTO;
import com.comunidadcineutn.cine.dto.PeliculaAltaFuncionDTO;

import com.comunidadcineutn.cine.exception.ExceptionNotFound;
import com.comunidadcineutn.cine.model.Funcion;
import com.comunidadcineutn.cine.model.Pelicula;
import com.comunidadcineutn.cine.model.Sala;
import com.comunidadcineutn.cine.service.InterfaceServiceFuncion;
import com.comunidadcineutn.cine.service.InterfaceServicePelicula;
import com.comunidadcineutn.cine.service.InterfaceServiceSala;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Funciones", description = "API para gestión de funciones")
@RequestMapping("/cineutn/funcion")
@Controller
public class FuncionController {
  @Autowired
  private InterfaceServiceFuncion funcionService;

  @Autowired
  private InterfaceServicePelicula peliculaService;

  @Autowired
  private InterfaceServiceSala salaService;

  @GetMapping("/revisar/{id}")
  @Operation(summary = "Obtener funcion por ID")
  public String buscarPeliculaPorId(@PathVariable Integer id, Model m) {
    try {
      Funcion f = funcionService.findFuncionPorId(id);
      m.addAttribute("funcion", f);
    } catch (ExceptionNotFound ex) {
      m.addAttribute("error", ex.getErrorMensaje());
      return "funciones/crud-funciones";
    }

    return "funciones/revisar-funcion";
  }

  @GetMapping("/crudfunciones")
  @Operation(summary = "Obtener crud de las funciones")
  public String getAllFunciones(Model m) {
    List<Funcion> listaFunciones = funcionService.getAllFuncion();
    System.out.println("paso la lista" + listaFunciones.toString());
    m.addAttribute("listaFunciones", listaFunciones);
    System.out.println("HOLA ACA LLEGA O NO ]???");
    return "funciones/crud-funciones";
  }

  @GetMapping("/agregar")
  @Operation(summary = "Obtengo el formulario para crear funcion")
  public String vistaAgregarFuncion(Model m) {
    m.addAttribute("funcion", new FuncionAltaDTO());
    List<PeliculaAltaFuncionDTO> lista = peliculaService.getListadoPeliculasAltaFuncion();
    m.addAttribute("listaPeliculas", lista);
    List<Sala> listaSalas = salaService.getAllSalas();
    m.addAttribute("listaSalas", listaSalas);
    List<LocalDate> fechas = funcionService.fechasHabilitadas();
    m.addAttribute("fechas", fechas);

    return "funciones/alta-funcion";
  }

  @PostMapping("/agregar")
  @Operation(summary = "Creo una funcion")
  public String agregarFuncion(@Valid @ModelAttribute("funcion") FuncionAltaDTO f,
      BindingResult br,
      RedirectAttributes ra) {
    String destino = "";
    if (br.hasErrors()) {
      System.out.println(br.getAllErrors());
      destino = "funciones/alta-funcion";
    } else {
      try {
        Funcion funcion = funcionService.toFuncionFromFuncionDTO(f);
        Funcion creada = funcionService.addFuncion(funcion);
        ra.addFlashAttribute("mensaje", "Funcion con id " + creada.getIdFuncion() +
            " " + creada.getPelicula().getNombre() + " ha sido creada");
        destino = "redirect:/cineutn/funcion/crudfunciones";
      } catch (Exception e) {
        ra.addFlashAttribute("error", e.getMessage());
        destino = "redirect:/cineutn/funcion/crudfunciones";
      }
    }
    return destino;
  }

  @GetMapping("/eliminar/{id}")
  @Operation(summary = "Obtener vista Eliminar funcion ")
  public String eliminarFuncion(@PathVariable("id") Integer id,
      Model m) {
    try {
      Funcion f = funcionService.findFuncionPorId(id);
      m.addAttribute("funcion", f);
      return "funciones/eliminar-funcion";
    } catch (ExceptionNotFound ex) {
      m.addAttribute("error", ex.getErrorMensaje());
      return "funciones/eliminar-funcion";
    }

  }

  @DeleteMapping("/eliminar")
  @Operation(summary = "Eliminar funcion por ID")
  public String eliminarPelicula(@RequestParam(required = true, name = "idFuncion") Integer id,
      RedirectAttributes ra) {
    Funcion f = funcionService.findFuncionPorId(id);
    funcionService.deleteFuncionPorId(id);
    ra.addFlashAttribute("mensaje", "Funcion de Id: " + f.getIdFuncion() + "ha sido eliminada");
    return "redirect:/cineutn/funcion/crudfunciones";
  }

  @GetMapping("/editar/{id}")
  @Operation(summary = "obtengo formulario para la edicion de la funcion")
  public String obtenerFormularioEdicion(@PathVariable Integer id, Model m,
      RedirectAttributes ra) {
    try {
      Funcion porEditar = funcionService.findFuncionPorId(id);
      if (porEditar.getCantButacasReservadas() > 0) {
        throw new Exception("Funcion con reservas hechas");
      }
      FuncionAltaDTO funcionEditada = funcionService.getFuncionEdicion(id);
      m.addAttribute("listaPeliculas", peliculaService.getAll());
      m.addAttribute("listaSalas", salaService.getAllSalas());
      m.addAttribute("fechas", funcionService.fechasHabilitadas());
      m.addAttribute("funcion", funcionEditada);
      return "funciones/editar-funcion";
    } catch (Exception ex) {
      ra.addFlashAttribute("error", ex.getMessage());
      return "redirect:/cineutn/funcion/crudfunciones"; // reenvio a la pagina crud de peliculas
    }
  }

  @PutMapping("/editar")
  @Operation(summary = "Editar funcion")
  public String actualizarFuncion(
      @Valid @ModelAttribute("funcion") FuncionAltaDTO f,
      BindingResult br,
      RedirectAttributes ra) {
    if (br.hasErrors()) {
      return "funciones/editar-funcion";
    }
    LocalDateTime fechaHora = LocalDateTime.of(f.getFechaFuncion(), f.getHoraFuncion());
    Funcion auxFuncion = new Funcion(f.getId(), f.getSala(), f.getPelicula(), fechaHora);

    Funcion editada = funcionService.editFuncion(auxFuncion);
    System.out.println("los horarios inicio " + editada.getHoraInicio());
    System.out.println("horario termina " + editada.getHoraFin());
    ra.addFlashAttribute("mensaje", "Funcion con id " + editada.getIdFuncion() +
        " " + editada.getPelicula().getNombre() + " ha sido editada");
    return "redirect:/cineutn/funcion/crudfunciones";
  }

  @GetMapping("/nuevaPelicula")
  @Operation(summary = "Obtengo el formulario para crear funcion")
  public String vistaAgregarFuncionNuevaPelicula(Model m) {
    m.addAttribute("funcion", new FuncionAltaDTO());
    Pelicula nueva = (Pelicula) m.getAttribute("pelicula");
    m.addAttribute("pelicula", nueva);
    List<Sala> listaSalas = salaService.getAllSalas();
    m.addAttribute("listaSalas", listaSalas);
    List<LocalDate> fechas = funcionService.fechasHabilitadas();
    m.addAttribute("fechas", fechas);

    return "funciones/alta-funcion";
  }

}
