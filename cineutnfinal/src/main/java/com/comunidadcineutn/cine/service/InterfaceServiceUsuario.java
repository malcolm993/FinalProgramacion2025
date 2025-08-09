/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.dto.UsuarioEdicionDTO;
import com.comunidadcineutn.cine.model.Usuario;
import java.util.List;

/**
 *
 * @author santi
 */
public interface InterfaceServiceUsuario {
    
     public List<Usuario> getAll();
    
    public Usuario addUsuario(Usuario u) throws Exception;
    
    public void deleteUserPorId(Integer id);
    
    public Usuario findUsuarioPorId(Integer id);
    
    public Usuario editUsuario(UsuarioEdicionDTO u) throws Exception;

    public UsuarioEdicionDTO getUsuarioEdicionDTOById(Integer id);

}
