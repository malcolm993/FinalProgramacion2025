package com.comunidadcineutn.cine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "salas") // Opcional si quieres nombre personalizado para la tabla
public class Sala implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSala;

    // Opcional para nombre de columna
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private int cantDeButacas;

    @Enumerated(EnumType.STRING)
    // Mapea el Enum como String en la BD
    private TipoDeSala tipoSala;

    @OneToMany(mappedBy = "sala")
    private List<Funcion> funcionesRealizadasSala;

    public Sala() {
    }

    public Sala(int id, int cantDeButacas, TipoDeSala tipoSala) {
        this.idSala = id;
        this.cantDeButacas = cantDeButacas;
        this.tipoSala = tipoSala;
    }

    @Override
    public String toString() {
        return "Sala{" + "idSala=" + idSala + ", cantDeButacas=" + cantDeButacas + ", tipoSala=" + tipoSala + '}';
    }

}
