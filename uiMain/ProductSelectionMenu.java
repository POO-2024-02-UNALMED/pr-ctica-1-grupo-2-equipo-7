package uiMain;

import tienda.Tienda;
import usuario.Comprador;
import usuario.Vendedor;

import java.util.Scanner;

public class ProductSelectionMenu {
	
	private Comprador comprador;
	private Tienda tienda;
	private Vendedor vendedor;
	
	public ProductSelectionMenu(Comprador comprador, Vendedor vendedor, Tienda tienda) {
		this.comprador = comprador;
		this.vendedor = vendedor;
		this.tienda = tienda;
	}
	
	public void display() {
		Scanner scanner1 = new Scanner(System.in);
		Scanner scanner2 = new Scanner(System.in);
		
		String coordenada;
		String[] filas = {"1", "2", "3", "4", "5", "6"};
		String[] columnas = {"A", "a", "B", "b", "C", "c", "D", "d",
							"E", "e", "F", "f"};
		
		
		
		do {
			
			System.out.println("Ingrese las coordenadas del producto que desea \n"
					+ "Ingrese primero la fila (número) y luego la columna (letra) \n"
						+ "en la que se encuentra el producto deseado separados por un espacio");
			System.out.println("Ingrese 0 para regresar: ");
			coordenada = scanner1.next();
			
			String[] opcion = coordenada.split(" ");
			
			
			
			
		} while (coordenada != "0");

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
}
