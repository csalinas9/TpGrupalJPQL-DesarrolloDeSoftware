package org.example;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Carga un conjunto de datos de prueba pensado específicamente para que
 * las 20 consultas de ConsultasJPQL devuelvan resultados representativos:
 * - Rubro "Electrónica" con varios artículos (Ejercicio 3)
 * - Facturas en 2024 y en 2026 (Ejercicio 4)
 * - Facturas EMITIDA / ANULADA / PENDIENTE / APROBADA (Ejercicios 5, 7, 20)
 * - Clientes que matchean por texto "electro" y por CUIT "20-" (Ejercicio 6)
 * - Puntos de venta 1, 2 y 5 (Ejercicio 9)
 * - Usuario "admin" con más de 5 facturas cargadas (Ejercicios 10 y 16)
 * - Artículos con y sin marca (Ejercicio 12)
 * - Marca "DELL" presente en varias facturas (Ejercicio 13)
 * - Artículos que nunca fueron facturados (Ejercicio 19)
 */
public class DataSeeder {

    private static final Date FECHA_BASE = fecha(2026, 9, 1);

    public static void seed(EntityManager em) {
        em.getTransaction().begin();

        // ===== USUARIOS =====
        Usuario admin = crearUsuario(em, "admin", "admin123", "Administrador", "Sistema");
        Usuario jperez = crearUsuario(em, "jperez", "1234", "Juan", "Pérez");
        Usuario mgarcia = crearUsuario(em, "mgarcia", "1234", "María", "García");
        Usuario lrodriguez = crearUsuario(em, "lrodriguez", "1234", "Luis", "Rodríguez");

        // ===== PUNTOS DE VENTA =====
        PuntoVenta pv1 = crearPuntoVenta(em, 1, "Casa Central", admin);
        PuntoVenta pv2 = crearPuntoVenta(em, 2, "Sucursal Norte", admin);
        PuntoVenta pv5 = crearPuntoVenta(em, 5, "Sucursal Sur", admin);

        // ===== RUBROS =====
        Rubro rubroElectronica = crearRubro(em, "R01", "Electrónica", admin);
        Rubro rubroAlimentos = crearRubro(em, "R02", "Alimentos", admin);
        Rubro rubroBebidas = crearRubro(em, "R03", "Bebidas", admin);

        // ===== MARCAS =====
        Marca marcaDell = crearMarca(em, "M01", "DELL", admin);
        Marca marcaHp = crearMarca(em, "M02", "HP", admin);
        Marca marcaSamsung = crearMarca(em, "M03", "Samsung", admin);
        Marca marcaCocaCola = crearMarca(em, "M04", "Coca-Cola", admin);

        // ===== ARTÍCULOS =====
        Articulo art1 = crearArticulo(em, "A01", "Notebook Dell Inspiron 15", rubroElectronica, marcaDell, admin);
        Articulo art2 = crearArticulo(em, "A02", "Monitor Dell 24 pulgadas", rubroElectronica, marcaDell, admin);
        Articulo art3 = crearArticulo(em, "A03", "Notebook HP Pavilion", rubroElectronica, marcaHp, admin);
        Articulo art4 = crearArticulo(em, "A04", "Mouse inalámbrico HP", rubroElectronica, marcaHp, admin);
        Articulo art5 = crearArticulo(em, "A05", "Smart TV Samsung 50 pulgadas", rubroElectronica, marcaSamsung, admin);
        Articulo art6 = crearArticulo(em, "A06", "Heladera Samsung No Frost", rubroElectronica, marcaSamsung, admin);
        Articulo art7 = crearArticulo(em, "A07", "Gaseosa sabor cola 2.25L", rubroBebidas, marcaCocaCola, admin);
        Articulo art8 = crearArticulo(em, "A08", "Agua mineral sin gas 500ml", rubroBebidas, null, admin);
        Articulo art9 = crearArticulo(em, "A09", "Galletitas dulces surtidas", rubroAlimentos, null, admin);
        Articulo art10 = crearArticulo(em, "A10", "Teclado mecánico genérico", rubroElectronica, null, admin);

        // ===== LISTA DE PRECIOS =====
        ListaPrecio lista = crearListaPrecio(em, "LP01", "Lista general 2026", admin);

        ListaPrecioArticulo lpa1 = crearLPA(em, lista, art1, 850000.00, admin);
        ListaPrecioArticulo lpa2 = crearLPA(em, lista, art2, 320000.00, admin);
        ListaPrecioArticulo lpa3 = crearLPA(em, lista, art3, 780000.00, admin);
        ListaPrecioArticulo lpa4 = crearLPA(em, lista, art4, 15000.00, admin);
        ListaPrecioArticulo lpa5 = crearLPA(em, lista, art5, 650000.00, admin);
        ListaPrecioArticulo lpa6 = crearLPA(em, lista, art6, 900000.00, admin);
        ListaPrecioArticulo lpa7 = crearLPA(em, lista, art7, 2500.00, admin);
        ListaPrecioArticulo lpa8 = crearLPA(em, lista, art8, 800.00, admin);
        crearLPA(em, lista, art9, 3000.00, admin);   // nunca se factura (Ejercicio 19)
        crearLPA(em, lista, art10, 45000.00, admin); // nunca se factura (Ejercicio 19)

        // ===== CLIENTES =====
        crearCliente(em, "30-12345678-9", "Electro Hogar S.A.", "contacto@electrohogar.com",
                "011-4444-1111", "Av. Corrientes", "1234", admin);
        crearCliente(em, "20-98765432-1", "Comercial San Martín", "ventas@sanmartin.com",
                "011-4444-2222", "San Martín", "567", admin);
        crearCliente(em, "27-11223344-5", "Distribuidora ElectroSur", "info@electrosur.com",
                "011-4444-3333", "Av. Rivadavia", "8900", admin);
        crearCliente(em, "23-44556677-8", "Kiosco La Esquina", "kiosco@laesquina.com",
                "011-4444-4444", "Belgrano", "45", admin);

        // ===== FACTURAS DE VENTA =====
        // --- Usuario admin: 7 facturas (> 5, para Ejercicios 10 y 16) ---
        crearFactura(em, 1L, fecha(2026, 9, 1), pv1, "EMITIDA", admin,
                Arrays.asList(det(lpa1, 1, 850000.00), det(lpa4, 2, 15000.00)));
        crearFactura(em, 2L, fecha(2026, 9, 3), pv1, "EMITIDA", admin,
                Arrays.asList(det(lpa2, 1, 320000.00)));
        crearFactura(em, 3L, fecha(2026, 9, 5), pv2, "EMITIDA", admin,
                Arrays.asList(det(lpa3, 1, 780000.00), det(lpa4, 1, 15000.00)));
        crearFactura(em, 4L, fecha(2024, 3, 10), pv1, "EMITIDA", admin,
                Arrays.asList(det(lpa5, 1, 650000.00)));
        crearFactura(em, 5L, fecha(2026, 9, 10), pv2, "PENDIENTE", admin,
                Arrays.asList(det(lpa7, 10, 2500.00)));
        crearFacturaAnulada(em, 6L, fecha(2026, 9, 12), pv1, admin,
                Arrays.asList(det(lpa8, 5, 800.00)), fecha(2026, 9, 13));
        crearFactura(em, 7L, fecha(2026, 9, 15), pv2, "EMITIDA", admin,
                Arrays.asList(det(lpa6, 1, 900000.00)));

        // --- Usuario jperez: 3 facturas ---
        crearFactura(em, 8L, fecha(2024, 6, 20), pv1, "EMITIDA", jperez,
                Arrays.asList(det(lpa1, 1, 850000.00), det(lpa2, 1, 320000.00)));
        crearFactura(em, 9L, fecha(2026, 9, 18), pv5, "APROBADA", jperez,
                Arrays.asList(det(lpa7, 4, 2500.00)));
        crearFactura(em, 10L, fecha(2026, 9, 20), pv1, "EMITIDA", jperez,
                Arrays.asList(det(lpa3, 1, 780000.00)));

        // --- Usuario mgarcia: 2 facturas ---
        crearFactura(em, 11L, fecha(2026, 9, 21), pv5, "EMITIDA", mgarcia,
                Arrays.asList(det(lpa5, 1, 650000.00), det(lpa6, 1, 900000.00)));
        crearFactura(em, 12L, fecha(2026, 9, 22), pv2, "PENDIENTE", mgarcia,
                Arrays.asList(det(lpa8, 3, 800.00)));

        // --- Usuario lrodriguez: 2 facturas ---
        crearFactura(em, 13L, fecha(2026, 9, 22), pv1, "EMITIDA", lrodriguez,
                Arrays.asList(det(lpa4, 3, 15000.00)));
        crearFacturaAnulada(em, 14L, fecha(2026, 9, 23), pv5, lrodriguez,
                Arrays.asList(det(lpa7, 2, 2500.00)), fecha(2026, 9, 23));

        em.getTransaction().commit();
        System.out.println("✓ Datos de prueba insertados correctamente (14 facturas, 10 artículos, 4 clientes).");
    }

    // ========================================================================
    // HELPERS DE CREACIÓN
    // ========================================================================

    private static void auditar(AuditoriaApp entidad, Usuario usuario, Date fecha) {
        entidad.setFechaAlta(fecha);
        entidad.setFechaModificacion(fecha);
        entidad.setUsuarioCarga(usuario);
        entidad.setUsuarioModificacion(usuario);
    }

    private static Usuario crearUsuario(EntityManager em, String usuario, String clave, String nombre, String apellido) {
        Usuario u = new Usuario();
        u.setUsuario(usuario);
        u.setClave(clave);
        u.setNombre(nombre);
        u.setApellido(apellido);
        em.persist(u);
        return u;
    }

    private static PuntoVenta crearPuntoVenta(EntityManager em, int numero, String descripcion, Usuario usuarioCarga) {
        PuntoVenta pv = new PuntoVenta();
        pv.setNumero(numero);
        pv.setDescripcion(descripcion);
        pv.setTipoEmision("A");
        auditar(pv, usuarioCarga, FECHA_BASE);
        em.persist(pv);
        return pv;
    }

    private static Rubro crearRubro(EntityManager em, String codigo, String denominacion, Usuario usuarioCarga) {
        Rubro r = new Rubro();
        r.setCodigo(codigo);
        r.setDenominacion(denominacion);
        auditar(r, usuarioCarga, FECHA_BASE);
        em.persist(r);
        return r;
    }

    private static Marca crearMarca(EntityManager em, String codigo, String denominacion, Usuario usuarioCarga) {
        Marca m = new Marca();
        m.setCodigo(codigo);
        m.setDenominacion(denominacion);
        auditar(m, usuarioCarga, FECHA_BASE);
        em.persist(m);
        return m;
    }

    private static Articulo crearArticulo(EntityManager em, String codigo, String denominacion,
                                           Rubro rubro, Marca marca, Usuario usuarioCarga) {
        Articulo a = new Articulo();
        a.setCodigo(codigo);
        a.setDenominacion(denominacion);
        a.setRubro(rubro);
        a.setMarca(marca);
        auditar(a, usuarioCarga, FECHA_BASE);
        em.persist(a);
        return a;
    }

    private static ListaPrecio crearListaPrecio(EntityManager em, String codigo, String denominacion, Usuario usuarioCarga) {
        ListaPrecio lp = new ListaPrecio();
        lp.setCodigo(codigo);
        lp.setDenominacion(denominacion);
        auditar(lp, usuarioCarga, FECHA_BASE);
        em.persist(lp);
        return lp;
    }

    private static ListaPrecioArticulo crearLPA(EntityManager em, ListaPrecio lista, Articulo articulo,
                                                 double precio, Usuario usuarioCarga) {
        ListaPrecioArticulo lpa = new ListaPrecioArticulo();
        lpa.setListaPrecio(lista);
        lpa.setArticulo(articulo);
        lpa.setPrecioVenta(precio);
        auditar(lpa, usuarioCarga, FECHA_BASE);
        em.persist(lpa);
        return lpa;
    }

    private static Cliente crearCliente(EntityManager em, String cuitCuil, String denominacion, String email,
                                         String telefono, String calle, String numeroCalle, Usuario usuarioCarga) {
        Contacto contacto = new Contacto();
        contacto.setEmail(email);
        contacto.setTelefono(telefono);
        contacto.setCelular(telefono);
        em.persist(contacto);

        Domicilio domicilio = new Domicilio();
        domicilio.setNombreCalle(calle);
        domicilio.setNumeroCalle(numeroCalle);
        em.persist(domicilio);

        Cliente c = new Cliente();
        c.setCuitCuil(cuitCuil);
        c.setDenominacion(denominacion);
        c.setContacto(contacto);
        c.setDomicilio(domicilio);
        auditar(c, usuarioCarga, FECHA_BASE);
        em.persist(c);
        return c;
    }

    private static FacturaVenta crearFactura(EntityManager em, Long numero, Date fechaEmision, PuntoVenta puntoVenta,
                                              String estado, Usuario usuario, List<DetalleTmp> detalles) {
        return crearFacturaInterna(em, numero, fechaEmision, puntoVenta, estado, usuario, detalles, null);
    }

    private static FacturaVenta crearFacturaAnulada(EntityManager em, Long numero, Date fechaEmision, PuntoVenta puntoVenta,
                                                      Usuario usuario, List<DetalleTmp> detalles, Date fechaAnulacion) {
        return crearFacturaInterna(em, numero, fechaEmision, puntoVenta, "ANULADA", usuario, detalles, fechaAnulacion);
    }

    private static FacturaVenta crearFacturaInterna(EntityManager em, Long numero, Date fechaEmision, PuntoVenta puntoVenta,
                                                      String estado, Usuario usuario, List<DetalleTmp> detalles,
                                                      Date fechaAnulacion) {
        FacturaVenta fv = new FacturaVenta();
        fv.setNumero(numero);
        fv.setFechaEmision(fechaEmision);
        fv.setPuntoVenta(puntoVenta);
        fv.setEstado(estado);
        fv.setFechaAnulacion(fechaAnulacion);
        fv.setImporteTotal(0.0);
        fv.setDetalles(new ArrayList<>());
        auditar(fv, usuario, fechaEmision);
        em.persist(fv); // IDENTITY: el INSERT se ejecuta ahora y obtenemos el id

        double total = 0.0;
        for (DetalleTmp d : detalles) {
            FacturaVentaDetalle fvd = new FacturaVentaDetalle();
            fvd.setFactura(fv);
            fvd.setListaPrecioArticulo(d.lpa);
            fvd.setCantidad(d.cantidad);
            fvd.setPrecioUnitario(d.precioUnitario);
            double subtotal = d.cantidad * d.precioUnitario;
            fvd.setImporteSubtotal(subtotal);
            em.persist(fvd);
            fv.getDetalles().add(fvd);
            total += subtotal;
        }
        fv.setImporteTotal(total);
        return fv;
    }

    private static DetalleTmp det(ListaPrecioArticulo lpa, double cantidad, double precioUnitario) {
        return new DetalleTmp(lpa, cantidad, precioUnitario);
    }

    private static Date fecha(int anio, int mesUnoADoce, int dia) {
        Calendar c = Calendar.getInstance();
        c.set(anio, mesUnoADoce - 1, dia, 12, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /** Estructura auxiliar para armar la lista de renglones de una factura antes de persistirlos. */
    private static class DetalleTmp {
        final ListaPrecioArticulo lpa;
        final double cantidad;
        final double precioUnitario;

        DetalleTmp(ListaPrecioArticulo lpa, double cantidad, double precioUnitario) {
            this.lpa = lpa;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
        }
    }
}
