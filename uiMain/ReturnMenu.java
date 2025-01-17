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
        System.out.print("Ingrese el ID de la factura donde se encuentra el producto a devolver: ");
        int idFactura = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese el ID del producto a devolver: ");
        int idProducto = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese la cantidad a devolver: ");
        int cantidadRetornar = Integer.parseInt(scanner.nextLine());

        String resultado = comprador.devolverProducto(idFactura, idProducto, cantidadRetornar, vendedor);

        switch (resultado) {
            case "FacturaInvalida":
                System.out.println("La factura ingresada no fue encontrada.");
                break;
            case "ProductoInvalido":
                System.out.println("El producto ingresado no fue encontrado en la factura, no es retornable ó la cantidad a devolver no es valida.");
                break;
            default:
            System.out.println("La devolución se ha procesado correctamente, en sus notificaciones encontrará más información.");
        }
    }

}
