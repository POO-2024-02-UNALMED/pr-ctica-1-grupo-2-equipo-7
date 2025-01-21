package fabrica;

import java.io.Serializable;

public class Trabajador implements Serializable{
    private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
    private int idTrabajador;
    private String nombre;
    private String horario; 
    private String estado; 

    public Trabajador(int idTrabajador, String nombre, String horario) {
        this.idTrabajador = idTrabajador;
        this.nombre = nombre;
        this.horario = horario;
        this.estado = "Disponible";
    }
    
    // getters y setters
    
    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    // metodos    
    public String liberarTrabajador() {
        estado = "Disponible";
        return "Trabajador " + nombre + " ha sido liberado.";
    }
}
