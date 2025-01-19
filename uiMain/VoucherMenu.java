package uiMain;

import java.util.Scanner;

import usuario.Comprador;

public class VoucherMenu {
    private Comprador comprador;

    public VoucherMenu(Comprador comprador){
        this.comprador = comprador;

    }
    public void display() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("===== MENÚ CUPONES =====");
            System.out.println("Usted actualmente dispone de " + comprador.getValorCupones().size() + " cupones. ¿Que desea hacer?:");
            System.out.println("1. Ver cupones disponibles");
            System.out.println("2. Eliminar cupones");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            System.out.println();

            switch (opcion) {
                case 1:
                    System.out.println("Cupones disponibles:");
                    if (comprador.getValorCupones().size() == 0){
                        System.out.println("No tiene cupones disponibles.\n");
                    }
                    else{
                        System.out.println(comprador.mostrarCupones());
                    }
                    opcion = 2; // Forzar salida del ciclo después de ver los cupones
                    break;
                case 2:
                    if (comprador.getValorCupones().size() == 0){
                        System.out.println("No tiene cupones disponibles para eliminar.\n");
                    }
                    else{
                        System.out.println("Cupones disponibles:");
                        System.out.println(comprador.mostrarCupones());
                        System.out.print("¿Cuál cupón deseas eliminar?: ");
                        int cuponEliminar = scanner.nextInt();
                        comprador.eliminarCupones(cuponEliminar);
                        comprador.cantidadCupones -= 1;
                        System.out.println("El cupón ha sido eliminado exitosamente.");
                    }
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 2);
    }
}
