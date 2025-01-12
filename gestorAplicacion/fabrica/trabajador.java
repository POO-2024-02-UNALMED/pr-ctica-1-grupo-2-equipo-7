package fabrica;

import java.time.LocalTime;

public class Trabajador {
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
    public boolean verificarHorario() {
       
       String[] partesHorario = horario.split("-");
        LocalTime inicio = LocalTime.parse(partesHorario[0]);
        LocalTime fin = LocalTime.parse(partesHorario[1]);
        LocalTime ahora = LocalTime.now();
        return estado.equals("Disponible") && ahora.isAfter(inicio) && ahora.isBefore(fin);
    }

    public void asignarTarea(OrdenFabricacion orden) {
        if (verificarHorario()) {
            estado = "Ocupado";
            System.out.println("Trabajador " + nombre + " ha sido asignado a la orden: " + orden.getDescripcion());
        } else {
            System.out.println("El trabajador " + nombre + " no está disponible para esta tarea.");
        }
    }
    public void liberarTrabajador() {
        estado = "Disponible";
        System.out.println("Trabajador " + nombre + " ha sido liberado.");
    }
}
