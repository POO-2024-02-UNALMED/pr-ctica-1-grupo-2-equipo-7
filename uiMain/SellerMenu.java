package uiMain;


import java.util.Scanner;
import tienda.Inventario;
import usuario.Notificacion;
import usuario.Vendedor;

public class SellerMenu {
    private Vendedor vendedor;    // Instancia de Vendedor
    private Inventario inventario;

    public SellerMenu( Vendedor vendedor, Inventario inventario) {
        this.vendedor = vendedor;
        this.inventario = inventario;
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println();
            System.out.println("===== MENÚ VENDEDOR =====");
            System.out.println("1. Generar reporte de ventas");
            System.out.println("2. Consultar cuenta bancaria");
            System.out.println("3. Ver notificaciones");
            System.out.println("4. Volver al Menú Principal\n");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea pendiente

            switch (opcion) {
                case 1:
                    System.out.println();
                    System.out.println(inventario.generarReporte());
                    System.out.println("A continucacion elija los productos que quiere crear en la fabrica para reponer en el inventario, seleccione un maximo de 50 unidades por orden");
                    System.out.println(vendedor.crearOrdenFabricacion());
                    String mensajeFabrica = "Se han entregado los productos."; 
                    String asuntoVendedor = "Orden De produccion";
                    vendedor.recibirNotificacion(new Notificacion(mensajeFabrica, asuntoVendedor, vendedor)); 
                break;
                case 2:
                    System.out.println();
                    System.out.println("========= CUENTA BANCARIA =========");
                    System.out.println(vendedor.consultarCuentaBancaria()); 
                    break;
                case 3:
                System.out.println();
                if (vendedor.getNotificaciones().size() == 0){
                    System.out.println("Usted no tiene notificaciones..."); // Se verifica si el usuario cuenta con notificaciones.
                }
                else{
                    System.out.println();
                    System.out.println(vendedor.mostrarNotificaciones() + "\n"); // Si es así entonces se muestran las notificaciones.
                }
                    break;
                case 4:
                    System.out.println();
                    System.out.println("Volviendo al Menú     Principal...");
                default:
                    System.out.println();
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
                    break;
        } while (opcion != 4);
    }
}
