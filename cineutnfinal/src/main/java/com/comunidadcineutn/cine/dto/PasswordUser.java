/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author santi
 */

@Getter
@Setter
public class PasswordUser {

    @NotNull
    private Integer id;

    @NotBlank(message = "Current Password must not be blank")
    private String currentPassword;

    @NotBlank(message = "New Password must not be blank")
    private String newPassword;

    @NotBlank(message = "Confirm Password must not be blank")
    private String confirmPassword;

    public PasswordUser() {
    }

    public PasswordUser(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PasswordUser{" + "id=" + id + ", currentPassword=" + currentPassword + ", newPassword=" + newPassword + ", confirmPassword=" + confirmPassword + '}';
    }
    
}
