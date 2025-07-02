package com.comunidadcineutn.cine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunidadcineutn.cine.model.Funcion;
import com.comunidadcineutn.cine.model.Pelicula;
import com.comunidadcineutn.cine.service.InterfaceServiceFuncion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Funciones", description = "API para gestión de funciones")
@RequestMapping("/funcion")
@RestController
public class FuncionController {
  @Autowired
  private InterfaceServiceFuncion funcionService;

  @GetMapping("/buscar/{id}")
    @Operation(summary = "Obtener funcion por ID")
    public ResponseEntity<Funcion> buscarPeliculaPorId(@PathVariable Integer id) {
        return funcionService.findFuncionPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/todas")
    @Operation(summary = "Obtener todas las funciones")
    public ResponseEntity<List<Funcion>> getAllFunciones() {
        List<Funcion> listaFunciones = funcionService.getAllFuncion();
        return new ResponseEntity<>(listaFunciones, HttpStatus.ACCEPTED);
    }

    @PostMapping("/agregar")
    @Operation(summary = "Agregar funcion")
    public ResponseEntity<Funcion> agregarFuncion(@RequestBody Funcion f) {
        Funcion fNueva = funcionService.addFuncion(f);
        return new ResponseEntity<>(fNueva, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar")
    @Operation(summary = "Eliminar funcion por ID")
    public ResponseEntity<?> eliminarPelicula(@RequestParam(required = true, name= "id") Integer id) {
        return funcionService.findFuncionPorId(id)
                .map(funcion -> {
                    funcionService.deleteFuncionPorId(id);
                    return ResponseEntity.ok().body("Funcion '" + funcion.getIdFuncion() + " pelicula "+ funcion.getPelicula() + "' eliminada correctamente");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/editar/{id}")
    @Operation(summary = "Editar funcion")
    public ResponseEntity<Funcion> actualizarFuncion(
            @PathVariable Integer id,
            @Valid
            @RequestBody Funcion funcion) {

        if (!funcionService.existFuncionById(id)) {
            return ResponseEntity.notFound().build();
        }

        funcion.setIdFuncion(id); // Asegurar que se actualice la película correcta
        Funcion funcionActualizada = funcionService.editFuncion(funcion);
        return ResponseEntity.ok(funcionActualizada);
    }

}
