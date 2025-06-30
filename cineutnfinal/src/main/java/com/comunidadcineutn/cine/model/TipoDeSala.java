/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comunidadcineutn.cine.model;

/**
 *
 * @author santi
 */
public enum TipoDeSala {
    _2D("Sala 2D"),
    _3D("Sala 3D");
    
    private String descripcion;

    private TipoDeSala(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion(){
        return this.descripcion;
    }
    
    
}
