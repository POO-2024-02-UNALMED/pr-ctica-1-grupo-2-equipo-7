package gestorAplicacion.pasarelaPago;
import gestorAplicacion.compras.CarritoCompras;
import gestorAplicacion.tienda.Producto;
import java.util.Random;
import gestorAplicacion.usuario.Comprador;

public class Factura {
    private CarritoCompras carritoCompras;
    private int IDFactura;
    private Transaccion transaccion;

    public Factura(CarritoCompras carritoCompras, int IDFactura, Transaccion transaccion){
        this.carritoCompras = carritoCompras;
        this.IDFactura = IDFactura;
        this.transaccion = transaccion;
        // Todo lo que esta aquí abajo es para generar un cupón de descuento aleatorio para siguientes compras del usuario durante el procesos de compra.
        Random random = new Random();
        Boolean generarCupon = random.nextBoolean(); // Esto nos indica si se va a generar un cupón o no. Como devuelve true o false entonces será un 50/50.
        if (generarCupon){
            int valorDescuentoAleatorio = random.nextInt(11) + 5;
            Comprador comprador = (Comprador) transaccion.getUsuarioRemitente();
            comprador.getValorCupones().add(valorDescuentoAleatorio);
        }
    }
    // getters y setters
    
    public int getIDFactura(){
        return IDFactura;
    }
    public CarritoCompras getCarritoCompras(){
        return this.carritoCompras;
    }

    // metodos
    
    public void modificarFactura(Producto producto, int cantidad, String accion){
        if(accion.equalsIgnoreCase("eliminar")){
            carritoCompras.restarCantidadPorProducto(producto, cantidad);
        }
    }

    public Producto verificarProducto(int idProducto){
        Producto producto = carritoCompras.buscarProducto(idProducto);
        return producto;
    }

    public Producto verificarProducto(int idProducto, int cantidadRetornar, boolean retornabilidad){
        Producto producto = carritoCompras.buscarProducto(idProducto);
        if(producto != null){
            if(producto.isRetornable() == retornabilidad){
                int cantidadVendida = carritoCompras.getCantidadPorProducto(producto);
                if (cantidadVendida >= cantidadRetornar) {
                    return producto;
                }
            }
        }
        return null;
    }
}
