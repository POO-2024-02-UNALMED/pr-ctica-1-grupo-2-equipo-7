package gestorAplicacion.usuario;

import java.time.LocalDateTime;

public class Notificacion {
    private String mensaje;
    private String asunto;
    private Usuario destinatario;
    private LocalDateTime fecha;

    public Notificacion(String mensaje, String asunto, Usuario destinatario) {
        this.mensaje = mensaje;
        this.asunto = asunto;
        this.destinatario = destinatario;
        this.fecha = LocalDateTime.now(); // fecha actual
    }

    //getters y setters

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String value) {
        this.mensaje = value;
    }

    public String getAsunto() {
        return this.asunto;
    }

    public void setAsunto(String value) {
        this.asunto = value;
    }

    public Usuario getDestinatario() {
        return this.destinatario;
    }

    public void setDestinatario(Usuario value) {
        this.destinatario = value;
    }

    public LocalDateTime getFecha() {
        return this.fecha;
    }

    // metodos

    public String mostrarResumen() {
        int limite = 100;
        String mensajeCorto = this.mensaje.length() > limite 
            ? this.mensaje.substring(0, limite) + "..." 
            : this.mensaje;
    
        return "Fecha: " + this.fecha + "\n" +
               "Destinatario: " + this.destinatario.getNombre() + "\n" +
               "Asunto: " + this.asunto + "\n" +
               "Mensaje:\n" + mensajeCorto;
    }
}