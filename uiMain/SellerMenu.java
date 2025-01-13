package uiMain;

import java.util.Scanner;
import usuario.Comprador;
import usuario.Vendedor;

public class SellerMenu {
    private Comprador comprador;  // Instancia de Comprador
    private Vendedor vendedor;    // Instancia de Vendedor

    public SellerMenu(Comprador comprador, Vendedor vendedor) {
        this.comprador = comprador;
        this.vendedor = vendedor;
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("===== MENÚ VENDEDOR =====");
            System.out.println("1. Ver mis productos");
            System.out.println("2. Agregar un nuevo producto");
            System.out.println("3. Actualizar un producto");
            System.out.println("4. Ver pedidos recibidos");
            System.out.println("5. Ver notificaciones");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea pendiente

            switch (opcion) {
                case 1:
                    // metodo
                    break;
                case 2:
                    // metodo
                    break;
                case 3:
                    // metodo
                    break;
                case 4:
                    // metodo
                    break;
                case 5:
                if (vendedor.getNotificaciones().size() == 0){
                    System.out.println("No tiene notificaciones.\n");
                }
                else{
                    System.out.println("Notificaciones:");
                    System.out.println(vendedor.mostrarNotificaciones());
                    System.out.println();
                }
                case 6:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 6);
    }
}
