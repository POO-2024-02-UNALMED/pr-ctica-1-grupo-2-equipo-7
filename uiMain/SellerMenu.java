package uiMain;

import java.util.Scanner;
import tienda.Inventario;
import usuario.Comprador;
import usuario.Notificacion;
import usuario.Vendedor;

public class SellerMenu {
    private Comprador comprador;  // Instancia de Comprador
    private Vendedor vendedor;    // Instancia de Vendedor
    private Inventario inventario;
    private Notificacion notificacion;

    public SellerMenu(Comprador comprador, Vendedor vendedor, Inventario inventario, Notificacion notificacion) {
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.inventario = inventario;
        this.notificacion = notificacion;
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("===== MENÚ VENDEDOR =====");
            System.out.println("1. Generar reporte de ventas");
            System.out.println("2. Ver notificaciones");
            System.out.println("3. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea pendiente

            switch (opcion) {
                case 1:
                    System.out.println(inventario.generarReporte());
                    break;
                case 2:
                if (vendedor.getNotificaciones().size() == 0){
                    System.out.println("Usted no tiene notificaciones..."); // Se verifica si el usuario cuenta con notificaciones.
                }
                else{
                    System.out.println(vendedor.mostrarNotificaciones() + "\n"); // Si es así entonces se muestran las notificaciones.
                }
                    break;
                case 3:
                    System.out.println("Volviendo al Menú     Principal...");
                default:
                            System.out.println("Opción no válida. Intente nuevamente.");
                    }
                    break;
        } while (opcion != 3);
    }
}
