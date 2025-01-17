package uiMain;

import tienda.Tienda;
import usuario.Comprador;
import usuario.Vendedor;
import tienda.Producto;

import java.util.Scanner;

public class BuyerMenu {
    private Comprador comprador;  // Instancia de Comprador
    private Vendedor vendedor;    // Instancia de Vendedor
    private Tienda tienda;

    public BuyerMenu(Comprador comprador, Vendedor vendedor, Tienda tienda) {
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.tienda = tienda;
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("===== MENÚ COMPRADOR =====");
            System.out.println("1. Gestionar Carrito/Ver Catálogo");
            System.out.println("2. Realizar Devolución");
            System.out.println("3. Realizar Compra");
            System.out.println("4. Gestionar cupones");
            System.out.println("5. Ver historial de compras");
            System.out.println("6. Ver Notificaciones");
            System.out.println("7. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1: //Menu para acceder al carrito y al catálogo
                    System.out.println();
                    new CartMenu(comprador, vendedor, tienda).display();
                    break;
                case 2: // Menú de devoluciones
                    System.out.println();
                    new ReturnMenu(comprador, vendedor).display();
                    break;
                case 3:
                System.out.println();
                    if (comprador.getCarritoCompras().getListaItems().size() == 0){ // Se verifica si el carrito de compras no esta vacío
                        System.out.println("Error. Su carrito de compras está vacío, por favor gestione su carrito de compras.");
                    }
                    else if (new BuyProcess(comprador, vendedor).verificacionCompra() == false){ // Se verifica si el usuario cuenta con saldo suficiente para realizar la compra.
                        System.out.println("Error. No cuentas con el saldo suficiente para hacer la compra.");
                    }
                    else{
                        comprador.getCarritoCompras().calcularTotal(); // Se calcula el valor total de la compra
                        new BuyProcess(comprador, vendedor).display(); // Se realiza el proceso de compra
                    }
                    break;
                case 4:
                    System.out.println();
                    new VoucherMenu(comprador).display();
                    break;
                case 5:
                    System.out.println();
                    System.out.println(comprador.mostrarHistorialCompras());
                    break;
                case 6:
                    System.out.println();
                    if (comprador.getNotificaciones().size() == 0){
                        System.out.println("Usted no tiene notificaciones..."); // Se verifica si el usuario cuenta con notificaciones.
                    }
                    else{
                        System.out.println(comprador.mostrarNotificaciones() + "\n"); // Si es así entonces se muestran las notificaciones.
                    }
                    break;
                case 7:
                    System.out.println();
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println();
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 7);
    }
}
