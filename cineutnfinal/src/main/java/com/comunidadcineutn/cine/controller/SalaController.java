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

import com.comunidadcineutn.cine.exception.ExceptionNotFound;
import com.comunidadcineutn.cine.model.Funcion;
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
  public String obtenerFormularioEdicion(@PathVariable("id") Integer id, Model m, RedirectAttributes ra) {
    String destino = null;
    try {
      Sala salaEditada = salaService.findSalaPorId(id);
      if (salaEditada.getFuncionesRealizadasSala().size() > 0) {
        throw new Exception("Las salas con funciones asociadas no es posible editarlas");
      }
      m.addAttribute("sala", salaEditada);
      m.addAttribute("tiposDeSala", TipoDeSala.values());
      destino = "salas/formulario-edicion";
    } catch (Exception ex) {
      ra.addFlashAttribute("error", ex.getMessage());
      destino = "redirect:/cineutn/sala/crudsalas"; // reenvio a la pagina crud de peliculas
    }
    return destino;
  }

  @GetMapping("/crudsalas")
  @Operation(summary = "obtener crud de las salas")
  public String mostrarCRUDSalas(Model m) {
    m.addAttribute("listaDeSalas", salaService.getAllSalas());
    return "salas/crud-salas";
  }

  @GetMapping("/agregar")
  @Operation(summary = "formulario para Agregar sala")
  public String agregarSala(Model m) {
    m.addAttribute("sala", new Sala());
    m.addAttribute("tiposDeSala", TipoDeSala.values());
    return "salas/formulario-alta";
  }

  @GetMapping("/funcionesAsociadas/{id}")
  @Operation(summary = "vista de funciones asociadas a la Sala")
  public String vistaListaFuncionesAsociadas(@PathVariable("id") Integer idSala, Model m , RedirectAttributes ra){
    String destino = null;
    try {
      Sala s = salaService.findSalaPorId(idSala);
      List<Funcion> funcionesAsociadas = s.getFuncionesRealizadasSala();
      if(funcionesAsociadas.size()==0){
        throw new Exception("La sala no tiene funciones Asociadas");
      }
      m.addAttribute("sala", s);
      destino= "salas/funciones";
    } catch (Exception e) {
      ra.addFlashAttribute("error", e.getMessage());
      destino = "redirect:/cineutn/sala/crudsalas";
    }
    return destino;
  };

  @PostMapping("/agregar")
  @Operation(summary = "Agregar sala")
  public String agregarSala(@Valid @ModelAttribute("sala") Sala s,
      BindingResult br,
      RedirectAttributes ra) {
    String destino = null;
    if (br.hasErrors()) {
      destino = "salas/formulario-alta";
    } else {
      Sala agregada = salaService.addSala(s);
      ra.addFlashAttribute("mensaje", "La sala con ID : " + agregada.getIdSala() + " fue correctamente creada");
      destino= "redirect:/cineutn/sala/crudsalas";
    }
    return destino;
  }

  @GetMapping("/eliminar/{id}")
  @Operation(summary = "obtengo vista de eliminacion de la sala")
  public String eliminarSala(@PathVariable("id") Integer id, Model m, RedirectAttributes ra) {
    String destino = null;
    try {
      Sala salaEliminada = salaService.findSalaPorId(id);
      m.addAttribute("sala", salaEliminada);
      destino = "salas/eliminar-sala";
    } catch (ExceptionNotFound ex) {
      ra.addFlashAttribute("error", ex.getErrorMensaje());
      destino = "redirect:/cineutn/sala/crudsalas"; // reenvio a la pagina crud de salas
    }
    return destino;
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
  @Operation(summary = "Eliminar sala por ID en URL")
  public String eliminarSala(@RequestParam(required = true, name = "idSala") Integer id,
      RedirectAttributes ra) {
    Sala s = salaService.findSalaPorId(id);
    salaService.deleteSalaPorId(id);
    ra.addFlashAttribute("mensaje", "Sala con Id : " + s.getIdSala() + " eliminada con éxito!");

    return "redirect:/cineutn/sala/crudsalas";
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
