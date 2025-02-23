package tienda;

import compras.HistorialCompras;
import java.io.Serializable;
import java.util.ArrayList;
import usuario.Comprador;
import usuario.Usuario;

public class Tienda implements Serializable{
	private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
   private String nombre; 
   private Inventario inventario;
   private ArrayList<Usuario> usuariosRegistrados;
	//getters y setters 
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}


	public ArrayList<Usuario> getUsuariosRegistrados() {
		return usuariosRegistrados;
	}

	public void setUsuariosRegistrados(ArrayList<Usuario> usuariosRegistrados) {
		this.usuariosRegistrados = usuariosRegistrados;
	}




    public Tienda(String nombre, Inventario inventario, boolean descuentoAleatorio, ArrayList<Usuario> usuariosRegistrados) {
        this.setNombre(nombre);
        this.setInventario(inventario);
        this.setUsuariosRegistrados(usuariosRegistrados);
    }

    public Object[][] recomendarProductos(Usuario usuario) {

		HistorialCompras historial = ((Comprador) usuario).getHistorialCompras();

        return inventario.crearCatalogo(historial);
    }

}