/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.comunidadcineutn.cine.repository;

import com.comunidadcineutn.cine.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author santi
 */
public interface InterfaceReservaRepository extends JpaRepository<Reserva, Integer>{
    
}
