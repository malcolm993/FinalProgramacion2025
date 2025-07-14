/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.model.Usuario;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author santi
 */
public interface InterfaceServiceUsuario {
    
     public List<Usuario> getAll();
    
    public Usuario addUsuario(Usuario u);
    
    public void deleteUserPorId(Integer id);
    
    public Optional<Usuario> findUsuarioPorId(Integer id);
    
    public Usuario editUsuario(Usuario u);
}
