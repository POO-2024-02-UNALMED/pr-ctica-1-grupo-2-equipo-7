package uiMain;

import tienda.Tienda;
import tienda.Producto;
import usuario.Comprador;
import compras.HistorialCompras;

import java.util.Scanner;



public class ProductSelectionMenu {
    private String llevar;
    private Comprador comprador;
	private Tienda tienda;
	private Object[][] catalogo;
    private Producto productoSeleccionado;
    private String fila;
    private String columna;

    public ProductSelectionMenu(Comprador comprador, Tienda tienda, Object[][] catalogo,    
                                Producto productoSeleccionado, String fila, String columna,String llevar) {
        this.comprador = comprador;
        this.tienda = tienda;
        this.catalogo = catalogo;
        this.productoSeleccionado = productoSeleccionado;
        this.fila = fila;
        this.columna = columna;
        this.llevar=llevar;

    }
    public ProductSelectionMenu(Comprador comprador, Tienda tienda, Object[][] catalogo,    
        Producto productoSeleccionado, String fila, String columna) {
        this.comprador = comprador;
        this.tienda = tienda;
        this.catalogo = catalogo;
        this.productoSeleccionado = productoSeleccionado;
        this.fila = fila;
        this.columna = columna;
        

}
    public void display(){

        Scanner scanner = new Scanner(System.in);
        int opcion;


        do{

        System.out.println("¿Qué desea hacer?");
						System.out.println("1. Agregar al carrito");
						System.out.println("2. Ver información del producto");
						System.out.println("3. Seleccionar otro producto");
                        System.out.println("4. Regresar");
						System.out.println("Seleccione una opción: ");
					    opcion = scanner.nextInt();

						switch (opcion) {
						case 1:
                           
							this.comprador.getCarritoCompras().añadirProducto(productoSeleccionado, Integer.parseInt(llevar));
                            System.out.println("Producto añadido correctamente");
							break;
						case 2:
							System.out.println(productoSeleccionado);
							break;
						case 3:
							break;
						default:
							System.out.println("Opción inválida, intente de nuevo");
							continue;
        }
        System.out.println();
    } while(opcion != 3 && opcion != 4 && opcion !=1 && opcion!=2);


    }

    public void display(HistorialCompras historial){

        Scanner scanner = new Scanner(System.in);
        String opcion;


        do{

            if (fila == "1" || fila == "2" || fila == "3"){
                System.out.println("¿Qué desea hacer?");
                System.out.println("1. Agregar al carrito");
                System.out.println("2. Ver información del producto");
                System.out.println("3. Seleccionar otro producto");
                System.out.println("4. Calificar recomendación");
                System.out.println("Seleccione una opción: ");
                opcion = scanner.nextLine();

                switch (opcion) {
                case "1":
                    this.comprador.getCarritoCompras().añadirProducto(productoSeleccionado, Integer.parseInt(llevar));
                    System.out.println("Producto añadido correctamente");
                    break;
                case "2":
                    System.out.println(productoSeleccionado);
                    break;
                case "3":
                    continue;
                case "4":
                    //Llamada a la lógica para calificar recomendaciones
                    break;
                default:
                    System.out.println("Opción inválida, intente de nuevo");
                    continue;
                }
            } else {
                
                display();
                break;
                
            }

        } while(opcion != "3");


    }

}
