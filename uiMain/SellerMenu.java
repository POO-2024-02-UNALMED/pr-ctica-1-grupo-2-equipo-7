package uiMain;


import fabrica.Fabrica;
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
    private Fabrica fabrica;

    public SellerMenu(Comprador comprador, Vendedor vendedor, Inventario inventario, Notificacion notificacion, Fabrica fabrica ) {
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.inventario = inventario;
        this.notificacion = notificacion;
        this.fabrica = fabrica;
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
                    System.out.println("A continucacion elija los productos que quiere crear en la fabrica para reponer en el inventario");
                    System.out.println(vendedor.crearOrdenFabricacion());
                    //System.out.println(fabrica.verificarEntregas());
                    //for (int ciclo = 0; ciclo < 20; ciclo++) { 
                       // ciclo += 1;
                        //System.out.println(fabrica.verificarEntregas());
            
                        // Simular un retraso lógico sin detener el programa
                       // for (long j = 0; j < 1_000_000_000L; j++); // Bucle para simular el tiempo
                    //}  
                

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
