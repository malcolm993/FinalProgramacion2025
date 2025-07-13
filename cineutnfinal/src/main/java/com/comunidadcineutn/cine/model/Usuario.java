package com.comunidadcineutn.cine.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter 
@Setter
@Entity
@Table (name = "usuarios")
public class Usuario {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @NotBlank
  private String nombre;
  @NotBlank
  private String apellido;
  
  @NotBlank
  @Email
  private String email;
  private String password;
  
  @Enumerated(EnumType.STRING)
  private TipoUsuario tipoUser;
  
  @OneToMany(mappedBy = "usuarioComprador", cascade= CascadeType.ALL, orphanRemoval = true)
  private List<Reserva> funcionesReservadasUser;
  
  public Usuario (){
    
  }

    public Usuario( String nombre, String apellido, String email, String password, TipoUsuario tipoUser) {
    
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.tipoUser = tipoUser;
        this.funcionesReservadasUser = new ArrayList<>();
    }
  

}
