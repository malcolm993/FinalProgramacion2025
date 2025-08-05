package com.comunidadcineutn.cine.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  private String nombre;
  @NotBlank
  private String apellido;

  @NotBlank
  @Email(message = "No tienen formate de email")
  private String email;

  @NotBlank(message = "ingresar una contraseña")
  @Size(min = 4, message = "no cumple con la cantidad minima de carateres (4)")
  private String password;

  @Transient
  private String confirmarPassword;

  @NotNull
  @ManyToOne // Relación muchos-a-uno en lugar de campo simple
  @JoinColumn(name = "rol_id") // Nombre de la columna en la tabla usuarios
  private Rol rolUsuario;

  @OneToMany(mappedBy = "usuarioComprador", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reserva> funcionesReservadasUser;

  public Usuario() {
  }


  public Usuario(Integer id, String nombre, String apellido, String email, String password) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.password = password;
  }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", password=" + password + ", confirmarPassword=" + confirmarPassword + ", rolUsuario=" + rolUsuario + '}';
    }
  
  

}
