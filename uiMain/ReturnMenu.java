package uiMain;

import java.util.Scanner;
import usuario.Comprador;
import usuario.Vendedor;

public class ReturnMenu {
    private Comprador comprador;  // Instancia de Comprador
    private Vendedor vendedor;    // Instancia de Vendedor

    public ReturnMenu(Comprador comprador, Vendedor vendedor) {
        this.comprador = comprador;
        this.vendedor = vendedor;
    }


    public void display() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== MENÚ DE DEVOLUCIONES =====");

        System.out.print("¿Conoce el ID de la factura y del producto a devolver? (S/N): ");
        String conoceIdFactura = scanner.nextLine().trim().toUpperCase();

        System.out.println();

        if (!conoceIdFactura.equals("S")) {
            System.out.println("Por favor, consiga la información necesaria en el numeral 6 del MENÚ COMPRADOR.\n");
            return; 
        }

        System.out.print("Ingrese el ID de la factura donde se encuentra el producto: ");
        int idFactura = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese el ID del producto a devolver: ");
        int idProducto = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese la cantidad a devolver: ");
        int cantidadRetornar = Integer.parseInt(scanner.nextLine());
        System.out.println();

        String resultado = comprador.devolverProducto(idFactura, idProducto, cantidadRetornar, vendedor);

        switch (resultado) {
            case "FacturaInvalida":
                System.out.println("La factura ingresada no fue encontrada.\n");
                break;
            case "ProductoInvalido":
                System.out.println("El producto ingresado no fue encontrado en la factura, no es retornable ó la cantidad a devolver no es valida.\n");
                break;
            default:
            System.out.println("La devolución se ha procesado correctamente, en sus notificaciones encontrará más información.\n");
        }
    }

}
