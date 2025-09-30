/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.dto.ReservaFormDTO;
import com.comunidadcineutn.cine.exception.ButacasInsuficientesException;
import com.comunidadcineutn.cine.exception.ExceptionNotFound;
import com.comunidadcineutn.cine.model.Funcion;
import com.comunidadcineutn.cine.model.Reserva;
import com.comunidadcineutn.cine.model.Usuario;
import com.comunidadcineutn.cine.repository.InterfaceFuncionRepository;
import com.comunidadcineutn.cine.repository.InterfaceReservaRepository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author santi
 */
@Transactional
@Service
public class ServiceReserva implements InterfaceServiceReserva {
  @Autowired
  private InterfaceReservaRepository repositorioReservas;

  @Autowired
  private InterfaceFuncionRepository repositorioFuncion;

  @Override
  public List<Reserva> getAll() {
    repositorioReservas.expirarTodasLasReservasVencidas(LocalDateTime.now());
    return repositorioReservas.findAll();
  }

  @Override
  public Reserva addReserva(Reserva r) {
    // Funcion f =
    // funcionService.findFuncionPorId(r.getFuncionReservada().getIdFuncion());
    Funcion f = repositorioFuncion.findById(r.getFuncionReservada().getIdFuncion())
        .orElseThrow(() -> new ExceptionNotFound("no se encontro la funcion con el Id"));
    f.setCantButacasReservadas(f.getCantButacasReservadas() + r.getCantidadEntradas());
    System.out.println(" servidor reserva addReserva funcion: " + f);
    return repositorioReservas.save(r);
  }

  @Override
  public void deleteReservaPorId(Integer idReserva, Integer idUsario) throws Exception {
    Reserva r = findReservaPorId(idReserva);
    if (!validacionUsuario(idUsario, r)) {
      throw new SecurityException("ERROR EN LA VALIDACION DE USUARIO");
    }
    if (!r.isCancelable()) {
      throw new IllegalStateException("Esta funcion no puede ser cancelada ya que es se proyectara en menos de 24 hs");
    }
    Funcion funcion = repositorioFuncion.findById(r.getFuncionReservada().getIdFuncion()).orElseThrow(() -> 
    new ExceptionNotFound("No se encontro la funcion con el Id solicitado"));
    funcion.setCantButacasReservadas(
        funcion.getCantButacasReservadas() - r.getCantidadEntradas());
    repositorioFuncion.save(funcion);
    repositorioReservas.deleteById(idReserva);
    System.out.println(" se elimino correctamente la reserva jejeee");
  }

  private boolean validacionUsuario(Integer idUsuario, Reserva reserva) {
    return reserva.getUsuarioComprador().getId() == idUsuario;
  }

  @Override
  public Reserva findReservaPorId(Integer id) {
    return repositorioReservas.findById(id).orElseThrow(
        () -> new ExceptionNotFound("No existe reserva que tengan el Id ingresado"));
  }

  @Override
  public Reserva editReserva(Reserva res) {
    return repositorioReservas.save(res);

  }

  @Override
  public Reserva crearReservaFromDTO(Usuario u, ReservaFormDTO r) {
    // TODO Auto-generated method stub
    Funcion funcion = repositorioFuncion.findById(r.getIdFuncion())
        .orElseThrow(() -> new ExceptionNotFound("No existe funcion con el Id ingresado"));
    ;

    int costoTotal = funcion.getPrecio() * r.getCantidadEntradas();
    Reserva toReservaEntidad = new Reserva();
    if (r.getCantidadEntradas() > funcion.getButacasDisponibles()) {
      throw new ButacasInsuficientesException("No hay cantidad de entradas pedidas excede las disponibles");
    }

    toReservaEntidad.setFuncionReservada(funcion);
    toReservaEntidad.setUsuarioComprador(u);
    toReservaEntidad.setCantidadEntradas(r.getCantidadEntradas());
    toReservaEntidad.setCostoReserva(costoTotal);

    return toReservaEntidad;
  }

  @Override
  public List<Reserva> getRerservasPorIdUsario(Integer id) {
    repositorioReservas.cambioEstadoReservasExpiradaUsuario(id, LocalDateTime.now());
    return repositorioReservas.findByUsuarioCompradorId(id);
  }

  @Override
  public List<Reserva> findReservasExpiradas() {
    repositorioReservas.expirarTodasLasReservasVencidas(LocalDateTime.now());
    return repositorioReservas.findByExpiradaTrue();
  }

  @Override
  public List<Reserva> findReservasNoExpirada() {
    repositorioReservas.expirarTodasLasReservasVencidas(LocalDateTime.now());
    return repositorioReservas.findByExpiradaFalse();
  }

}
