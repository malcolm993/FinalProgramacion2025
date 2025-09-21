/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.comunidadcineutn.cine.repository;

import com.comunidadcineutn.cine.model.Funcion;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author santi
 */
public interface InterfaceFuncionRepository extends JpaRepository<Funcion, Integer> {

    public List<Funcion> findBySalaIdSala(Integer idSala);

    public List<Funcion> findByFuncionHabilitadaTrue();

    public List<Funcion> findByPeliculaIdPelicula(Integer idPelicula);
    // nota para la generacion de query
    // f.sala.id "f" es la funcion, "sala" la sala dentro de la funcion, "id" aca
    // solo se tiene que poner id
    // para referirse a la id_sala de mi base de datos no hace falta el nombre
    // verdadero de la columna id_sala

    @Query("SELECT COUNT(f) > 0 FROM Funcion f WHERE f.sala.id = :salaIdSala " +
            "AND ((f.horaInicio < :horaFin AND f.horaFin > :horaInicio))")
    boolean existsBySalaAndOverlappingTime(
            @Param("salaIdSala") Integer salaId,
            @Param("horaInicio") LocalDateTime horaInicio,
            @Param("horaFin") LocalDateTime horaFin);
}
