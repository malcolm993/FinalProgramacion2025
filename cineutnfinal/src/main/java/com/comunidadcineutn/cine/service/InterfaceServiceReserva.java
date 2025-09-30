/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.comunidadcineutn.cine.service;


import com.comunidadcineutn.cine.dto.ReservaFormDTO;
import com.comunidadcineutn.cine.model.Reserva;
import com.comunidadcineutn.cine.model.Usuario;

import java.util.List;

/**
 *
 * @author santi
 */
public interface InterfaceServiceReserva {
    
    public List<Reserva> getAll();
    
    public Reserva addReserva(Reserva r);
    
    public void deleteReservaPorId(Integer idReserva, Integer idUsuario) throws Exception;
    
    public Reserva findReservaPorId(Integer id);
    
    public Reserva editReserva(Reserva res);

    public Reserva crearReservaFromDTO(Usuario u, ReservaFormDTO r);

    public List<Reserva> getRerservasPorIdUsario(Integer id);

    public List<Reserva> findReservasExpiradas();

    public List<Reserva> findReservasNoExpirada();

}
