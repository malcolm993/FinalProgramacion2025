/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.service;

import com.comunidadcineutn.cine.dto.UsuarioEdicionDTO;
import com.comunidadcineutn.cine.exception.ExceptionNotFound;
import com.comunidadcineutn.cine.model.Usuario;
import com.comunidadcineutn.cine.repository.InterfaceUsarioRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author santi
 */

@Service
public class ServiceUsuario implements InterfaceServiceUsuario {

  @Autowired
  private InterfaceUsarioRepository repositorioUsuario;

  @Override
  public List<Usuario> getAll() {
    return repositorioUsuario.findAll();
  }

  @Override
  public Usuario addUsuario(Usuario u) throws Exception {
    System.out.println(u.toString());
    if (checkEmailDisponible(u) && checkPasswordSimilares(u)) {
      u = repositorioUsuario.save(u);
    }
    System.out.println("aca no tiene que llegar solo si se guarda");
    return u;
  }

  @Override
  public void deleteUserPorId(Integer id) {
    repositorioUsuario.deleteById(id);
  }

  @Override
  public Usuario findUsuarioPorId(Integer id) {
    return repositorioUsuario.findById(id).orElseThrow(
        () -> new ExceptionNotFound("No existe el usuario con el Id ingresado"));
  }

  @Override
  public Usuario editUsuario(UsuarioEdicionDTO usuarioActualizadoDTO) throws Exception {
    Usuario usuarioExistente = findUsuarioPorId(usuarioActualizadoDTO.getId());

    if (!usuarioExistente.getEmail().equals(usuarioActualizadoDTO.getEmail())) {

      String email = usuarioActualizadoDTO.getEmail();
      Integer id = usuarioActualizadoDTO.getId();
      if (repositorioUsuario.existsByEmailAndIdNot(email, id)) {
        throw new Exception("email ya esta registrado");
      }
    }

    usuarioExistente.setId(usuarioActualizadoDTO.getId());
    usuarioExistente.setApellido(usuarioActualizadoDTO.getApellido());
    usuarioExistente.setNombre(usuarioActualizadoDTO.getNombre());
    usuarioExistente.setEmail(usuarioActualizadoDTO.getEmail());
    return repositorioUsuario.save(usuarioExistente);
  }

  private boolean checkEmailDisponible(Usuario u) throws Exception {
    Optional<Usuario> encontrado = repositorioUsuario.findByEmail(u.getEmail());
    if (encontrado.isPresent()) {
      System.out.println("mail invalido :" + u.getEmail());
      throw new Exception("email ya esta registrado");
    }
    return true;
  }

  private boolean checkPasswordSimilares(Usuario u) throws Exception {
    String password1 = u.getPassword();
    String password2 = u.getConfirmarPassword();
    if (!password1.equals(password2)) {
      throw new Exception("Las contraseñas no coinciden");
    }
    return true;
  }

  @Override
  public UsuarioEdicionDTO getUsuarioEdicionDTOById(Integer id) {
    Usuario u = findUsuarioPorId(id);
    return new UsuarioEdicionDTO(u.getId(), u.getNombre(), u.getApellido(), u.getEmail(), u.getRolUsuario());
  }

}
