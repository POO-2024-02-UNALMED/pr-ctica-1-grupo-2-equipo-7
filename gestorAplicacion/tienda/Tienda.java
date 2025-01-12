package gestorAplicacion.tienda;

import java.util.ArrayList;
import gestorAplicacion.usuario.Usuario;

public class Tienda {

   private String nombre; 
   private Inventario inventario;
   private boolean descuentoAleatorio;
   private ArrayList<Usuario> usuariosRegistrados;

    public Tienda(String nombre, Inventario inventario, boolean descuentoAleatorio, ArrayList<Usuario> usuariosRegistrados) {
        this.setNombre(nombre);
        this.setInventario(inventario);
        this.setDescuentoAleatorio(descuentoAleatorio);
        this.setUsuariosRegistrados(usuariosRegistrados);
    }

    public void recomendarProductos(Usuario usuario) {
        // Lógica para recomendar un producto - retorna un array de productos
    }

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

	public boolean hasDescuentoAleatorio() {
		return descuentoAleatorio;
	}

	public void setDescuentoAleatorio(boolean descuentoAleatorio) {
		this.descuentoAleatorio = descuentoAleatorio;
	}

	public ArrayList<Usuario> getUsuariosRegistrados() {
		return usuariosRegistrados;
	}

	public void setUsuariosRegistrados(ArrayList<Usuario> usuariosRegistrados) {
		this.usuariosRegistrados = usuariosRegistrados;
	}


}
