package uiMain;

import java.util.Scanner;
import tienda.Inventario;
import tienda.Tienda;
import usuario.Comprador;
import usuario.Notificacion;
import usuario.Vendedor;

public class MainMenu {
    private Comprador comprador;
    private Vendedor vendedor;
    private Tienda tienda;
    private Inventario inventario;
    private Notificacion notificacion;

    public MainMenu(Comprador comprador, Vendedor vendedor, Tienda tienda, Inventario inventario, Notificacion notificacion){
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.tienda = tienda;
        this.inventario = inventario;
        this.notificacion = notificacion;
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("===== MENÚ PRINCIPAL =====");
            System.out.println("1. Menú Comprador");
            System.out.println("2. Menú Vendedor");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    new BuyerMenu(comprador, vendedor, tienda).display();
                    break;
                case 2:
                    new SellerMenu(comprador, vendedor, inventario, notificacion).display();
                    break;
                case 3:
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 3);
    }
}
