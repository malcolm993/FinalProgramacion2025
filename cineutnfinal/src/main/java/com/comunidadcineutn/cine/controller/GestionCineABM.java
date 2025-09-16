package com.comunidadcineutn.cine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ABM-Cine", description = "pagina de gestion del cine para adninistradores")
@RequestMapping("/cineutn/gestionCine")
@Controller

public class GestionCineABM {
  
  @GetMapping
  @Operation(summary = "Obtener interface para gestion del Cine UTN")
  public String getGestionDeCine(){
    return "inicio/AdminABM";
  }

}
