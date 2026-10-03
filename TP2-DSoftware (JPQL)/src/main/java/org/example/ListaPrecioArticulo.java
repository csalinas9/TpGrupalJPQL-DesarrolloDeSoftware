package org.example;

import jakarta.persistence.*;

@Entity @Table(name = "lista_precio_articulo")
public class ListaPrecioArticulo extends AuditoriaApp{
    @ManyToOne @JoinColumn(nullable = false)
    private ListaPrecio listaPrecio;
    @Column(nullable = false)
    private double precioVenta;
    @ManyToOne @JoinColumn(nullable = false)
    private Articulo articulo;

    public void setListaPrecio(ListaPrecio listaPrecio) {
        this.listaPrecio = listaPrecio;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
}
