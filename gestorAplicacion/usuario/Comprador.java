package gestorAplicacion.usuario;

import java.util.ArrayList;
import gestorAplicacion.compras.CarritoCompras;
import gestorAplicacion.compras.HistorialCompras;
import gestorAplicacion.pasarelaPago.CuentaBancaria;
import gestorAplicacion.pasarelaPago.Factura;
import gestorAplicacion.tienda.Producto;

public class Comprador extends Usuario{
    protected CarritoCompras carritoCompras;
    protected HistorialCompras historialCompras;
    protected ArrayList<Integer> valorCupones = new ArrayList<>();
    public int cantidadCupones; // Cantidad inicial de cupones antes de la compra

    public Comprador(CarritoCompras carritoCompras, CuentaBancaria cuenta){
        super(cuenta);
        this.carritoCompras = carritoCompras;
        this.historialCompras = new HistorialCompras();
        this.valorCupones.add(10); // se le añade a la lista de cupones un cupon de 10% de descuento que van a tener por defecto todos los compradores.
        this.cantidadCupones += 1; // se le añade a la cantidad de cupones 1 por defecto.
    }

    //getters y setters

    public CarritoCompras getCarritoCompras() {
      return this.carritoCompras;
    }
    public void setCarritoCompras(CarritoCompras carritoCompras) {
        this.carritoCompras = carritoCompras;
      }
    public HistorialCompras getHistorialCompras() {
      return this.historialCompras;
    }

    public ArrayList<Integer> getValorCupones() {
      return this.valorCupones;
    }

    // metodos
    
    public boolean devolverProducto(int idFactura, int idProducto, int cantidadRetornar, Vendedor vendedor){
        Factura factura = historialCompras.buscarFactura(idFactura); // Verificar existencia de la factura
        if(factura != null){
            Producto producto = factura.verificarProducto(idProducto, cantidadRetornar, true); // Verificar existencia del producto en la factura
            if(producto != null){
                int descuento = factura.getCarritoCompras().getDescuentoAplicadoCompra(); // Obtener descuento aplicado a la compra
                double valorDevolver = vendedor.devolucionDinero(this, producto.getPrecio(), descuento, cantidadRetornar); // Devolver dinero al comprador
                vendedor.devolverProducto(cantidadRetornar, producto); // Actualizar inventario
                historialCompras.actualizarCantidadDevueltos(cantidadRetornar); // Actualizar cantidad de productos devueltos
                factura.modificarFactura(producto, cantidadRetornar, "eliminar"); // modificar factura con los cambios 
                String mensajeComprador = "Su devolución de " + cantidadRetornar + " productos por un valor de " + valorDevolver + " ha sido procesada exitosamente."; 
                String asuntoComprador = "Devolución procesada"; 
                String mensajeVendedor = "Ha recibido una devolución de " + cantidadRetornar + " productos por un valor de " + valorDevolver + "."; 
                String asuntoVendedor = "Devolución recibida";
                this.recibirNotificacion(new Notificacion(mensajeComprador, asuntoComprador, this)); // Notificación de devolución al comprador
                vendedor.recibirNotificacion(new Notificacion(mensajeVendedor, asuntoVendedor, vendedor)); // Notificación de devolución al vendedor
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public String mostrarHistorialCompras(){
        return historialCompras.mostrarFactura();
    }

    public String mostrarCupones(){
        StringBuilder mensaje = new StringBuilder();
        for (int i = 0; i < this.valorCupones.size(); i++){
            mensaje.append((i+1) + ". Descuento de: " + this.valorCupones.get(i) +"%\n");
        }
        return mensaje.toString();
    }

    public void eliminarCupones(int cuponEliminar){
        this.valorCupones.remove(cuponEliminar - 1);
    }

}
