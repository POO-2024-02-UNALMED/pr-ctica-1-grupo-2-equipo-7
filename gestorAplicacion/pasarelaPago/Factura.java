package pasarelaPago;

import compras.CarritoCompras;
import java.io.Serializable;
import tienda.Producto;
import usuario.Comprador;

public class Factura implements Serializable, Cupon{
    private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
    private CarritoCompras carritoCompras;
    private int IDFactura;
    private Transaccion transaccion;
    
    public Factura(){};

    public Factura(CarritoCompras carritoCompras, int IDFactura, Transaccion transaccion){
        this.carritoCompras = carritoCompras;
        this.IDFactura = IDFactura;
        this.transaccion = transaccion;
        // Todo lo que esta aquí abajo es para generar un cupón de descuento aleatorio para siguientes compras del usuario durante el procesos de compra.
        boolean verificacion = crearCupon();
        if (verificacion){
            int valorDescuentoAleatorio = Cupon.generarValorCupon();
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
                if (cantidadVendida >= cantidadRetornar && cantidadRetornar > 0) {
                    return producto;
                }
            }
        }
        return null;
    }
}
