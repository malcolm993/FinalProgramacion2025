package com.comunidadcineutn.cine.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.comunidadcineutn.cine.dto.PeliculaAltaFuncionDTO;

import com.comunidadcineutn.cine.model.Funcion;
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

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Obtener funcion por ID")
    public ResponseEntity<Funcion> buscarPeliculaPorId(@PathVariable Integer id) {
        return funcionService.findFuncionPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/crudfunciones")
    @Operation(summary = "Obtener crud de las funciones")
    public String getAllFunciones(Model m) {
        List<Funcion> listaFunciones = funcionService.getAllFuncion();
        m.addAttribute("listaFunciones",  listaFunciones);
        return "funciones/crud-funciones";
    }
    /* 
    @PostMapping("/agregar")
    @Operation(summary = "Agregar funcion")
    public ResponseEntity<Funcion> agregarFuncion(@RequestBody Funcion f) {
        Funcion fNueva = funcionService.addFuncion(f);
        return new ResponseEntity<>(fNueva, HttpStatus.CREATED);
    }
    */

    @GetMapping("/agregar")
    @Operation(summary = "Obtengo el formulario para crear funcion")
    public String vistaAgregarFuncion(Model m) {
        m.addAttribute("funcion", new Funcion());
        List <PeliculaAltaFuncionDTO> lista = peliculaService.getListadoPeliculasAltaFuncion();
        m.addAttribute("listaPeliculas", lista );
        List<Sala> listaSalas = salaService.getAllSalas();
        m.addAttribute("listaSalas", listaSalas);
        List <LocalDate> fechas = funcionService.fechasHabilitadas(); 
        m.addAttribute("fechas", fechas);
        

        return "funciones/alta-funcion";
    }


    @PostMapping("/agregar")
    @Operation(summary = "Creo una funcion")
    public String agregarFuncion(@Valid @ModelAttribute
        ("funcion") Funcion f,
        BindingResult br,
        RedirectAttributes ra){
            if(br.hasErrors()){
                System.out.println(br.getAllErrors()); 
                return "funciones/alta-funcion";
            }
            Funcion creada = funcionService.addFuncion(f);
            ra.addFlashAttribute("mensaje", "Funcion con id "+creada.getIdFuncion()+
            " " +creada.getPelicula().getNombre() + " ha sido creada");
            return "redirect:/cineutn/funcion/crudfunciones";
        }


    @DeleteMapping("/eliminar")
    @Operation(summary = "Eliminar funcion por ID")
    public ResponseEntity<?> eliminarPelicula(@RequestParam(required = true, name = "id") Integer id) {
        return funcionService.findFuncionPorId(id)
                .map(funcion -> {
                    funcionService.deleteFuncionPorId(id);
                    return ResponseEntity.ok().body("Funcion '" + funcion.getIdFuncion() + " pelicula "
                            + funcion.getPelicula() + "' eliminada correctamente");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/editar/{id}")
    @Operation(summary = "Editar funcion")
    public ResponseEntity<Funcion> actualizarFuncion(
            @PathVariable Integer id,
            @Valid @RequestBody Funcion funcion) {

        if (!funcionService.existFuncionById(id)) {
            return ResponseEntity.notFound().build();
        }

        funcion.setIdFuncion(id); // Asegurar que se actualice la película correcta
        Funcion funcionActualizada = funcionService.editFuncion(funcion);
        return ResponseEntity.ok(funcionActualizada);
    }

}
