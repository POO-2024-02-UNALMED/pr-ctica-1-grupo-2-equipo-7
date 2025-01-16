package usuario;

import java.util.ArrayList;
import pasarelaPago.CuentaBancaria;
import tienda.Producto;
import tienda.Inventario;
import fabrica.Fabrica; //alejandro funcionalidad 5 reporte

public class Vendedor extends Usuario {
    Inventario inventario;
    Fabrica fabrica;
    private ArrayList<ArrayList<Object>> ordenesPendientes; //alejandro funcionalidad 5 reporte

    public Vendedor(CuentaBancaria cuenta, Inventario inventario, Fabrica fabrica){
        super(cuenta);
        this.inventario = inventario;
        this.fabrica = fabrica;
        this.ordenesPendientes = new ArrayList<>();  //alejandro funcionalidad 5 reporte
    }

    // getters y setters
    
    // Genera un reporte detallado del inventario actual, incluyendo productos vendidos, no vendidos y cantidades vendidas.
     
    public ArrayList<String> generarReporteInventario() {
        ArrayList<String> reporte = new ArrayList<>();
        ArrayList<Producto> productos = inventario.getProductosTotal();

        for (Producto producto : productos) {
            if (producto.getCantidadVendida() > 0) {
                reporte.add(producto.getNombre() + ": Vendido - " + producto.getCantidadVendida() + " unidades");
            } else {
                reporte.add(producto.getNombre() + ": No vendido - " + producto.getCantidad() + " unidades disponibles");
            }
        }

        return reporte;
    }
    // Permite al vendedor decidir qué productos y cuántas unidades solicitar a la fábrica.

    public void crearOrdenFabricacion(ArrayList<Producto> productosSeleccionados, ArrayList<Integer> cantidades) {
        ArrayList<Object> orden = new ArrayList<>();
        orden.add(productosSeleccionados); 
        orden.add(cantidades);          

        // Agregar la nueva orden a la lista de órdenes pendientes
        ordenesPendientes.add(orden);

        // Enviar la orden a la fábrica 
        fabrica.recibirOrden(productosSeleccionados, cantidades);
    }

    public double devolucionDinero(Usuario usuarioReceptor, double precioProducto, int descuento, int cantidadRetornar){
        double valorDevolver;
        if(descuento > 0){
            double precioConDescuento = precioProducto - (precioProducto * (descuento / 100.0)); 
            valorDevolver = (precioConDescuento - (precioConDescuento * 0.01)) * cantidadRetornar;
        }else{
            valorDevolver = (precioProducto - (precioProducto * 0.1)) * cantidadRetornar;
        }
        pago(this, usuarioReceptor, valorDevolver, "devolucion");
        return valorDevolver;
    }

    public void devolverProducto(int cantidad, Producto producto){
        inventario.reabastecerProductos(cantidad, producto);
    }
    
    // Devuelve una lista de órdenes de fabricación pendientes.
    // retorna Lista de órdenes pendientes.
    
    public ArrayList<ArrayList<Object>> getOrdenesPendientes() {
        return ordenesPendientes;
    }
    // Actualiza el estado de una orden de fabricación específica.
    // orden Orden de fabricación que se completó.
     
    public void actualizarEstadoOrden(ArrayList<Object> orden) {
        ordenesPendientes.remove(orden);
    }
}
