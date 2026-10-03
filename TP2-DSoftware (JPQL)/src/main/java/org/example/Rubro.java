package org.example;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity @Table(name = "rubro")
public class Rubro extends AuditoriaApp{
    @Column(nullable = false)
    private String codigo;
    @Column(nullable = false)
    private String denominacion;

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDenominacion() {
        return denominacion;
    }
}
