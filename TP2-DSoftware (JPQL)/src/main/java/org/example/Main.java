package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Main{
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FacturacionPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Usuario usuario = new Usuario();
        usuario.setUsuario("jperez");
        usuario.setClave("1234");
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        em.persist(usuario);
        PuntoVenta puntoVenta = new PuntoVenta();
        puntoVenta.setNumero(1);
        puntoVenta.setDescripcion("Casa Central");
        puntoVenta.setFechaAlta(new Date(126,Calendar.SEPTEMBER,9));
        puntoVenta.setFechaModificacion(new Date(126,Calendar.SEPTEMBER,9));
        puntoVenta.setUsuarioCarga(usuario);
        puntoVenta.setUsuarioModificacion(usuario);
        em.persist(puntoVenta);
        Articulo articulo = new Articulo();
        articulo.setCodigo("1");
        articulo.setDenominacion("Bebida gaseosa sabor cola");
        articulo.setFechaAlta(new Date(126,Calendar.SEPTEMBER,9));
        articulo.setFechaModificacion(new Date(126,Calendar.SEPTEMBER,9));
        articulo.setUsuarioCarga(usuario);
        articulo.setUsuarioModificacion(usuario);
        em.persist(articulo);
        ListaPrecio lp = new ListaPrecio();
        lp.setCodigo("1");
        lp.setDenominacion("Lista precios Septiembre 2026");
        lp.setFechaAlta(new Date(126,Calendar.SEPTEMBER,9));
        lp.setFechaModificacion(new Date(126,Calendar.SEPTEMBER,9));
        lp.setUsuarioCarga(usuario);
        lp.setUsuarioModificacion(usuario);
        em.persist(lp);
        ListaPrecioArticulo lpa = new ListaPrecioArticulo();
        lpa.setListaPrecio(lp);
        lpa.setPrecioVenta(2500.00);
        lpa.setArticulo(articulo);
        lpa.setFechaAlta(new Date(126,Calendar.SEPTEMBER,9));
        lpa.setFechaModificacion(new Date(126,Calendar.SEPTEMBER,9));
        lpa.setUsuarioCarga(usuario);
        lpa.setUsuarioModificacion(usuario);
        em.persist(lpa);
        Articulo articulo2 = new Articulo();
        articulo2.setCodigo("2");
        articulo2.setDenominacion("Alimento a base de papas fritadas en aceite");
        articulo2.setFechaAlta(new Date(126,Calendar.SEPTEMBER,9));
        articulo2.setFechaModificacion(new Date(126,Calendar.SEPTEMBER,9));
        articulo2.setUsuarioCarga(usuario);
        articulo2.setUsuarioModificacion(usuario);
        em.persist(articulo2);
        ListaPrecioArticulo lpa2 = new ListaPrecioArticulo();
        lpa2.setListaPrecio(lp);
        lpa2.setPrecioVenta(3000.00);
        lpa2.setArticulo(articulo2);
        lpa2.setFechaAlta(new Date(126,Calendar.SEPTEMBER,9));
        lpa2.setFechaModificacion(new Date(126,Calendar.SEPTEMBER,9));
        lpa2.setUsuarioCarga(usuario);
        lpa2.setUsuarioModificacion(usuario);
        em.persist(lpa2);
        FacturaVenta fv = new FacturaVenta();
        fv.setFechaEmision(new Date(126,Calendar.SEPTEMBER,9));
        fv.setPuntoVenta(puntoVenta);
        fv.setImporteTotal(8000.00);
        fv.setEstado("Aprobada");
        fv.setFechaAlta(new Date(126,Calendar.SEPTEMBER,9));
        fv.setFechaModificacion(new Date(126, Calendar.SEPTEMBER,9));
        fv.setUsuarioCarga(usuario);
        fv.setUsuarioModificacion(usuario);
        fv.setDetalles(new ArrayList<FacturaVentaDetalle>());
        FacturaVentaDetalle fvd = new FacturaVentaDetalle();
        em.persist(fv);
        fvd.setFactura(fv);
        fvd.setListaPrecioArticulo(lpa);
        fvd.setCantidad(2.00);
        fvd.setPrecioUnitario(2500.00);
        fvd.setImporteSubtotal(5000.00);

        FacturaVentaDetalle fvd2 = new FacturaVentaDetalle();
        fvd2.setFactura(fv);
        fvd2.setListaPrecioArticulo(lpa2);
        fvd2.setCantidad(1.00);
        fvd2.setPrecioUnitario(3000.00);
        fvd2.setImporteSubtotal(3000.00);
        fv.getDetalles().add(fvd);
        fv.getDetalles().add(fvd2);

        em.getTransaction().commit();

        em.close();
        emf.close();
        System.out.println("cargado exitoso");
    }
}