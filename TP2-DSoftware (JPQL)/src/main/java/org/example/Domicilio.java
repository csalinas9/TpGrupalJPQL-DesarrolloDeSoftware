package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity @Table(name = "domicilio")
public class Domicilio extends EntityId{
    private String nombreCalle;
    private String numeroCalle;

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public void setNumeroCalle(String numeroCalle) {
        this.numeroCalle = numeroCalle;
    }
}
