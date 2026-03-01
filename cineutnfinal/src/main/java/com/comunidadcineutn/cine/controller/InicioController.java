package com.comunidadcineutn.cine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.comunidadcineutn.cine.service.InterfaceServicePelicula;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Inicio", description = "Inicio de la pagina")
@Controller
@RequestMapping({ "/cineutn/inicio", "/" })
public class InicioController {

    @Autowired
    private InterfaceServicePelicula peliculaService;

    @GetMapping
    @Operation(summary = "Obtener todas las peliculas")
    public String getPeliculas(
            @RequestParam(required = false) String error,
            Model model) {

        // Cargar películas
        model.addAttribute("listaPeliculas", peliculaService.getAll());

        // Manejo de error por acceso denegado (403)
        if ("forbidden".equals(error)) {
            model.addAttribute("error",
                    "No tiene permisos para acceder a esa sección.");
        }

        return "inicio/index";
    }

    @GetMapping("/nosotros")
    @Operation(summary = "Obtener vista presentación")
    public String mostrarVistaNosotros() {
        return "inicio/nosotros";
    }
}