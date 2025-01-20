package fabrica;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import tienda.Inventario;
import tienda.Producto;

public class Fabrica implements Serializable{
    private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
    private List<Trabajador> trabajadores;
    private List<ArrayList<Object>> ordenesPendientes;
    Inventario inventario;

    public Fabrica() {
        this.trabajadores = new ArrayList<>();
        this.ordenesPendientes = new ArrayList<>();
        this.inventario = inventario;
        for (int i = 1; i <= 100; i++) {
            trabajadores.add(new Trabajador(i, "Trabajador " + i, "08:00-20:00"));
        }
        
    }
    
    // getters y setters
   public List<ArrayList<Object>> getOrdenesPendientes() {
        return ordenesPendientes;
    }

    //metodos
    
    public String recibirOrden(ArrayList<Producto> productos, ArrayList<Integer> cantidades) {
        ArrayList<Object> orden = new ArrayList<>();
        orden.add(productos);
        orden.add(cantidades);

         // Calcula la hora de entrega y la añade a la orden
         int tiempoProduccion = calcularTiempoProduccion(productos, cantidades);
         LocalTime horaEntrega = LocalTime.now().plusMinutes(tiempoProduccion);
         orden.add(horaEntrega);

 
         int trabajadoresRequeridos = calcularTrabajadoresRequeridos(cantidades);
         if (asignarTrabajadores(trabajadoresRequeridos)) {
             ordenesPendientes.add(orden);
             return "Orden de fabricación recibida. Los productos estarán listos a las "
                     + horaEntrega.format(DateTimeFormatter.ofPattern("HH:mm")) + ".";
         } else {
             return "No hay suficientes trabajadores disponibles para procesar esta orden.";
         }
     }

    private String verificarEntregas() {
        LocalTime ahora = LocalTime.now();
        List<ArrayList<Object>> entregasRealizadas = new ArrayList<>();
        String mensaje = "";

        for (ArrayList<Object> orden : ordenesPendientes) {
            LocalTime horaEntrega = (LocalTime) orden.get(2);

            if (!ahora.isBefore(horaEntrega)) { // Si es la hora o ya pasó
                ArrayList<Producto> productos = (ArrayList<Producto>) orden.get(0);
                ArrayList<Integer> cantidades = (ArrayList<Integer>) orden.get(1);

                entregarProductos(productos, cantidades);
                int trabajadoresRequeridos = calcularTrabajadoresRequeridos(cantidades);
                liberarTrabajadores(trabajadoresRequeridos);
                entregasRealizadas.add(orden);

                mensaje += "Productos entregados al inventario a las "
                        + horaEntrega.format(DateTimeFormatter.ofPattern("HH:mm")) + ".\n";
            }
        }

        // Remover órdenes ya entregadas
        ordenesPendientes.removeAll(entregasRealizadas);

        return mensaje.isEmpty() ? "No hay entregas pendientes en este momento.\n" : mensaje;
    }

    private void entregarProductos(ArrayList<Producto> productos, ArrayList<Integer> cantidades) {
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            int cantidad = cantidades.get(i);
            inventario.reabastecerProductos(cantidad, producto);
        }
    }

    private boolean asignarTrabajadores(int trabajadoresRequeridos) {
        int asignados = 0;
        for (Trabajador trabajador : trabajadores) {
            if (trabajador.getEstado().equals("Disponible") && asignados < trabajadoresRequeridos) {
                trabajador.setEstado("Ocupado");
                asignados++;
            }
            if (asignados == trabajadoresRequeridos) {
                return true; // Se asignaron todos los trabajadores necesarios
            }
        }
        return false; // No se pudieron asignar suficientes trabajadores
    }

    private void liberarTrabajadores(int trabajadoresLiberar) {
        int liberados = 0;
        for (Trabajador trabajador : trabajadores) {
            if (trabajador.getEstado().equals("Ocupado") && liberados < trabajadoresLiberar) {
                trabajador.setEstado("Disponible");
                liberados++;
            }
            if (liberados == trabajadoresLiberar) {
                break; // Se liberaron los trabajadores necesarios
            }
        }
    }
    
    private int calcularTiempoProduccion(ArrayList<Producto> productos, ArrayList<Integer> cantidades) {
        int tiempoPorUnidad = 3; // 3 minutos para un solo producto
        int cantidadTotal = cantidades.stream().mapToInt(Integer::intValue).sum();
        return cantidadTotal * tiempoPorUnidad;
    }

    
    private int calcularTrabajadoresRequeridos(ArrayList<Integer> cantidades) {
        int trabajadoresPorUnidad = 2; // 2 trabajadores por producto
        int cantidadTotal = cantidades.stream().mapToInt(Integer::intValue).sum();
        return cantidadTotal * trabajadoresPorUnidad;
    }

}
