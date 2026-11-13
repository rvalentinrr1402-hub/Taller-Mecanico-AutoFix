/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package taller.mecanico;

import java.util.ArrayList;

/**
 *
 * @author rvale
 */
public class TallerMecanico {
    //crear un ArrayList a nivel de los demas metodos
    //permite usarlo en todos ellos con la misma informacion que contienen
    private static ArrayList<TipoVentas> ventas = new ArrayList<>();
    /**
     * @param args the command line arguments
     */
    public static void registrarVenta(TipoVentas v) {
        //se intenta agregar una venta o servicio 
         try {
            //todas son clases hijas de TipoVentas, lo unico que cambia son la interfas (en este caso no es requerida)
            for (TipoVentas existente: ventas) {
                // equalsIgnoreCase compara textos ignorando mayúsculas/minúsculas (ej. "f001" == "F001")
                if (existente.getFolio().equalsIgnoreCase(v.getFolio())) {
                    // Si ya existe, lanzamos manualmente una excepción para interrumpir el registro
                    throw new IllegalArgumentException("Folio repetido: " + v.getFolio() + ". Registro rechazado.");
                }
            }
            // Si pasó la validación de folio único, se agrega a la colección principal
            ventas.add(v);
            System.out.println("-Venta registrada con éxito: [" + v.getFolio() + "] " + v.getTipo());
            
        } catch (IllegalArgumentException e) {
            // Captura tanto los folios repetidos de arriba como los datos inválidos lanzados desde los constructores
            // (ej. litros negativos, ejes fuera de rango, etc.), permitiendo que el programa siga con el siguiente registro.
            System.out.println("Error: " + e.getMessage());
        }
    }
    /**
     * MÉTODO AUXILIAR PARA BUSCAR Y CANCELAR UNA VENTA POR FOLIO
     * @param folioBuscado Identificador único de la venta a cancelar.
     * @param horasAnticipacion Horas faltantes para la cita al momento de pedir la cancelación.
     */
    public static void cancelarVentaPorFolio(String folioBuscado, int horasAnticipacion) {
        System.out.println("\nIntentando cancelar folio: " + folioBuscado + " con " + horasAnticipacion + "h de anticipación.");
        
        // Variable temporal para guardar la referencia del objeto si lo encontramos
        TipoVentas encontrada = null;

        // Buscar el folio en la colección
        for (TipoVentas v : ventas) {
            if (v.getFolio().equalsIgnoreCase(folioBuscado)) {
                encontrada = v;
                break; // Detiene el ciclo en cuanto lo encuentra para no recorrer de más
            }
        }

        //Validar si existió
        if (encontrada == null) {
            System.out.println("El folio " + folioBuscado + " no existe en el sistema.");
            return; // Sale del método inmediatamente
        }

        //Validar si el tipo de venta soporta cancelaciones (Interfaz Cancelable)
        if (!(encontrada instanceof Cancelable)) {
            System.out.println("El servicio de tipo " + encontrada.getTipo() + " no es cancelable.");
            return; // Sale del método inmediatamente
        }

        //Intentar ejecutar la cancelación y manejar el error propio del negocio
        try {
            // Ejecuta la lógica específica de cada clase (plazo mínimo y porcentaje de devolución)
            double reembolso = ((Cancelable) encontrada).cancelar(horasAnticipacion);
            
            // Si no lanzó excepción, significa que estuvo en tiempo: se elimina de la lista
            ventas.remove(encontrada);
            System.out.printf("Cancelación aceptada. Reembolso entregado: ", reembolso);
            
        } catch (CancelacionFueraDePlazo e) {
            // Captura nuestra excepción cuando las horas no alcanzan
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Taller mecanico AutoFix ===");

        // Datos mínimos: 8 ventas (2 de cada tipo), 2 registros con datos inválidos, 1 folio repetido y 4
        //cancelaciones: una aceptada, una fuera de plazo, una de un tipo no cancelable y una de un
        //folio inexistente.
        System.out.println("\nRegistrando Ventas...");
        registrarVenta(new ServicioAfinacion("F001", "Carlos", 4));
        registrarVenta(new ServicioAfinacion("F002", "Ana", 8));
        
        registrarVenta(new ServicioFrenos("F003", "Luis", 2));
        registrarVenta(new ServicioFrenos("F004", "Maria", 1));
        
        registrarVenta(new ServicioDiagnostico("F005", "Jorge", 3, true));
        registrarVenta(new ServicioDiagnostico("F006", "Sofia", 2, false));
        
        registrarVenta(new VentaRefaccion("F007", "Pedro", "Pastillas de freno", 450.0, 4));
        registrarVenta(new VentaRefaccion("F008", "Juan", "Bujías", 120.0, 2));

        // Datos inválidos de prueba
        try{
            registrarVenta(new ServicioAfinacion("F009", "Error Litros", 10)); // Litros fuera de rango
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        try{
        registrarVenta(new VentaRefaccion("F010", "Error Precio", "Aceite", -100.0, 1)); // Precio negativo
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        
        // Folio repetido
        registrarVenta(new ServicioAfinacion("F001", "Duplicado", 3)); //solo se verifica el folio, no el demas contenido

        //IMprimir cada vebnta usando el método toString
        System.out.println("\n=== Lista de ventas ===");
        for (TipoVentas v : ventas) {
            System.out.println(v);
            System.out.println("===================");
        }

        //reporte un solo recorrido
        System.out.println("\n=== Reporte diario ===");
        double totalDia = 0;
        int cAfinacion = 0;
        int cFrenos = 0;
        int cDiagnostico = 0;
        int cRefaccion = 0;
        double mayorCobro = -1;
        String folioMayor = "";

        for (TipoVentas v : ventas) {
            double cobro = v.calcularCobro();
            totalDia += cobro;

            if (cobro > mayorCobro) {
                mayorCobro = cobro;
                folioMayor = v.getFolio() + " (" + v.getTipo() + ")";
            }

            switch (v.getTipo()) {
                case "Afinacion": cAfinacion++; break;
                case "Frenos": cFrenos++; break;
                case "Diagnostico": cDiagnostico++; break;
                case "Refaccion": cRefaccion++; break;
            }
        }

        System.out.printf("Total recaudado en el día: ", totalDia);
        System.out.println("Cantidad de ventas por tipo:");
        System.out.println(" - Afinación: " + cAfinacion);
        System.out.println(" - Frenos: " + cFrenos);
        System.out.println(" - Diagnóstico: " + cDiagnostico);
        System.out.println(" - Refacción: " + cRefaccion);
        System.out.println("Venta con el mayor cobro: " + folioMayor + " con $" + mayorCobro);

        //(Todo cancelable con 48h de anticipación)
        System.out.println("\nReembolsos con 48 horas de anticipación");
        double totalReembolsos = 0;
        for (TipoVentas v : ventas) {
            if (v instanceof Cancelable) {
                try {
                    totalReembolsos += ((Cancelable) v).cancelar(48);
                } catch (CancelacionFueraDePlazo e) {
                    // Excepción no esperada con 48h
                }
            }
        }
        System.out.printf("\nTotal a reembolsar si se cancelara todo hoy con 48h: $" + totalReembolsos);

        //intentando canselar datos erroneos
        System.out.println("\n=== intento cancelacion ===");
        cancelarVentaPorFolio("F999", 24); // Inexistente
        cancelarVentaPorFolio("F005", 24); // No cancelable (Diagnóstico)
        cancelarVentaPorFolio("F001", 1);  // Fuera de plazo (Afinación necsita 2h)
        cancelarVentaPorFolio("F002", 5);  // Aceptada (Afinación con 5h)

        //Despues de cancelar
        System.out.println("\n=== Lista despues de cancelar ===");
        double nuevoTotalDia = 0;
        for (TipoVentas v : ventas) {
            System.out.println(v);
            System.out.println("===================-");
            nuevoTotalDia += v.calcularCobro();
        }
        System.out.printf("Nuevo total del día: "+ nuevoTotalDia);
    }
}
