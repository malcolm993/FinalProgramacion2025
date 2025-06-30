package com.comunidadcineutn.cine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity
@Table(name = "salas") // Opcional si quieres nombre personalizado para la tabla
public class Sala implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

// Opcional para nombre de columna
    private int cantDeButacas;

    @Enumerated(EnumType.STRING) 
// Mapea el Enum como String en la BD

    private TipoDeSala tipoSala;



}

