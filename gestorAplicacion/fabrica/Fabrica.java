package fabrica;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import tienda.Producto;

public class Fabrica implements Serializable{
    private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
    private List<Trabajador> trabajadores;
    private List<ArrayList<Object>> ordenesPendientes;

    public Fabrica() {
        this.trabajadores = new ArrayList<>();
        this.ordenesPendientes = new ArrayList<>();
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
        ordenesPendientes.add(orden);

        String mensaje = "Orden de fabricación recibida. Se están procesando los productos.\n";
        mensaje += procesarOrden(orden);  // Procesa la orden de inmediato sin hilos.

        return mensaje;
    }

    private String procesarOrden(ArrayList<Object> orden) {
        ArrayList<Producto> productos = (ArrayList<Producto>) orden.get(0);
        ArrayList<Integer> cantidades = (ArrayList<Integer>) orden.get(1);


        int trabajadoresRequeridos = calcularTrabajadoresRequeridos(productos, cantidades);
        String mensaje = "Procesando su orden... ";

        if (trabajadoresRequeridos > 0) {
            asignarTrabajadores(trabajadoresRequeridos);
            int tiempoProduccion = calcularTiempoProduccion(productos, cantidades);

            mensaje += "Los trabajadores han sido asignados, producción en curso.\n";
            mensaje += "Esperando " + tiempoProduccion + " minutos para completar la producción...\n";
            int tiempoRestante = tiempoProduccion;
            while (tiempoRestante > 0) {
                // Este ciclo simula que la producción está avanzando.
                // El tiempo restante se va reduciendo en "unidades de tiempo" (puede ser un minuto)
                tiempoRestante--;
                
                // Aquí podrías actualizar el estado del sistema si lo necesitas.
                // Esto simula el paso del tiempo sin bloquear el flujo del programa.
            }

            liberarTrabajadores();
            mensaje += "Los productos han sido procesados y serán entregados al inventario en breve.\n";
            mensaje += "Su pedido estará listo para el inventario a las " + calcularHoraEntrega(tiempoProduccion) + ".";
        } else {
            mensaje += "No hay suficientes trabajadores disponibles para esta orden.";
        }

        return mensaje;
    }

    private void asignarTrabajadores(int trabajadoresRequeridos) {
        int asignados = 0;
        for (Trabajador trabajador : trabajadores) {
            if (trabajador.getEstado().equals("Disponible") && asignados < trabajadoresRequeridos) {
                trabajador.setEstado("Ocupado");
                asignados++;
            }
            if (asignados == trabajadoresRequeridos) break;
        }
    }

    private void liberarTrabajadores() {
        for (Trabajador trabajador : trabajadores) {
            if (trabajador.getEstado().equals("Ocupado")) {
                trabajador.setEstado("Disponible");
            }
        }
    }
    
    private int calcularTiempoProduccion(ArrayList<Producto> productos, ArrayList<Integer> cantidades) {
        int tiempoPorUnidad = 3;  // 30 minutos para un solo producto
        int cantidadTotal = cantidades.stream().mapToInt(Integer::intValue).sum();
        
        // El tiempo de producción total en minutos
        return cantidadTotal * tiempoPorUnidad;
    }

    private String calcularHoraEntrega(int tiempoProduccion) {
        // Obtener la hora actual de la PC
        LocalTime horaActual = LocalTime.now();

        // Sumar el tiempo de producción a la hora actual
        LocalTime horaEntrega = horaActual.plusMinutes(tiempoProduccion);

        // Convertir la hora de entrega a formato 24 horas (HH:mm)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return horaEntrega.format(formatter);
    }
    
    private int calcularTrabajadoresRequeridos(ArrayList<Producto> productos, ArrayList<Integer> cantidades) {
        int trabajadoresPorUnidad = 2;  // 2 trabajadores por producto
        int cantidadTotal = cantidades.stream().mapToInt(Integer::intValue).sum();
        
        // El número total de trabajadores necesarios para todos los productos
        return cantidadTotal * trabajadoresPorUnidad;
    }
}
