package tienda;

import compras.Reseña;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Producto implements Serializable{
    private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
    int cantidad;
    private int cantidadDevuelta = 0;
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
    	}
        public String getNombre() {
    		return this.nombre;
    	}
    }

    public Producto(int cantidad, int cantidadAlerta, int cantidadVendida, int cantidadDevuelta, Categoria categoria, int ID, String nombre, int precio, 
                      String descripcion, boolean retornable, ArrayList<Reseña> reseñas) {
        this.cantidad = cantidad;
        this.cantidadVendida = 0;
        this.cantidadDevuelta = cantidadDevuelta;
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
    			
    			+ "Precio unitario: " + precio + "\n";
    			
    }
    public String toStringdif(){
        return "Producto: " + nombre + "\n"
    			+ "Categoria: " + categoria + "\n"
    			+ "Precio: " + precio + "\n"
    			+ "Cantidad Disponible: " + cantidad + "\n";
    }
    
    // Sobrescribir equals()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return Double.compare(producto.ID, ID) == 0 &&
               nombre.equals(producto.nombre);
    }

    // Sobrescribir hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    
    // Getters y setters
    
    // Obtiene la cantidad de productos vendidos.
    // retornaLa cantidad vendida.
     
    public int getCantidadVendida() {
        return this.cantidadVendida;
    }

    public int getCantidadDevuelta(){
        return this.cantidadDevuelta;
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

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public void setCantidadDevuelta(int cantidadDevuelta){
        this.cantidadDevuelta = cantidadDevuelta;
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
