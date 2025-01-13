package fabrica;

import java.util.ArrayList;
import tienda.Producto;

public class Fabrica {
    private String nombre;
    private int trabajadoresDisponibles;
    private ArrayList<ArrayList<Object>> ordenesPendientes;

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

   public ArrayList<ArrayList<Object>> getOrdenesPendientes() {
        return ordenesPendientes;
    }

    //metodos
    
    public void recibirOrden(ArrayList<Producto> productos, ArrayList<Integer> cantidades) {
        ArrayList<Object> orden = new ArrayList<>();
        orden.add(productos);
        orden.add(cantidades);
        ordenesPendientes.add(orden);
        System.out.println("Orden recibida.");
    }

    public String asignarTrabajadoresYTiempo(ArrayList<Object> orden) {
        ArrayList<Producto> productos = (ArrayList<Producto>) orden.get(0);
        ArrayList<Integer> cantidades = (ArrayList<Integer>) orden.get(1);

        int trabajadoresRequeridos = calcularTrabajadoresRequeridos(productos, cantidades);

        if (trabajadoresDisponibles >= trabajadoresRequeridos) {
            trabajadoresDisponibles -= trabajadoresRequeridos;
            int tiempoEstimado = calcularTiempoProduccion(productos, cantidades);
            return "Orden procesada. Tiempo estimado: " + tiempoEstimado + " horas.";
        } else {
            return "No hay suficientes trabajadores disponibles. La orden está en espera.";
        }
    }

    public void producirProducto(ArrayList<Object> orden) {
        ArrayList<Producto> productos = (ArrayList<Producto>) orden.get(0);
        ArrayList<Integer> cantidades = (ArrayList<Integer>) orden.get(1);

        int trabajadoresRequeridos = calcularTrabajadoresRequeridos(productos, cantidades);

        if (trabajadoresDisponibles >= trabajadoresRequeridos) {
            System.out.println("Produciendo los productos de la orden.");
            trabajadoresDisponibles -= trabajadoresRequeridos;
            ordenesPendientes.remove(orden); // Eliminar la orden después de producirla
            trabajadoresDisponibles += trabajadoresRequeridos; // Los trabajadores vuelven a estar disponibles
        } else {
            System.out.println("No hay trabajadores disponibles para iniciar esta orden.");
        }
    }
    
    private int calcularTiempoProduccion(ArrayList<Producto> productos, ArrayList<Integer> cantidades) {
        int tiempoBase = 2; 
        int cantidadProductos = 0;

        // Sumar todas las cantidades de productos en la orden
        for (Integer cantidad : cantidades) {
            cantidadProductos += cantidad;
        }
        return (int) Math.ceil((double) cantidadProductos / 10) * tiempoBase; 
    }

    private int calcularTrabajadoresRequeridos(ArrayList<Producto> productos, ArrayList<Integer> cantidades) {
        int trabajadores = 0;
        for (int i = 0; i < productos.size(); i++) {
            trabajadores += (int) Math.ceil((double) cantidades.get(i) / 10);
        }
        return trabajadores;
    }
}
