package com.comunidadcineutn.cine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter 
@Setter
@Entity
@Table (name = "usuarios")
public class Usuario {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String nombre;
  private String apellido;
  private String email;
  private String password;
  
  @Enumerated(EnumType.STRING)
  private TipoUsuario tipoUser;
  
  public Usuario (){
    
  }

    public Usuario(int id, String nombre, String apellido, String email, String password, TipoUsuario tipoUser) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.tipoUser = tipoUser;
    }
  

}
