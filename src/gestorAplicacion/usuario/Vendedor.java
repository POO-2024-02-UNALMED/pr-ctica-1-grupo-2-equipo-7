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

    public Vendedor(CuentaBancaria cuentaBancaria, Inventario inventario, Fabrica fabrica){
        this("Fulanito", cuentaBancaria, inventario, fabrica);
    }

    // getters y setters
    //Permite al vendedor decidir qué productos y cuántas unidades solicitar a la fábrica.

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
                return "Producto no encontrado: " + nombreProducto  + "\nPor favor vuelva a ingresar todos los productos de nuevo";
                
            }

            String entradaCantidad = scanner.nextLine();
            boolean esNumero = entradaCantidad.matches("\\d+");
            if (!esNumero) {
                return "Cantidad inválida.\nvuelva a ingresar todos los producto nuevamente con una cantidad valida";
            }

            int cantidad = Integer.parseInt(entradaCantidad);

            productosSeleccionados.add(producto);
            cantidades.add(cantidad);
            ArrayList<Object> orden = new ArrayList<>();
            orden.add(productosSeleccionados);
            orden.add(cantidades);
            ordenesPendientes.add(orden);

            
        if(cantidad>50){
            ordenesPendientes.removeAll(ordenesPendientes);
        }
        }

        if (productosSeleccionados.isEmpty()) {
            return "No se seleccionaron productos. La orden no se creó.";
        }
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

    @Override
    public String consultarCuentaBancaria(){
        return "Estado de tu cuenta bancaria:\n" + "Saldo: " + this.getCuentaBancaria().getSaldo();
    }
}
