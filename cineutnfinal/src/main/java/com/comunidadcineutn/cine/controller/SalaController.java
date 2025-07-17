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
import com.comunidadcineutn.cine.model.Sala;
import com.comunidadcineutn.cine.service.InterfaceServiceSala;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Salas", description = "API para gestion de salas")
@RequestMapping ("/cineutn/sala")
@RestController
public class SalaController {
  
  @Autowired
  private InterfaceServiceSala salaService;

  @GetMapping("/buscar/{id}")
    @Operation(summary = "Obtener sala por ID")
    public ResponseEntity<Sala> buscarSalaPorId(@PathVariable Integer id) {
        return salaService.findSalaPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/todas")
    @Operation(summary = "Obtener todas las salas")
    public ResponseEntity<List<Sala>> getSalas() {
        List<Sala> listaSalas = salaService.getAllSalas();
        return new ResponseEntity<>(listaSalas, HttpStatus.ACCEPTED);
    }

    @PostMapping("/agregar")
    @Operation(summary = "Agregar sala")
    public ResponseEntity<Sala> agregarSala(@RequestBody Sala s) {
        Sala nueva = salaService.addSala(s);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar")
    @Operation(summary = "Eliminar sala por ID")
    public ResponseEntity<?> eliminarPelicula(@RequestParam(required = true, name= "id") Integer id) {
        return salaService.findSalaPorId(id)
                .map(sala -> {
                    salaService.deleteSalaPorId(id);
                    return ResponseEntity.ok().body("Sala Id " + sala.getIdSala() + " eliminada correctamente");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/editar/{id}")
    @Operation(summary = "Editar sala")
    public ResponseEntity<Sala> actualizarSala(
            @PathVariable Integer id,
            @Valid
            @RequestBody Sala sala) {

        if (!salaService.existSalaById(id)) {
            return ResponseEntity.notFound().build();
        }

        sala.setIdSala(id); // Asegurar que se actualice la película correcta
        
        Sala salaActualizada = salaService.editSala(sala);
        return ResponseEntity.ok(salaActualizada);
    }
    
}
