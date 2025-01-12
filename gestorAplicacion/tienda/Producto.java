package tienda;

import java.util.ArrayList;
import compras.Reseña;

public class Producto {
    int cantidad;
    private int cantidadVendida;
    int cantidadAlerta;
    Categoria categoria;
    private int ID;
    private String nombre;
    private int precio;
    String descripcion;
    boolean retornable;
    ArrayList<Reseña> reseñas;

    public static enum Categoria {
        TECNOLOGIA("Tecnologia"),
        ASEO("Aseo"),
        COMIDA("Comida"),
        PAPELERIA("Papeleria"),
        JUGUETERIA("Jugueteria"),
        DEPORTES("Deportes");
    	
    	private final String nombre;
    	
    	Categoria(String nombre){
    		this.nombre=nombre;
    	public String getNombre() {
    		return this.nombre;
    	}
    	}
    }

    public Producto(int cantidad, int cantidadAlerta, int cantidadVendida, Categoria categoria, int ID, String nombre, int precio, 
                      String descripcion, boolean retornable, ArrayList<Reseña> reseñas) {
        this.cantidad = cantidad;
        this.cantidadVendida = 0;
        this.cantidadAlerta = cantidadAlerta;
        this.categoria = categoria;
        this.ID = ID;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.retornable = retornable;
        this.reseñas = new ArrayList<>();
    }
    
    public String toString() {
    	return "Producto: " + nombre + "\n"
    			+ "Categoria: " + categoria + "\n"
    			+ "Precio: " + precio + "\n"
    			+ "Cantidad: " + cantidad + "\n";
    }


    
    // Getters y setters
    
    // Obtiene la cantidad de productos vendidos.
    // retornaLa cantidad vendida.
     
    public int getCantidadVendida() {
        return this.cantidadVendida;
    }


    public int getID() {
        return this.ID;
    }

    public double getPrecio() {
        return this.precio;
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int nuevaCantidad){
        this.cantidad = nuevaCantidad;
    }

    public boolean isRetornable() {
        return retornable;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombres(String nombre) {
        this.nombre = nombre;
    }

    //metodos
    public int aplicardescuento(Producto producto ,double descuento) {//metodo para un posible descuento
    	int resta=(int) (producto.precio*descuento);
    	producto.precio=(int) (producto.precio-resta);
    	return resta;
    	
    	
    }
    public boolean verificarCantidadProductos(){
        if (this.cantidad <= this.cantidadAlerta){ // Se verifica si la cantidad de productos es menor o igual a la cantidad de alerta
            return true;
        }
        return false;
    }
    // Incrementa la cantidad vendida del producto.
    // cantidad La cantidad de productos vendidos.
    public void registrarVenta(int cantidad) {
        this.cantidadVendida += cantidad;
        this.cantidad -= cantidad;
    }
    void reabastecerCantidad(int cantidad) {
        this.cantidad += cantidad;
    }


     // Agrega una reseña al producto.
     // reseña La reseña a agregar.

    //public void agregarReseña(Reseña reseña) {
    //    reseñas.add(reseña);
    // }
    
    public Categoria getCategoria() {
    	return categoria;
    }
    
    public void setCategoria(Categoria categoria) {
    	this.categoria = categoria;
    }

}
