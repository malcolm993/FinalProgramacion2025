package com.comunidadcineutn.cine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.comunidadcineutn.cine.exception.ExceptionNotFound;

import com.comunidadcineutn.cine.model.Sala;
import com.comunidadcineutn.cine.model.TipoDeSala;
import com.comunidadcineutn.cine.service.InterfaceServiceSala;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Salas", description = "API para gestion de salas")
@RequestMapping("/cineutn/sala")
@Controller
public class SalaController {

  @Autowired
  private InterfaceServiceSala salaService;

  @GetMapping("/buscar/{id}")
  @Operation(summary = "Obtener sala por ID")
  public Sala buscarSalaPorId(@PathVariable Integer id) {
    return salaService.findSalaPorId(id);
  }

  @GetMapping("/editar/{id}")
  @Operation(summary = "obtengo formulario para la edicion de la sala")
  public String obtenerFormularioEdicion(@PathVariable("id") Integer id, Model m) {
    try {
      Sala salaEditada = salaService.findSalaPorId(id);
      m.addAttribute("sala", salaEditada);
      return "salas/formulario-edicion";
    } catch (ExceptionNotFound ex) {
      m.addAttribute("error", ex.getErrorMensaje());
      return "salas/crudsalas"; // reenvio a la pagina crud de peliculas
    }
  }

  @GetMapping("/crudsalas")
  @Operation(summary = "obtener crud de las salas")
  public String mostrarCRUDSalas(Model m) {
    m.addAttribute("listaDeSalas", salaService.getAllSalas());
    return "salas/crud-salas";
  }

  @GetMapping("/todas")
  @Operation(summary = "Obtener todas las salas")
  public ResponseEntity<List<Sala>> getSalas() {
    List<Sala> listaSalas = salaService.getAllSalas();
    return new ResponseEntity<>(listaSalas, HttpStatus.ACCEPTED);
  }

  @GetMapping("/agregar")
  @Operation(summary = "formulario para Agregar sala")
  public String agregarSala(Model m) {
    m.addAttribute("sala", new Sala());
    m.addAttribute("tiposDeSala", TipoDeSala.values());
    return "salas/formulario-alta";
  }

  @PostMapping("/agregar")
  @Operation(summary = "Agregar sala")
  public String agregarSala(@Valid @ModelAttribute("sala") Sala s,
      BindingResult br,
      RedirectAttributes ra) {

    if (br.hasErrors()) {
      return "salas/formulario-alta";
    }
    ;
    Sala agregada = salaService.addSala(s);
    ra.addFlashAttribute("mensaje", "La sala con ID : " + agregada.getIdSala() + " fue correctamente creada");
    return "redirect:/cineutn/sala/crudsalas";
  }

  @GetMapping("/eliminar/{id}")
  @Operation(summary = "obtengo vista de eliminacion de la sala")
  public String eliminarSala(@PathVariable("id") Integer id, Model m) {

    try {
      Sala salaEliminada = salaService.findSalaPorId(id);
      m.addAttribute("sala", salaEliminada);
      return "salas/eliminar-sala";
    } catch (ExceptionNotFound ex) {
      m.addAttribute("error", ex.getErrorMensaje());
      return "salas/crudpelicula"; // reenvio a la pagina crud de salas
    }
  }

  /*
   * @DeleteMapping("/eliminar")
   * 
   * @Operation(summary = "Eliminar sala por ID")
   * public ResponseEntity<?> eliminarPelicula(@RequestParam(required = true, name
   * = "id") Integer id) {
   * return salaService.findSalaPorId(id)
   * .map(sala -> {
   * salaService.deleteSalaPorId(id);
   * return ResponseEntity.ok().body("Sala Id " + sala.getIdSala() +
   * " eliminada correctamente");
   * })
   * .orElse(ResponseEntity.notFound().build());
   * }
   */

  @DeleteMapping("/eliminar")
  @Operation(summary = "Eliminar sala por ID")
  public String eliminarSala(@RequestParam(required = true, name = "idSala") Integer id,
      RedirectAttributes ra) {
    System.out.println("id recibido : " + id);
    Sala s = salaService.findSalaPorId(id);
    salaService.deleteSalaPorId(id);
    ra.addFlashAttribute("mensaje", "Sala con Id : " + s.getIdSala() + " eliminada con éxito!");

    return "redirect:/cineutn/pelicula/crudsalas";
  }

  @PutMapping("/editar")
  @Operation(summary = "Editar sala")
  public String edicionSala(
      @Valid @ModelAttribute("sala") Sala s,
      BindingResult br,
      RedirectAttributes ra) {
    if (br.hasErrors()) {
      return "peliculas/formulario-alta";
    }
    Sala salaEditada = salaService.editSala(s);
    ra.addFlashAttribute("null", "Sala Id:" + salaEditada.getIdSala() + "editada con exito");
    return "redirect:/cineutn/sala/crudsalas";
  }

}
