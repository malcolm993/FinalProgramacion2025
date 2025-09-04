/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.securityconfiguration;

import com.comunidadcineutn.cine.model.Usuario;
import com.comunidadcineutn.cine.repository.InterfaceUsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author santi
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private InterfaceUsuarioRepository repostorioUsuario;

    @Override
    public UserDetails loadUserByUsername(String usermail) throws UsernameNotFoundException {
        Usuario u = repostorioUsuario.findByEmail(usermail).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        User autU = null;
        autU = new User(u.getEmail(),
                u.getPassword(),
                List.of(new SimpleGrantedAuthority(u.getRolUsuario().getNombreRol())));

        return autU;
    }
}
