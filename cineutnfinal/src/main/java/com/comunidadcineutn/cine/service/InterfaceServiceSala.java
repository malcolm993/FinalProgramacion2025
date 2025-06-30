/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.model.Sala;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author santi
 */
public interface InterfaceServiceSala {
    
    public List<Sala> getAllFuncion();
    
    public Sala addFuncion(Sala s);
    
    public void deleteSalaPorId(Integer id);
    
    public Optional<Sala> findSalaPorId(Integer id);
    
    public Sala editSala(Sala s);
    
    public boolean existSalaById (Integer id);
    
    public boolean capacitySala (Integer id);
}
