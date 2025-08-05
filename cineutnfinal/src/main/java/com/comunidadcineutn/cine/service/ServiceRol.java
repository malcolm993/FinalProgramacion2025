package com.comunidadcineutn.cine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunidadcineutn.cine.exception.ExceptionNotFound;
import com.comunidadcineutn.cine.model.Rol;
import com.comunidadcineutn.cine.repository.InterfaceRolRepository;

@Service
public class ServiceRol implements InterfaceServiceRol {

  @Autowired
  private InterfaceRolRepository repositorioRol;
  @Override
  public List<Rol> getAllRoles() {
    return repositorioRol.findAll();
  }

  @Override
  public Rol addRol(Rol r) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addRol'");
  }

  @Override
  public void deleteRolPorId(Integer id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteRolPorId'");
  }

  @Override
  public Rol findRolPorId(Integer id) {
    return repositorioRol.findById(id).orElseThrow(
      ()-> new ExceptionNotFound("No existe el Rol con el Id buscado"));
  }

  @Override
  public Rol editRol(Rol r) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'editRol'");
  }

  @Override
  public boolean existRolById(Integer id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'existRolById'");
  }
  
}
