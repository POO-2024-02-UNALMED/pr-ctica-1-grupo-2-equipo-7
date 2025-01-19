package usuario;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notificacion implements Serializable{
    private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
    private String mensaje;
    private String asunto;
    private Usuario destinatario;
    private String fecha;

    public Notificacion(String mensaje, String asunto, Usuario destinatario) {
        this.mensaje = mensaje;
        this.asunto = asunto;
        this.destinatario = destinatario;

         // Formatear la fecha al momento de crear el objeto.
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
         this.fecha = LocalDateTime.now().format(formatter); // Almacenar la fecha como String formateado.
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

    public String getFecha() {
        return this.fecha;
    }

    // metodos

    public String mostrarResumen() {
        int limite = 80;
        StringBuilder mensajeFormateado = new StringBuilder();

        for (int i = 0; i < this.mensaje.length(); i += limite) {
            int fin = Math.min(i + limite, this.mensaje.length());
            mensajeFormateado.append(this.mensaje, i, fin).append("");
        }

        String mensajeCorto = mensajeFormateado.toString();
    
        return "Fecha: " + this.fecha + "\n" +
               "Destinatario: " + this.destinatario.getNombre() + "\n" +
               "Asunto: " + this.asunto + "\n" +
               "Mensaje: " + mensajeCorto;
    }
}