package org.example;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity @Table(name = "articulo")
public class Articulo extends AuditoriaApp{
    @ManyToOne
    private Rubro rubro;
    @Column(nullable = false)
    private String codigo;
    @Column(nullable = false)
    private String denominacion;
    @ManyToOne
    private Marca marca;

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getDenominacion() { return denominacion; }

    public Rubro getRubro() { return rubro; }

    public String getCodigo() { return codigo; }

    public Marca getMarca() { return marca; }
}
