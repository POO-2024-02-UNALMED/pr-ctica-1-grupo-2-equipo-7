package fabrica;

import java.util.ArrayList;

public class Fabrica {
    private String nombre;
    private int trabajadoresDisponibles;
    private ArrayList<OrdenFabricacion> ordenesPendientes;

    public Fabrica(String nombre, int trabajadoresDisponibles) {
        this.nombre = nombre;
        this.trabajadoresDisponibles = trabajadoresDisponibles;
        this.ordenesPendientes = new ArrayList<>();
    }
    // getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTrabajadoresDisponibles() {
        return trabajadoresDisponibles;
    }

    public void setTrabajadoresDisponibles(int trabajadoresDisponibles) {
        this.trabajadoresDisponibles = trabajadoresDisponibles;
    }

   public ArrayList<OrdenFabricacion> getOrdenesPendientes() {
        return ordenesPendientes;
    }

    //metodos
    
    public void recibirOrden(OrdenFabricacion orden) {
        ordenesPendientes.add(orden);
        System.out.println("Orden recibida: " + orden.getDescripcion());
    }

    public String asignarTrabajadoresYTiempo(OrdenFabricacion orden) {
        if (trabajadoresDisponibles >= orden.getTrabajadoresRequeridos()) {
            trabajadoresDisponibles -= orden.getTrabajadoresRequeridos();
            int tiempoEstimado = calcularTiempoProduccion(orden);
            return "Orden procesada. Tiempo estimado: " + tiempoEstimado + " horas.";
        } else {
            return "No hay suficientes trabajadores disponibles. La orden está en espera.";
        }
    }

    public void producirProducto(OrdenFabricacion orden) {
        if (trabajadoresDisponibles >= orden.getTrabajadoresRequeridos()) {
            System.out.println("Produciendo los productos de la orden: " + orden.getDescripcion());
            trabajadoresDisponibles -= orden.getTrabajadoresRequeridos();
            ordenesPendientes.remove(orden);
            trabajadoresDisponibles += orden.getTrabajadoresRequeridos(); // Trabajadores quedan disponibles tras finalizar
        } else {
            System.out.println("No hay trabajadores disponibles para iniciar esta orden.");
        }
    }
    private int calcularTiempoProduccion(OrdenFabricacion orden) {
        int tiempoBase = 2; // Tiempo base por lote de 10 productos
        int cantidadProductos = orden.getProductos().size();
        return (int) Math.ceil((double) cantidadProductos / 10) * tiempoBase;
    }
}
