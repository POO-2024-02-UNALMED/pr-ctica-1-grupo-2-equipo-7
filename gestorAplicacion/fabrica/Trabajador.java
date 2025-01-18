package fabrica;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import tienda.Producto;

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
    
    public boolean verificarHorario() {
        String[] partesHorario = horario.split("-");
        LocalTime inicio = LocalTime.parse(partesHorario[0]);
        LocalTime fin = LocalTime.parse(partesHorario[1]);
        LocalTime ahora = LocalTime.now();
        return estado.equals("Disponible") && ahora.isAfter(inicio) && ahora.isBefore(fin);
    }

    public String asignarTarea(ArrayList<Object> orden) {
        if (verificarHorario()) {
            estado = "Ocupado";
            ArrayList<Producto> productos = (ArrayList<Producto>) orden.get(0);
            ArrayList<Integer> cantidades = (ArrayList<Integer>) orden.get(1);
            StringBuilder descripcion = new StringBuilder("Productos: ");

            for (int i = 0; i < productos.size(); i++) {
                descripcion.append(productos.get(i).getNombre()).append(" (").append(cantidades.get(i)).append(" unidades), ");
            }
            return "Trabajador " + nombre + " ha sido asignado a la orden: " + descripcion.substring(0, descripcion.length() - 2);
        } else {
            return "El trabajador " + nombre + " no está disponible para esta tarea.";
        }
    }
    
    public String liberarTrabajador() {
        estado = "Disponible";
        return "Trabajador " + nombre + " ha sido liberado.";
    }
}
