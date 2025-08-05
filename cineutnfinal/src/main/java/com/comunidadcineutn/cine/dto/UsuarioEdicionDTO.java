package com.comunidadcineutn.cine.dto;

import com.comunidadcineutn.cine.model.Rol;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UsuarioEdicionDTO {

    private Integer id;

    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;

    @NotBlank
    @Email(message = "No tienen formate de email")
    private String email;

    private Rol rolUsuario;

    public UsuarioEdicionDTO() {
    }

    public UsuarioEdicionDTO(Integer id, String nombre, String apellido, String email, Rol r) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rolUsuario = r;
    }

    @Override
    public String toString() {
        return "UsuarioEdicionDTO{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + '}';
    }
    
    

}
