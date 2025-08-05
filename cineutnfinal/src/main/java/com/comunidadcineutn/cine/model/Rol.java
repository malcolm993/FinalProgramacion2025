package com.comunidadcineutn.cine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Rol {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  private String nombreRol;

  @NotBlank
  private String descripcion; 

    public Rol() {
    }

    public Rol(Integer id, String nombreRol, String descripcion) {
        this.id = id;
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Rol{" + "id=" + id + ", nombreRol=" + nombreRol + ", descripcion=" + descripcion + '}';
    }
  
  
  

}
