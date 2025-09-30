/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.comunidadcineutn.cine.repository;

import com.comunidadcineutn.cine.model.Reserva;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author santi
 */
public interface InterfaceReservaRepository extends JpaRepository<Reserva, Integer> {
    public List<Reserva> findByUsuarioCompradorId(Integer id);

    @Modifying
    @Query("UPDATE Reserva r SET r.expirada = true " +
            "WHERE r.usuarioComprador.id = :id " +
            "AND r.funcionReservada.horaInicio < :fechaHoraActual " +
            "AND r.expirada = false")
    public void cambioEstadoReservasExpiradaUsuario(@Param("id") Integer id,
            @Param("fechaHoraActual") LocalDateTime fechaHoraActual);

    @Modifying
    @Query("UPDATE Reserva r SET r.expirada = true " +
            "WHERE r.funcionReservada.horaInicio < :fechaHoraActual " +
            "AND r.expirada = false")
    public void expirarTodasLasReservasVencidas(@Param("fechaHoraActual") LocalDateTime fechaHoraActual);
    
    public List<Reserva> findByExpiradaTrue();
    public List<Reserva> findByExpiradaFalse();

}
