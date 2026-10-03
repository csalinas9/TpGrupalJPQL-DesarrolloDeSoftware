package org.example;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity @Table(name = "factura_venta")
public class FacturaVenta extends AuditoriaApp{
    private Long numero;
    @Temporal(TemporalType.TIMESTAMP) @Column(nullable = false)
    private Date fechaEmision;
    @ManyToOne @JoinColumn(nullable = false)
    private PuntoVenta puntoVenta;
    private double importeCobrado;
    private double importeSaldo;
    @Column(nullable = false)
    private double importeTotal;
    private String cae;
    @Temporal(TemporalType.TIMESTAMP)
    private Date caeFechaVencimiento;
    private String resultadoAfip;
    private String motivoRechazo;
    @Column(nullable = false)
    private String estado;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAnulacion;
    private String observaciones;
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<FacturaVentaDetalle> detalles;

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public void setPuntoVenta(PuntoVenta puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    public void setImporteCobrado(double importeCobrado) {
        this.importeCobrado = importeCobrado;
    }

    public void setImporteSaldo(double importeSaldo) {
        this.importeSaldo = importeSaldo;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public void setCae(String cae) {
        this.cae = cae;
    }

    public void setCaeFechaVencimiento(Date caeFechaVencimiento) {
        this.caeFechaVencimiento = caeFechaVencimiento;
    }

    public void setResultadoAfip(String resultadoAfip) {
        this.resultadoAfip = resultadoAfip;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaAnulacion(Date fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setDetalles(List<FacturaVentaDetalle> detalles) {
        this.detalles = detalles;
    }

    public List<FacturaVentaDetalle> getDetalles() {
        return detalles;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Date getFechaAnulacion() {
        return fechaAnulacion;
    }

    public String getEstado() {
        return estado;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public String getResultadoAfip() {
        return resultadoAfip;
    }

    public Date getCaeFechaVencimiento() {
        return caeFechaVencimiento;
    }

    public String getCae() {
        return cae;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public double getImporteSaldo() {
        return importeSaldo;
    }

    public double getImporteCobrado() {
        return importeCobrado;
    }

    public PuntoVenta getPuntoVenta() {
        return puntoVenta;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public Long getNumero() {
        return numero;
    }

}
