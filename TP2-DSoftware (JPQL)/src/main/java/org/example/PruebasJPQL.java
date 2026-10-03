package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.text.SimpleDateFormat;
import java.util.*;

public class PruebasJPQL {

    public static void main(String[] args) {
        // Inicializar EntityManager (usa el persistence-unit de persistence.xml)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FacturacionPU");
        EntityManager em = emf.createEntityManager();

        try {
            // Cargar datos de prueba (misma base H2 en memoria que usarán las consultas)
            DataSeeder.seed(em);

            // Crear instancia de ConsultasJPQL
            ConsultasJPQL consultas = new ConsultasJPQL(em);

            System.out.println("========================================");
            System.out.println("PRUEBAS DEL TP GRUPAL JPQL");
            System.out.println("========================================\n");

            // Ejecutar pruebas por nivel
            pruebasNivel1(consultas);
            pruebasNivel2(consultas);
            pruebasNivel3(consultas);
            pruebasNivel4(consultas);
            pruebasNivel5(consultas);

            System.out.println("\n========================================");
            System.out.println("PRUEBAS COMPLETADAS EXITOSAMENTE");
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("Error durante las pruebas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    // ========================================================================
    // NIVEL 1: PRUEBAS BÁSICAS
    // ========================================================================

    private static void pruebasNivel1(ConsultasJPQL consultas) {
        System.out.println("\n### NIVEL 1: CONSULTAS BÁSICAS Y PROYECCIONES ###\n");

        // Ejercicio 1
        try {
            List<FacturaVenta> facturas = consultas.ejercicio1_ListarTodasLasFacturas();
            System.out.println("✓ Ejercicio 1: Listar todas las facturas");
            System.out.println("  → " + facturas.size() + " facturas encontradas");
            if (!facturas.isEmpty()) {
                FacturaVenta f = facturas.get(0);
                System.out.println("  → Ejemplo: Factura #" + f.getNumero() +
                        ", Importe: $" + f.getImporteTotal());
            }
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 1: " + e.getMessage());
        }

        // Ejercicio 2
        try {
            List<Object[]> proyecciones = consultas.ejercicio2_ProyeccionFacturas();
            System.out.println("\n✓ Ejercicio 2: Proyección de atributos");
            System.out.println("  → " + proyecciones.size() + " registros");
            if (!proyecciones.isEmpty()) {
                Object[] fila = proyecciones.get(0);
                System.out.println("  → Ejemplo: Número=" + fila[0] + ", Fecha=" + fila[1] +
                        ", Importe=" + fila[2]);
            }
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 2: " + e.getMessage());
        }

        // Ejercicio 3
        try {
            List<Articulo> articulos = consultas.ejercicio3_ArticulosPorRubro("Electrónica");
            System.out.println("\n✓ Ejercicio 3: Artículos por rubro");
            System.out.println("  → " + articulos.size() + " artículos en rubro 'Electrónica'");
            if (!articulos.isEmpty()) {
                System.out.println("  → Ejemplo: " + articulos.get(0).getDenominacion());
            }
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 3: " + e.getMessage());
        }

        // Ejercicio 4
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date desde = sdf.parse("2024-01-01");
            Date hasta = sdf.parse("2024-12-31");

            List<FacturaVenta> facturasPeriodo = consultas.ejercicio4_FacturasPorFecha(desde, hasta);
            System.out.println("\n✓ Ejercicio 4: Facturas por rango de fechas");
            System.out.println("  → " + facturasPeriodo.size() + " facturas entre " +
                    desde + " y " + hasta);
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 4: " + e.getMessage());
        }
    }

    // ========================================================================
    // NIVEL 2: CONDICIONALES Y AGREGACIONES
    // ========================================================================

    private static void pruebasNivel2(ConsultasJPQL consultas) {
        System.out.println("\n### NIVEL 2: CONDICIONALES Y AGREGACIONES ###\n");

        // Ejercicio 5
        try {
            List<FacturaVenta> facturasEmitidas = consultas.ejercicio5_FacturasEmitidas();
            System.out.println("✓ Ejercicio 5: Facturas emitidas (estado='EMITIDA', importe>10000, no anuladas)");
            System.out.println("  → " + facturasEmitidas.size() + " facturas");
            double importePromedio = facturasEmitidas.stream()
                    .mapToDouble(FacturaVenta::getImporteTotal)
                    .average()
                    .orElse(0.0);
            System.out.println("  → Importe promedio: $" + String.format("%.2f", importePromedio));
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 5: " + e.getMessage());
        }

        // Ejercicio 6
        try {
            List<Cliente> clientes = consultas.ejercicio6_ClientesPorPatron("electro");
            System.out.println("\n✓ Ejercicio 6: Búsqueda por patrón de texto");
            System.out.println("  → " + clientes.size() + " clientes encontrados");
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 6: " + e.getMessage());
        }

        // Ejercicio 7
        try {
            List<String> estados = consultas.ejercicio7_EstadosDistintos();
            System.out.println("\n✓ Ejercicio 7: Estados distintos");
            System.out.println("  → " + estados.size() + " estados únicos");
            System.out.println("  → Estados: " + String.join(", ", estados));
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 7: " + e.getMessage());
        }

        // Ejercicio 8
        try {
            Object[] resultado = consultas.ejercicio8_AgreggacionesFacturas();
            System.out.println("\n✓ Ejercicio 8: Agregaciones (COUNT, SUM, AVG)");
            if (resultado != null) {
                Long cantidad = (Long) resultado[0];
                Double suma = (Double) resultado[1];
                Double promedio = (Double) resultado[2];
                System.out.println("  → Cantidad: " + cantidad);
                System.out.println("  → Suma: $" + String.format("%.2f", suma != null ? suma : 0));
                System.out.println("  → Promedio: $" + String.format("%.2f", promedio != null ? promedio : 0));
            }
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 8: " + e.getMessage());
        }

        // Ejercicio 9
        try {
            List<Integer> numeros = Arrays.asList(1, 2, 5);
            List<PuntoVenta> puntos = consultas.ejercicio9_PuntosPorLista(numeros);
            System.out.println("\n✓ Ejercicio 9: Puntos de venta por lista");
            System.out.println("  → Buscados: " + numeros);
            System.out.println("  → " + puntos.size() + " puntos encontrados");
            puntos.forEach(p -> System.out.println("    - Punto #" + p.getNumero() + ": " + p.getDescripcion()));
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 9: " + e.getMessage());
        }
    }

    // ========================================================================
    // NIVEL 3: JOINS Y SUBCONSULTAS
    // ========================================================================

    private static void pruebasNivel3(ConsultasJPQL consultas) {
        System.out.println("\n### NIVEL 3: NAVEGACIÓN, JOINs Y SUBCONSULTAS ###\n");

        // Ejercicio 10
        try {
            List<FacturaVenta> facturas = consultas.ejercicio10_FacturasPorUsuario("admin");
            System.out.println("✓ Ejercicio 10: Facturas por usuario");
            System.out.println("  → " + facturas.size() + " facturas del usuario 'admin'");
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 10: " + e.getMessage());
        }

        // Ejercicio 11
        try {
            List<FacturaVentaDetalle> detalles = consultas.ejercicio11_DetallesPorPuntoVenta(1);
            System.out.println("\n✓ Ejercicio 11: Detalles por punto de venta");
            System.out.println("  → " + detalles.size() + " detalles en punto de venta #1");
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 11: " + e.getMessage());
        }

        // Ejercicio 12
        try {
            List<Object[]> articulosMarcas = consultas.ejercicio12_ArticulosConMarca();
            System.out.println("\n✓ Ejercicio 12: Artículos con marca (LEFT JOIN)");
            System.out.println("  → " + articulosMarcas.size() + " artículos");
            int sinMarca = 0;
            for (Object[] fila : articulosMarcas) {
                if (fila[1] == null) sinMarca++;
            }
            System.out.println("  → " + sinMarca + " artículos sin marca asignada");
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 12: " + e.getMessage());
        }

        // Ejercicio 13
        try {
            List<FacturaVenta> facturas = consultas.ejercicio13_FacturasPorMarca("DELL");
            System.out.println("\n✓ Ejercicio 13: Facturas por marca");
            System.out.println("  → " + facturas.size() + " facturas con artículos marca 'DELL'");
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 13: " + e.getMessage());
        }

        // Ejercicio 14
        try {
            List<FacturaVenta> facturas = consultas.ejercicio14_FacturasAboutPromedio();
            System.out.println("\n✓ Ejercicio 14: Facturas sobre el promedio");
            System.out.println("  → " + facturas.size() + " facturas con importe > promedio");
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 14: " + e.getMessage());
        }
    }

    // ========================================================================
    // NIVEL 4: GROUP BY Y HAVING
    // ========================================================================

    private static void pruebasNivel4(ConsultasJPQL consultas) {
        System.out.println("\n### NIVEL 4: AGRUPAMIENTO (GROUP BY) Y HAVING ###\n");

        // Ejercicio 15
        try {
            List<Object[]> ventasPorPunto = consultas.ejercicio15_FacturasPorPuntoVenta();
            System.out.println("✓ Ejercicio 15: Facturas por punto de venta");
            System.out.println("  → " + ventasPorPunto.size() + " puntos de venta");
            System.out.println("\n  Detalle:");
            for (Object[] fila : ventasPorPunto) {
                System.out.println("    - " + fila[0] + ": " + fila[1] + " facturas, $" +
                        String.format("%.2f", fila[2]));
            }
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 15: " + e.getMessage());
        }

        // Ejercicio 16
        try {
            List<String> usuarios = consultas.ejercicio16_UsuariosConMuchosRegistros();
            System.out.println("\n✓ Ejercicio 16: Usuarios con más de 5 facturas");
            System.out.println("  → " + usuarios.size() + " usuarios");
            usuarios.forEach(u -> System.out.println("    - " + u));
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 16: " + e.getMessage());
        }

        // Ejercicio 17
        try {
            List<Object[]> ventasPorMarca = consultas.ejercicio17_VentasPorMarca();
            System.out.println("\n✓ Ejercicio 17: Ventas por marca");
            System.out.println("  → " + ventasPorMarca.size() + " marcas");
            System.out.println("\n  Detalle (primeras 5):");
            ventasPorMarca.stream().limit(5).forEach(fila -> {
                System.out.println("    - " + fila[0] + ": " + fila[1] + " unidades, $" +
                        String.format("%.2f", fila[2]));
            });
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 17: " + e.getMessage());
        }
    }

    // ========================================================================
    // NIVEL 5: SUBCONSULTAS CORRELACIONADAS, EXISTS, CASE
    // ========================================================================

    private static void pruebasNivel5(ConsultasJPQL consultas) {
        System.out.println("\n### NIVEL 5: EXISTS, NOT EXISTS, CASE WHEN ###\n");

        // Ejercicio 18
        try {
            List<Marca> marcas = consultas.ejercicio18_MarcasConArticulosFacturados();
            System.out.println("✓ Ejercicio 18: Marcas con artículos facturados");
            System.out.println("  → " + marcas.size() + " marcas");
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 18: " + e.getMessage());
        }

        // Ejercicio 19
        try {
            List<Articulo> articulos = consultas.ejercicio19_ArticulosNuncaFacturados();
            System.out.println("\n✓ Ejercicio 19: Artículos nunca facturados");
            System.out.println("  → " + articulos.size() + " artículos sin movimiento");
            articulos.stream().limit(3).forEach(a -> {
                System.out.println("    - " + a.getDenominacion());
            });
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 19: " + e.getMessage());
        }

        // Ejercicio 20
        try {
            List<Object[]> facturasCategoria = consultas.ejercicio20_FacturasConCategoria();
            System.out.println("\n✓ Ejercicio 20: Facturas con categoría (CASE WHEN)");
            System.out.println("  → " + facturasCategoria.size() + " facturas clasificadas");

            Map<String, Integer> resumen = new HashMap<>();
            for (Object[] fila : facturasCategoria) {
                String categoria = (String) fila[2];
                resumen.put(categoria, resumen.getOrDefault(categoria, 0) + 1);
            }

            System.out.println("\n  Resumen por categoría:");
            resumen.forEach((cat, cant) -> System.out.println("    - " + cat + ": " + cant));

            System.out.println("\n  Primeras 3 facturas:");
            facturasCategoria.stream().limit(3).forEach(fila -> {
                System.out.println("    - Factura #" + fila[0] + ": $" +
                        String.format("%.2f", fila[1]) + " (" + fila[2] + ")");
            });
        } catch (Exception e) {
            System.err.println("✗ Ejercicio 20: " + e.getMessage());
        }
    }

    // ========================================================================
    // MÉTODO AUXILIAR: MOSTRAR RESULTADOS FORMATEADOS
    // ========================================================================

    private static void mostrarResultados(List<?> resultados, String titulo) {
        System.out.println("\n" + titulo);
        System.out.println("Registros: " + resultados.size());

        if (resultados.isEmpty()) {
            System.out.println("  (Sin resultados)");
            return;
        }

        int limite = Math.min(5, resultados.size());
        for (int i = 0; i < limite; i++) {
            System.out.println("  " + (i + 1) + ". " + resultados.get(i));
        }

        if (resultados.size() > 5) {
            System.out.println("  ... y " + (resultados.size() - 5) + " más");
        }
    }
}