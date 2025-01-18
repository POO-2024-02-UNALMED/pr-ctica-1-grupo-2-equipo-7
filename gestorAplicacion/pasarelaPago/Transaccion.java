package pasarelaPago;

import java.io.Serializable;
import usuario.Comprador;
import usuario.Usuario;
import usuario.Vendedor;

public class Transaccion implements Serializable{
	private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
	private Usuario usuarioRemitente;
	private Usuario usuarioReceptor;
	private String tipoTransaccion;
	private boolean estadoCompra;

	public Transaccion(Usuario usuarioRemitente, Usuario usuarioReceptor, String tipoTransaccion){
		this.usuarioRemitente = usuarioRemitente;
		this.usuarioReceptor = usuarioReceptor;
		this.tipoTransaccion = tipoTransaccion;
	}

	//getters y setters
	
	public String getTipoTransaccion() {
		return this.tipoTransaccion;
	}

	public boolean getEstadoCompra() {
		return this.estadoCompra;
	}
	public void setEstadoCompra(boolean value) {
		this.estadoCompra = value;
	}

    	public Usuario getUsuarioRemitente() {
      		return this.usuarioRemitente;
    	}

	//metodos
	
	public boolean verificarSaldo(){
		if (usuarioRemitente instanceof Comprador) {
			Comprador comprador = (Comprador) usuarioRemitente; // Hacemos el casting
			return comprador.getCuentaBancaria().getSaldo() >= comprador.getCarritoCompras().getPrecioTotal();
		}else if(usuarioRemitente instanceof Vendedor){
			return true;
		}
		return false;
	}

	public void ejecutarTransaccion(double cantidadTransferir) { 
		if (estadoCompra) {
			usuarioRemitente.getCuentaBancaria().transferirDinero(usuarioReceptor, cantidadTransferir); 
		}
	}

	public void generarFactura(){
		if (usuarioRemitente instanceof Comprador){ // Se verifica si el usuario es de tipo Comprador para poder generar una factura.
			Comprador comprador = (Comprador) usuarioRemitente; // Se hace el casting a tipo Comprador.
			Factura factura = new Factura(comprador.getCarritoCompras(), comprador.getHistorialCompras().getFacturas().size() + 1, this);
			comprador.getHistorialCompras().agregarFactura(factura);
		}
	}

	public String obtenerDetallesTransaccion() { 
		return "Transacción: \n" 
		+ "Tipo de Transacción: " + this.tipoTransaccion + "\n" 
		+ "Estado de Compra: " + (this.estadoCompra ? "Completada" : "Pendiente") + "\n" 
		+ "Usuario Remitente: " + this.usuarioRemitente.getNombre() + "\n" 
		+ "Usuario Receptor: " + this.usuarioReceptor.getNombre(); }

	
}
