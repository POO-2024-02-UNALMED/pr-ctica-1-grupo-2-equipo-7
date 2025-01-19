package usuario;
import java.io.Serializable;
import java.util.ArrayList;
import pasarelaPago.CuentaBancaria;
import pasarelaPago.Transaccion;

public class Usuario implements Serializable{
  private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
	private String membresia;
	private int vacescomprado;
	private int puntos;
    protected String nombre;
    protected String correo;
    protected CuentaBancaria cuentaBancaria;
    protected ArrayList<Notificacion> notificaciones = new ArrayList<>();

    public Usuario(String nombre ,CuentaBancaria cuenta){
        this.nombre = nombre;
        this.cuentaBancaria = cuenta;
    }

    // getters y setters

    public String getNombre() {
      return this.nombre;
    }
	
    public void setNombre(String value) {
      this.nombre = value;
    }

    public String getCorreo() {
      return this.correo;
    }
    public void setCorreo(String value) {
      this.correo = value;
    }

    public CuentaBancaria getCuentaBancaria() {
      return this.cuentaBancaria;
    }
    public void setCuentaBancaria(CuentaBancaria value) {
      this.cuentaBancaria = value;
    }

    public ArrayList<Notificacion> getNotificaciones() {
      return this.notificaciones;
    }
    public String getMembresia() {
		return membresia;
	}

	public void setMembresia(String membresia) {
		this.membresia = membresia;
	}
	public int getVacescomprado() {
		return vacescomprado;
	}

	public void setVacescomprado(int vacescomprado) {
		this.vacescomprado = vacescomprado;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

    // metodos
	
    public void pago(Usuario usuarioRemitente, Usuario usuarioReceptor, double cantidadTransferir, String tipoTransaccion){
        Transaccion transaccion = new Transaccion(usuarioRemitente, usuarioReceptor, tipoTransaccion);
        boolean verificacionSaldo = transaccion.verificarSaldo();
        transaccion.setEstadoCompra(verificacionSaldo);
        
        if (transaccion.getEstadoCompra() == true && tipoTransaccion.equalsIgnoreCase("devolucion")){
            transaccion.ejecutarTransaccion(cantidadTransferir);
        }
        else if (transaccion.getEstadoCompra() == true && tipoTransaccion.equalsIgnoreCase("compra")){
          transaccion.generarFactura();
          transaccion.ejecutarTransaccion(cantidadTransferir);
        }
    }
	
    public String mostrarNotificaciones(){
        int contador = 1;
        String notificaciones = "";
        for (Notificacion notificacion : this.notificaciones){
                notificaciones += "\n" + contador + ". " + notificacion.mostrarResumen() + "\n";
                contador++;
        }
        return notificaciones;
        }
    
    public String consultarCuentaBancaria(){
      //Dejo el método vacío en caso de que vayamos a convertir usuario en una clase abstracta, por lo cual este método sería abstracto.
      return "";
    }

     public void recibirNotificacion(Notificacion notificacion){
      		notificaciones.add(notificacion);
     }

	

	
}