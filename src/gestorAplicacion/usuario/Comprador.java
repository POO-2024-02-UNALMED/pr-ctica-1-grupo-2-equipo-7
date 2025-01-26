package usuario;

import compras.CarritoCompras;
import compras.HistorialCompras;
import java.io.Serializable;
import java.util.ArrayList;
import pasarelaPago.CuentaBancaria;
import pasarelaPago.Factura;
import tienda.Inventario;
import tienda.Producto;

public class Comprador extends Usuario implements Serializable{
    private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
    protected CarritoCompras carritoCompras;
    protected HistorialCompras historialCompras;
    protected ArrayList<Integer> valorCupones = new ArrayList<>();
    public int cantidadCupones; // Cantidad inicial de cupones antes de la compra

    public Comprador(String nombre, CarritoCompras carritoCompras, CuentaBancaria cuenta){
        super(nombre, cuenta);
        this.carritoCompras = carritoCompras;
        this.historialCompras = new HistorialCompras();
        this.valorCupones.add(10); // se le añade a la lista de cupones un cupon de 10% de descuento que van a tener por defecto todos los compradores.
        this.cantidadCupones += 1; // se le añade a la cantidad de cupones 1 por defecto.
    }

    public Comprador(CarritoCompras carritoCompras, CuentaBancaria cuenta){
        this("Pepito Perez", carritoCompras, cuenta);
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
    
    public String devolverProducto(int idFactura, int idProducto, int cantidadRetornar, Vendedor vendedor){
        Factura factura = historialCompras.buscarFactura(idFactura); // Verificar existencia de la factura
        if(factura != null){
            Producto producto = factura.verificarProducto(idProducto, cantidadRetornar, true); // Verificar existencia del producto en la factura
            if(producto != null){
                int descuento = factura.getCarritoCompras().getDescuentoAplicadoCompra(); // Obtener descuento aplicado a la compra
                double valorDevolver = vendedor.devolucionDinero(this, producto.getPrecio(), descuento, cantidadRetornar); // Devolver dinero al comprador
                vendedor.devolverProducto(cantidadRetornar, producto); // Actualizar inventario
                historialCompras.actualizarCantidadDevueltos(cantidadRetornar); // Actualizar cantidad de productos devueltos
                Inventario.ajusteProductos(producto, "devolucion");
                factura.modificarFactura(producto, cantidadRetornar, "eliminar"); // modificar factura con los cambios 
                String mensajeComprador = "Su devolución de " + cantidadRetornar + " " + producto.getNombre()  +"/s por un valor de " + valorDevolver + " pesos (corresponde a lo pagado menos un 10% de retención) ha sido procesada exitosamente."; 
                String asuntoComprador = "Devolución procesada"; 
                String mensajeVendedor = "Ha recibido una devolución de " + cantidadRetornar + " productos por un valor de " + valorDevolver + "."; 
                String asuntoVendedor = "Devolución recibida";
                this.recibirNotificacion(new Notificacion(mensajeComprador, asuntoComprador, this)); // Notificación de devolución al comprador
                vendedor.recibirNotificacion(new Notificacion(mensajeVendedor, asuntoVendedor, vendedor)); // Notificación de devolución al vendedor
                return "DevolucionExitosa";
            }else{
                return "ProductoInvalido";
            }
        }else{
            return "FacturaInvalida";
        }
    }

    @Override
    public String consultarCuentaBancaria(){
        return "Estado de tu cuenta bancaria:\n" + "Saldo: " + this.getCuentaBancaria().getSaldo();
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
