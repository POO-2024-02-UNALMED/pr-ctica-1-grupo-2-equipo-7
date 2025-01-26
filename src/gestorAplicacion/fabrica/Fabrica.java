package fabrica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import tienda.Inventario;
import tienda.Producto;
import usuario.Vendedor;

public class Fabrica implements Serializable{
    private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
    private List<Trabajador> trabajadores;
    private List<ArrayList<Object>> ordenesPendientes;
    Inventario inventario;
    Vendedor vendedor;

    public Fabrica(Inventario inventario, Vendedor vendedor) {
        this.trabajadores = new ArrayList<>();
        this.ordenesPendientes = new ArrayList<>();
        this.inventario = inventario;
        this.vendedor = vendedor;
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

         int trabajadoresRequeridos = calcularTrabajadoresRequeridos(cantidades);
         if (asignarTrabajadores(trabajadoresRequeridos)) {
             ordenesPendientes.add(orden);
             liberarTrabajadores(trabajadoresRequeridos);
             return "Orden de fabricación recibida.\n" + entregarProductos(productos, cantidades) ;
         } else {
             return "No hay suficientes trabajadores disponibles para procesar esta orden.";
         }
     }


    private String entregarProductos(ArrayList<Producto> productos, ArrayList<Integer> cantidades) {
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            int cantidad = cantidades.get(i);
            inventario.reabastecerProductos(cantidad, producto);
        }
        
        return "Le mandaremos un correo cuando se entreguen sus productos";
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
    
    private int calcularTrabajadoresRequeridos(ArrayList<Integer> cantidades) {
        int trabajadoresPorUnidad = 2; // 2 trabajadores por producto
        int cantidadTotal = cantidades.stream().mapToInt(Integer::intValue).sum();
        return cantidadTotal * trabajadoresPorUnidad;
    }


    public Vendedor getVendedor() {
      return this.vendedor;
    }
    public void setVendedor(Vendedor value) {
      this.vendedor = value;
    }
}
