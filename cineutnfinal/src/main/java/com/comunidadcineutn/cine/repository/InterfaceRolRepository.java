package com.comunidadcineutn.cine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comunidadcineutn.cine.model.Rol;

public interface InterfaceRolRepository extends JpaRepository <Rol, Integer> {
  
}
