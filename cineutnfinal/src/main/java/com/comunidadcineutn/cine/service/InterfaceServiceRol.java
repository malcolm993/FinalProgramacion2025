package com.comunidadcineutn.cine.service;

import java.util.List;

import com.comunidadcineutn.cine.model.Rol;

public interface InterfaceServiceRol {
  public List<Rol> getAllRoles();

  public Rol addRol(Rol r);

  public void deleteRolPorId(Integer id);

  public Rol findRolPorId(Integer id);

  public Rol editRol(Rol r);

  public boolean existRolById(Integer id);

}
