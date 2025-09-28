package com.comunidadcineutn.cine.controller;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.comunidadcineutn.cine.exception.ExceptionNotFound;


@ControllerAdvice
public class ExceptionController {
  
  @ExceptionHandler(ExceptionNotFound.class)
  public String handlePeliculaException (ExceptionNotFound ex,
    RedirectAttributes ra){

      ra.addFlashAttribute("error", ex.getMessage());
      return "redirect:/";
    }

}
