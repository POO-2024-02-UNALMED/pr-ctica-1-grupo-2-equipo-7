package usuario;

import fabrica.Fabrica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import pasarelaPago.CuentaBancaria;
import tienda.Inventario; //alejandro funcionalidad 5 reporte
import tienda.Producto;

public class Vendedor extends Usuario implements Serializable{
    private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
    Inventario inventario;
    Fabrica fabrica;
    private ArrayList<ArrayList<Object>> ordenesPendientes; //alejandro funcionalidad 5 reporte

    public Vendedor(String nombre, CuentaBancaria cuenta, Inventario inventario, Fabrica fabrica){
        super(nombre, cuenta);
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

    public String crearOrdenFabricacion() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Producto> productosSeleccionados = new ArrayList<>();
        ArrayList<Integer> cantidades = new ArrayList<>();

        while (true) {
            String nombreProducto = scanner.nextLine();
            if (nombreProducto.equalsIgnoreCase("fin")) {
                break;
            }

            Producto producto = buscarProducto(nombreProducto);
            if (producto == null) {
                return "Producto no encontrado: " + nombreProducto;
            }

            int cantidad;
            try {
                cantidad = Integer.parseInt(scanner.nextLine());
                if (cantidad <= 0) {
                    return "Cantidad inválida para " + nombreProducto;
                }
            } catch (NumberFormatException e) {
                return "Entrada inválida, ingrese un número válido para la cantidad.";
            }

            productosSeleccionados.add(producto);
            cantidades.add(cantidad);
        }

        if (productosSeleccionados.isEmpty()) {
            return "No se seleccionaron productos. La orden no se creó.";
        }

        ArrayList<Object> orden = new ArrayList<>();
        orden.add(productosSeleccionados);
        orden.add(cantidades);
        ordenesPendientes.add(orden);

        String mensajeFabrica = fabrica.recibirOrden(productosSeleccionados, cantidades);
return "Orden creada con éxito. Productos seleccionados: " + productosSeleccionados.size() + ". \n"  + mensajeFabrica;
}
    

    private Producto buscarProducto(String nombre) {
        for (Producto producto : inventario.getProductosTotal()) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return producto;
            }
        }
        return null;
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

    public String consultarCuentaBancaria(){
        return "Estado de tu cuenta bancaria:\n" + "Saldo: " + this.getCuentaBancaria().getSaldo();
    }
}
