package com.comunidadcineutn.cine.controller;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.comunidadcineutn.cine.exception.ExceptionPeliculas;


@ControllerAdvice
public class ExceptionController {
  
  @ExceptionHandler(ExceptionPeliculas.class)
  public String handlePeliculaException (ExceptionPeliculas ex,
    RedirectAttributes ra){

      ra.addFlashAttribute("error", ex.getMessage());
      return "redirect:/cineutn/pelicula/crudpeliculas";
    }

}
