package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Date;

/**
 * SOLUCIONES TP GRUPAL JPQL - Consultas Avanzadas
 * Estudiante: [Nombre]
 * Materia: Teoría de Desarrollo - Diseño de Sistemas
 *
 * Este archivo contiene las 20 consultas JPQL solicitadas, organizadas por niveles.
 */

public class ConsultasJPQL {

    private EntityManager em;

    public ConsultasJPQL(EntityManager em) {
        this.em = em;
    }

    // ============================================================================
    // NIVEL 1: CONSULTAS BÁSICAS Y PROYECCIONES
    // ============================================================================

    /**
     * Ejercicio 1: Consulta de Entidades Completas
     * Consigna: Obtener la lista completa de todas las facturas de venta
     * registradas en el sistema.
     */
    public List<FacturaVenta> ejercicio1_ListarTodasLasFacturas() {
        String jpql = "SELECT f FROM FacturaVenta f";

        TypedQuery<FacturaVenta> query = em.createQuery(jpql, FacturaVenta.class);
        return query.getResultList();
    }

    /**
     * Ejercicio 2: Proyección de Atributos Específicos
     * Consigna: Seleccionar únicamente el número de factura, la fecha de emisión
     * y el importe total de todas las facturas de venta.
     *
     * Nota: Se retorna un array de Objects debido a la proyección múltiple
     */
    public List<Object[]> ejercicio2_ProyeccionFacturas() {
        String jpql = "SELECT f.numero, f.fechaEmision, f.importeTotal FROM FacturaVenta f";

        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        return query.getResultList();
    }

    /**
     * Ejercicio 3: Filtrado por Igualdad (WHERE)
     * Consigna: Obtener todos los artículos que pertenecen a un rubro
     * con una denominación específica (ej. "Electrónica").
     */
    public List<Articulo> ejercicio3_ArticulosPorRubro(String denominacionRubro) {
        String jpql = "SELECT a FROM Articulo a " +
                "WHERE a.rubro.denominacion = :denominacion";

        TypedQuery<Articulo> query = em.createQuery(jpql, Articulo.class);
        query.setParameter("denominacion", denominacionRubro);
        return query.getResultList();
    }

    /**
     * Ejercicio 4: Filtrado por Rango de Fechas (BETWEEN)
     * Consigna: Listar todas las facturas de venta emitidas dentro
     * de un rango de fechas determinado.
     */
    public List<FacturaVenta> ejercicio4_FacturasPorFecha(Date fechaDesde, Date fechaHasta) {
        String jpql = "SELECT f FROM FacturaVenta f " +
                "WHERE f.fechaEmision BETWEEN :fechaDesde AND :fechaHasta";

        TypedQuery<FacturaVenta> query = em.createQuery(jpql, FacturaVenta.class);
        query.setParameter("fechaDesde", fechaDesde);
        query.setParameter("fechaHasta", fechaHasta);
        return query.getResultList();
    }

    // ============================================================================
    // NIVEL 2: CONDICIONALES COMBINADOS, OPERADORES DE TEXTO Y AGREGACIONES
    // ============================================================================

    /**
     * Ejercicio 5: Condicionales Complejos y Verificación de Nulos (AND, OR, IS NULL)
     * Consigna: Obtener las facturas cuyo estado sea "EMITIDA", con un importe
     * total superior a $10,000 y que no hayan sido anuladas (fechaAnulacion sea nula).
     */
    public List<FacturaVenta> ejercicio5_FacturasEmitidas() {
        String jpql = "SELECT f FROM FacturaVenta f " +
                "WHERE f.estado = :estado " +
                "AND f.importeTotal > :importeMinimo " +
                "AND f.fechaAnulacion IS NULL";

        TypedQuery<FacturaVenta> query = em.createQuery(jpql, FacturaVenta.class);
        query.setParameter("estado", "EMITIDA");
        query.setParameter("importeMinimo", 10000.0);
        return query.getResultList();
    }

    /**
     * Ejercicio 6: Búsqueda por Patrón de Texto (LIKE y LOWER)
     * Consigna: Buscar todos los clientes cuya denominación contenga un texto
     * parcial (sin importar mayúsculas/minúsculas) o cuyo CUIT/CUIL comience con "20-".
     *
     * Nota: Se asume que Cliente tiene denominación y cuitCuil
     */
    public List<Cliente> ejercicio6_ClientesPorPatron(String textoBusqueda) {
        String jpql = "SELECT c FROM Cliente c " +
                "WHERE LOWER(c.denominacion) LIKE LOWER(:texto) " +
                "OR c.cuitCuil LIKE :cuit";

        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
        query.setParameter("texto", "%" + textoBusqueda + "%");
        query.setParameter("cuit", "20-%");
        return query.getResultList();
    }

    /**
     * Ejercicio 7: Valores Distintos y Ordenamiento (DISTINCT y ORDER BY)
     * Consigna: Obtener sin duplicados todos los estados posibles registrados
     * en las facturas de venta, ordenados alfabéticamente de forma ascendente.
     */
    public List<String> ejercicio7_EstadosDistintos() {
        String jpql = "SELECT DISTINCT f.estado FROM FacturaVenta f " +
                "ORDER BY f.estado ASC";

        TypedQuery<String> query = em.createQuery(jpql, String.class);
        return query.getResultList();
    }

    /**
     * Ejercicio 8: Funciones de Agregación Simples (COUNT, SUM, AVG)
     * Consigna: Obtener la cantidad total de facturas emitidas, la suma
     * acumulada de sus importes totales y el importe promedio devuelto
     * en un solo objeto/arreglo.
     */
    public Object[] ejercicio8_AgreggacionesFacturas() {
        String jpql = "SELECT COUNT(f), SUM(f.importeTotal), AVG(f.importeTotal) " +
                "FROM FacturaVenta f";

        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> resultado = query.getResultList();

        // Retornamos el primer (y único) registro
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    /**
     * Ejercicio 9: Operador de Inclusión (IN)
     * Consigna: Obtener todos los puntos de venta cuyo número coincida
     * con una lista de enteros proporcionada por parámetro (ej. 1, 2, 5).
     */
    public List<PuntoVenta> ejercicio9_PuntosPorLista(List<Integer> numeros) {
        String jpql = "SELECT p FROM PuntoVenta p WHERE p.numero IN :numeros";

        TypedQuery<PuntoVenta> query = em.createQuery(jpql, PuntoVenta.class);
        query.setParameter("numeros", numeros);
        return query.getResultList();
    }

    // ============================================================================
    // NIVEL 3: NAVEGACIÓN DE ENTIDADES, JOINs Y SUBCONSULTAS SIMPLES
    // ============================================================================

    /**
     * Ejercicio 10: Navegación Implícita por Relaciones (Path Expressions)
     * Consigna: Consultar todas las facturas de venta creadas por un usuario
     * en particular navegando por su nombre de usuario de carga (usuarioCarga.usuario).
     */
    public List<FacturaVenta> ejercicio10_FacturasPorUsuario(String nombreUsuario) {
        String jpql = "SELECT f FROM FacturaVenta f " +
                "WHERE f.usuarioCarga.usuario = :usuario";

        TypedQuery<FacturaVenta> query = em.createQuery(jpql, FacturaVenta.class);
        query.setParameter("usuario", nombreUsuario);
        return query.getResultList();
    }

    /**
     * Ejercicio 11: Cláusula INNER JOIN Explícita
     * Consigna: Obtener todos los detalles de factura (FacturaVentaDetalle)
     * que correspondan a facturas emitidas por un punto de venta determinado.
     */
    public List<FacturaVentaDetalle> ejercicio11_DetallesPorPuntoVenta(int numeroPuntoVenta) {
        String jpql = "SELECT fvd FROM FacturaVentaDetalle fvd " +
                "INNER JOIN fvd.factura f " +
                "WHERE f.puntoVenta.numero = :numero";

        TypedQuery<FacturaVentaDetalle> query = em.createQuery(jpql, FacturaVentaDetalle.class);
        query.setParameter("numero", numeroPuntoVenta);
        return query.getResultList();
    }

    /**
     * Ejercicio 12: Cláusula LEFT JOIN (Inclusión de Nulos)
     * Consigna: Listar la denominación de todos los artículos junto con
     * la denominación de su marca asociada, incluyendo también aquellos
     * artículos que no posean una marca asignada.
     */
    public List<Object[]> ejercicio12_ArticulosConMarca() {
        String jpql = "SELECT a.denominacion, m.denominacion FROM Articulo a " +
                "LEFT JOIN a.marca m";

        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        return query.getResultList();
    }

    /**
     * Ejercicio 13: Navegación Multinivel con JOINs Combinados
     * Consigna: Obtener todas las facturas de venta que contengan al menos
     * un detalle de artículo perteneciente a una marca específica.
     */
    public List<FacturaVenta> ejercicio13_FacturasPorMarca(String denominacionMarca) {
        String jpql = "SELECT DISTINCT f FROM FacturaVenta f " +
                "JOIN f.detalles fvd " +
                "JOIN fvd.listaPrecioArticulo lpa " +
                "JOIN lpa.articulo a " +
                "JOIN a.marca m " +
                "WHERE m.denominacion = :marca";

        TypedQuery<FacturaVenta> query = em.createQuery(jpql, FacturaVenta.class);
        query.setParameter("marca", denominacionMarca);
        return query.getResultList();
    }

    /**
     * Ejercicio 14: Subconsulta en Cláusula WHERE
     * Consigna: Listar las facturas de venta cuyo importeTotal sea
     * estrictamente mayor al promedio de importeTotal de todas
     * las facturas registradas.
     */
    public List<FacturaVenta> ejercicio14_FacturasAboutPromedio() {
        String jpql = "SELECT f FROM FacturaVenta f " +
                "WHERE f.importeTotal > " +
                "(SELECT AVG(f2.importeTotal) FROM FacturaVenta f2)";

        TypedQuery<FacturaVenta> query = em.createQuery(jpql, FacturaVenta.class);
        return query.getResultList();
    }

    // ============================================================================
    // NIVEL 4: AGRUPAMIENTO (GROUP BY) Y FILTROS DE GRUPO (HAVING)
    // ============================================================================

    /**
     * Ejercicio 15: Agrupamiento Básico (GROUP BY)
     * Consigna: Obtener la descripción del punto de venta, la cantidad
     * de facturas emitidas por cada uno y la suma total facturada.
     */
    public List<Object[]> ejercicio15_FacturasPorPuntoVenta() {
        String jpql = "SELECT p.descripcion, COUNT(f), SUM(f.importeTotal) " +
                "FROM FacturaVenta f " +
                "JOIN f.puntoVenta p " +
                "GROUP BY p.id, p.descripcion";

        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        return query.getResultList();
    }

    /**
     * Ejercicio 16: Agrupamiento con Condicional de Grupo (HAVING)
     * Consigna: Obtener los nombres de los usuarios de carga que hayan
     * registrado más de 5 facturas de venta en el sistema.
     */
    public List<String> ejercicio16_UsuariosConMuchosRegistros() {
        String jpql = "SELECT u.nombre FROM FacturaVenta f " +
                "JOIN f.usuarioCarga u " +
                "GROUP BY u.id, u.nombre " +
                "HAVING COUNT(f) > 5";

        TypedQuery<String> query = em.createQuery(jpql, String.class);
        return query.getResultList();
    }

    /**
     * Ejercicio 17: Agrupamiento y Agregación sobre Entidades Relacionadas
     * Consigna: Obtener la denominación de cada marca, la cantidad total
     * de unidades vendidas (SUM(cantidad)) y el subtotal acumulado,
     * agrupado por marca.
     */
    public List<Object[]> ejercicio17_VentasPorMarca() {
        String jpql = "SELECT m.denominacion, SUM(fvd.cantidad), SUM(fvd.importeSubtotal) " +
                "FROM FacturaVentaDetalle fvd " +
                "JOIN fvd.listaPrecioArticulo lpa " +
                "JOIN lpa.articulo a " +
                "JOIN a.marca m " +
                "GROUP BY m.id, m.denominacion";

        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        return query.getResultList();
    }

    // ============================================================================
    // NIVEL 5: SUBCONSULTAS CORRELACIONADAS, EXISTS, NOT EXISTS Y CASE
    // ============================================================================

    /**
     * Ejercicio 18: Subconsulta Correlacionada con EXISTS
     * Consigna: Obtener la lista de todas las marcas que tienen al menos
     * un artículo que haya sido facturado en alguna factura de venta.
     */
    public List<Marca> ejercicio18_MarcasConArticulosFacturados() {
        String jpql = "SELECT m FROM Marca m " +
                "WHERE EXISTS (" +
                "  SELECT 1 FROM Articulo a " +
                "  JOIN a.marca am " +
                "  WHERE am.id = m.id " +
                "  AND EXISTS (" +
                "    SELECT 1 FROM FacturaVentaDetalle fvd " +
                "    JOIN fvd.listaPrecioArticulo lpa " +
                "    WHERE lpa.articulo.id = a.id" +
                "  )" +
                ")";

        TypedQuery<Marca> query = em.createQuery(jpql, Marca.class);
        return query.getResultList();
    }

    /**
     * Ejercicio 19: Subconsulta Correlacionada con NOT EXISTS
     * Consigna: Obtener todos los artículos registrados en el sistema
     * que nunca han sido incluidos en ningún detalle de factura de venta.
     */
    public List<Articulo> ejercicio19_ArticulosNuncaFacturados() {
        String jpql = "SELECT a FROM Articulo a " +
                "WHERE NOT EXISTS (" +
                "  SELECT 1 FROM FacturaVentaDetalle fvd " +
                "  JOIN fvd.listaPrecioArticulo lpa " +
                "  WHERE lpa.articulo.id = a.id" +
                ")";

        TypedQuery<Articulo> query = em.createQuery(jpql, Articulo.class);
        return query.getResultList();
    }

    /**
     * Ejercicio 20: Proyección Condicional (CASE WHEN)
     * Consigna: Listar el número de factura, su importe total y una columna
     * calculada llamada "Categoría" que clasifique la factura como:
     *   - "ALTO VALOR" si el importeTotal es mayor a $50,000
     *   - "MEDIO VALOR" si el importeTotal está entre $10,000 y $50,000
     *   - "BAJO VALOR" si el importeTotal es menor a $10,000
     * Ordenar los resultados de mayor a menor importe.
     */
    public List<Object[]> ejercicio20_FacturasConCategoria() {
        String jpql = "SELECT f.numero, f.importeTotal, " +
                "CASE " +
                "  WHEN f.importeTotal > 50000 THEN 'ALTO VALOR' " +
                "  WHEN f.importeTotal BETWEEN 10000 AND 50000 THEN 'MEDIO VALOR' " +
                "  ELSE 'BAJO VALOR' " +
                "END as categoria " +
                "FROM FacturaVenta f " +
                "ORDER BY f.importeTotal DESC";

        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        return query.getResultList();
    }

    // ============================================================================
    // MÉTODOS DE UTILIDAD
    // ============================================================================

    /**
     * Método auxiliar para mostrar resultados de una consulta
     */
    public void mostrarResultados(List<?> resultados, String nombreConsulta) {
        System.out.println("\n" + nombreConsulta);
        System.out.println("Registros encontrados: " + resultados.size());

        if (resultados.isEmpty()) {
            System.out.println("  (Sin resultados)");
            return;
        }

        // Mostramos los primeros 5 registros
        resultados.stream().limit(5).forEach(r -> System.out.println("  - " + r));

        if (resultados.size() > 5) {
            System.out.println("  ... y " + (resultados.size() - 5) + " registros más");
        }
    }
}