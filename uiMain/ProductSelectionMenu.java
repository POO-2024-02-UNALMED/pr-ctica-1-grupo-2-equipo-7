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
    public boolean display(){

        //Si retorna true, se devuelve al menú de selección de productos 
		//Si no, vuelve al menú del carrito

        Scanner scanner = new Scanner(System.in);
        Scanner cantidad=new Scanner(System.in);
        int opcion;


        do{

        System.out.println("¿Qué desea hacer?");
						System.out.println("1. Agregar al carrito");
						System.out.println("2. Ver información del producto");
						System.out.println("3. Regresar/Seleccionar otro producto");
						System.out.println("Seleccione una opción: ");
					    opcion = scanner.nextInt();

						switch (opcion) {
						case 1:
                            
                        System.out.println("Ingresa la cantidad a llevar: ");
                        llevar=cantidad.nextLine();
                        int numerico=Integer.parseInt(llevar);
                        if (numerico == 1 || numerico == 2 || numerico == 3 || numerico == 4 || numerico == 5){
                            
                        }else{
                            llevar="1";
                            System.out.println("Cantidad inválida , se te asignará una por default que es 1");
                        }
                           
							String o = this.comprador.getCarritoCompras().añadirProducto(productoSeleccionado, Integer.parseInt(llevar));
                            System.out.println(o);
							return false;
						case 2:
							System.out.println(productoSeleccionado);
							return true;
						case 3:
                            return true; 
						default:
							System.out.println("Opción inválida, intente de nuevo");
							continue;
        }
       
    } while(opcion != 3);

    return false;


    }

    public boolean display(HistorialCompras historial){

        //Si retorna true, se devuelve al menú de selección de productos 
		//Si no, vuelve al menú del carrito

        Scanner scanner = new Scanner(System.in);
        Scanner cantidad=new Scanner(System.in);
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

                System.out.println("Ingresa la cantidad a llevar: ");
                llevar=cantidad.nextLine();
                int numerico=Integer.parseInt(llevar);
                if (numerico == 1 || numerico == 2 || numerico == 3 || numerico == 4 || numerico == 5){
                    
                }else{
                    llevar="1";
                    System.out.println("Cantidad inválida , se te asignará una por default que es 1");
                }
                    this.comprador.getCarritoCompras().añadirProducto(productoSeleccionado, Integer.parseInt(llevar));
                    System.out.println("Producto añadido correctamente");
                    return false;
                case "2":
                    System.out.println(productoSeleccionado);
                    return true;
                case "3":
                    return true;
                case "4":
                    //Llamada a la lógica para calificar recomendaciones
                    return false;
                default:
                    System.out.println("Opción inválida, intente de nuevo");
                    continue;
                }
            } else {
                
                display();
                return false;
                
            }

        } while(opcion != "3");

        return false;


    }

}
