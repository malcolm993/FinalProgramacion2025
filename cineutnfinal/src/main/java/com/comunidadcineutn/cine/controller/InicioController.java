  package com.comunidadcineutn.cine.controller;

  import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.RequestMapping;

  import com.comunidadcineutn.cine.service.InterfaceServicePelicula;
  import io.swagger.v3.oas.annotations.Operation;
  import io.swagger.v3.oas.annotations.tags.Tag;

  @Tag(name = "Inicio", description = "Inicio de la pagina")
  @RequestMapping("/inicio")
  @Controller
  public class InicioController {

      @Autowired
      private InterfaceServicePelicula peliculaService;

      @GetMapping("/todas")
      @Operation(summary = "Obtener todas las peliculas")
      public String getPeliculas(Model modelo) {
          modelo.addAttribute("listaPeliculas", peliculaService.getAll());
          //return new ResponseEntity<>(listaPeliculas, HttpStatus.ACCEPTED);
          return "index";
      }
   

    
}
