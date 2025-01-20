package uiMain;

import baseDatos.Deserializador;
import baseDatos.Serializador;
import fabrica.Fabrica;
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
    private Fabrica fabrica;

    public MainMenu(Comprador comprador, Vendedor vendedor, Tienda tienda, Inventario inventario, Notificacion notificacion){
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.tienda = tienda;
        this.inventario = inventario;
        this.notificacion = notificacion;
    }

    public MainMenu(){
      Deserializador.deserealizar(this);
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println();
            System.out.println("===== MENÚ PRINCIPAL =====");
            System.out.println("1. Menú Comprador");
            System.out.println("2. Menú Vendedor");
            System.out.println("3. Salir\n");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    new BuyerMenu(comprador, vendedor, tienda).display();
                    break;
                case 2:
                    new SellerMenu(comprador, vendedor, inventario, notificacion, fabrica).display();
                    break;
                case 3:
                    Serializador.serializar(this);
                    Serializador.serializarInventarioStatic();
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    System.out.println();
                    break;
                default:
                    System.out.println();
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 3);
    }

    public Comprador getComprador() {
      return this.comprador;
    }
    public void setComprador(Comprador value) {
      this.comprador = value;
    }

    public Vendedor getVendedor() {
      return this.vendedor;
    }
    public void setVendedor(Vendedor value) {
      this.vendedor = value;
    }

    public Tienda getTienda() {
      return this.tienda;
    }
    public void setTienda(Tienda value) {
      this.tienda = value;
    }

    public Inventario getInventario() {
      return this.inventario;
    }
    public void setInventario(Inventario value) {
      this.inventario = value;
    }

    public Notificacion getNotificacion() {
      return this.notificacion;
    }
    public void setNotificacion(Notificacion value) {
      this.notificacion = value;
    }
}
