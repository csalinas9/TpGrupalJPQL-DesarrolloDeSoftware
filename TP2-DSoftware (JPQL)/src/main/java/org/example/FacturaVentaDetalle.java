package org.example;

import jakarta.persistence.*;

@Entity @Table(name = "factura_venta_detalle")
public class FacturaVentaDetalle extends EntityId{
    @ManyToOne @JoinColumn(nullable = false)
    private FacturaVenta factura;
    @ManyToOne @JoinColumn(nullable = false)
    private ListaPrecioArticulo listaPrecioArticulo;
    private String descripcion;
    @Column(nullable = false)
    private double cantidad;
    @Column(nullable = false)
    private double precioUnitario;
    private double porcentajeBonificacion;
    private double importeNeto;
    private double importeIva;
    @Column(nullable = false)
    private double importeSubtotal;

    public void setFactura(FacturaVenta factura) {
        this.factura = factura;
    }

    public void setListaPrecioArticulo(ListaPrecioArticulo listaPrecioArticulo) {
        this.listaPrecioArticulo = listaPrecioArticulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setPorcentajeBonificacion(double porcentajeBonificacion) {
        this.porcentajeBonificacion = porcentajeBonificacion;
    }

    public void setImporteNeto(double importeNeto) {
        this.importeNeto = importeNeto;
    }

    public void setImporteIva(double importeIva) {
        this.importeIva = importeIva;
    }

    public void setImporteSubtotal(double importeSubtotal) {
        this.importeSubtotal = importeSubtotal;
    }
}
