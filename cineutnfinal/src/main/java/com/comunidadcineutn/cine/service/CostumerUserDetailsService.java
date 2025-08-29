/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.model.Usuario;
import com.comunidadcineutn.cine.repository.InterfaceUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author santi
 */
@Service
public class CostumerUserDetailsService implements UserDetailsService {

    @Autowired
    private InterfaceUsuarioRepository repostorioUsuario;

    @Override
    public UserDetails loadUserByUsername(String usermail) throws UsernameNotFoundException {
        return repostorioUsuario.findByEmail(usermail).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        
    }

}
