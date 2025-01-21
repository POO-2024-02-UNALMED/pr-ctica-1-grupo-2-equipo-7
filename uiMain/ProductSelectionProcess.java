package uiMain;

import tienda.Tienda;
import tienda.Producto;
import usuario.Comprador;
import usuario.Vendedor;

import java.util.Arrays;
import java.util.Scanner;



public class ProductSelectionProcess {
	
	private Comprador comprador;
	private Tienda tienda;
	private Vendedor vendedor;
	private Object[][] catalogo;
	private boolean recomendaciones;
	
	public ProductSelectionProcess(Comprador comprador, Vendedor vendedor, Tienda tienda, Object[][] catalogo,
									boolean recomendaciones) {
		this.comprador = comprador;
		this.vendedor = vendedor;
		this.tienda = tienda;
		this.catalogo = catalogo;
		this.recomendaciones = recomendaciones;
	}
	
	public void display() {
		Scanner scanner1 = new Scanner(System.in);
		
		
		String llevar="1";
		String opcion;
		String fila;
		String columna;
		String[] filas = {"1", "2", "3", "4", "5", "6"};
		String[] columnas = {"A", "B", "C", "D", "E", "F"};
		
		
		
		do {
			
			System.out.println(" \nIngrese las coordenadas del producto que desea \n \n"
								+ "Ingrese primero la fila (número) y luego la columna (letra) \n"
								+ "en la que se encuentra el producto deseado \n");

			System.out.println("Ingrese la fila para continuar o 0 para salir: ");
			opcion = scanner1.nextLine();
			
			

			if (opcion.equals("0")) {
				break;
			}

			if (Arrays.asList(filas).contains(opcion)){
				fila = opcion;


				System.out.println("Ingrese la columna (en mayúscula) para continuar o 0 para salir: ");
				opcion = scanner1.nextLine();
				

				if (opcion.equals("0")) {
					break;
				}

				if (Arrays.asList(columnas).contains(opcion)){
					columna = opcion;
					Producto productoSeleccionado = (Producto) catalogo[Integer.parseInt(fila)][Arrays.asList(columnas).indexOf(columna)+2];

					

					System.out.println("Producto seleccionado: " + productoSeleccionado.getNombre() + "\n");
					


					if (recomendaciones == true){
						
						//A partir de la segunda compra ya se tiene acceso al historial, por lo que
						//Se llama al método sobrecargado de display de ProductSelectionMenu
						//Que permite calificar los productos recomendados

						new ProductSelectionMenu(comprador, tienda, catalogo, productoSeleccionado,
												fila, columna,llevar).display(comprador.getHistorialCompras());
						break;

					} else {

						//En la primera compra no hay historial, por lo que no se pueden hacer recomendaciones

						//Este display retorna un valor booleano dependiendo de la opción que se escoja,
						//Esto con el fin de saber si se debe volver al menú de selección de productos o
						//Al menú del carrito directamente
						boolean retorno = new ProductSelectionMenu(comprador, tienda, catalogo, productoSeleccionado,
												fila, columna,llevar).display();

						if (retorno == false){
							break;
						} else {
							continue;
						}
						
					}

		
						} else {
							System.out.println("Columna inválida, intente de nuevo");
							continue;
					}

				} else {
					System.out.println("Fila inválida, intente de nuevo");
					continue;
				}

			} while (opcion != "0");

	}
	
	
	
	
	//getters y setters
	public Comprador getComprador() {
		return comprador;
	}
	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}
	public Tienda getTienda() {
		return tienda;
	}
	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public Object[][] getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(Object[][] catalogo) {
		this.catalogo = catalogo;
	}
}
