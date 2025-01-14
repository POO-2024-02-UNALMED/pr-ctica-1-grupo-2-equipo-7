package uiMain;

import java.util.ArrayList;

import compras.CarritoCompras;
import compras.Reseña;
import fabrica.Fabrica;
import pasarelaPago.CuentaBancaria;
import tienda.Inventario;
import tienda.Producto;
import tienda.Tienda;
import usuario.Comprador;
import usuario.Vendedor;

public class App {
    public static void main(String[] args) {
        //Aqui se realiza la serializacion para enviar la tienda ya cargada a el MainMenu (Esto es solo un ejemplo)
        ArrayList<Reseña> reseñas1 = new ArrayList<>();
        ArrayList<Reseña> reseñas2 = new ArrayList<>();
        ArrayList<Reseña> reseñas3 = new ArrayList<>();

        Producto producto1 = new Producto(9, 10, 0, Producto.Categoria.TECNOLOGIA, 1, "Laptop", 1200, "Laptop de alto rendimiento", true, reseñas1);
        Producto producto2 = new Producto(200, 20, 0, Producto.Categoria.ASEO, 2, "Jabón", 5, "Jabón antibacterial", false, reseñas2);
        Producto producto3 = new Producto(100, 15, 0, Producto.Categoria.COMIDA, 3, "Galletas", 3, "Galletas de chocolate", false, reseñas3);
        
       

        ArrayList<Producto> categoriaTecnologia = new ArrayList<>();
        ArrayList<Producto> categoriaAseo = new ArrayList<>();
        ArrayList<Producto> categoriaComida = new ArrayList<>();
        ArrayList<Producto> categoriaPapeleria = new ArrayList<>();
        ArrayList<Producto> categoriaJuegueteria = new ArrayList<>(); 
        ArrayList<Producto> categoriaDeportes = new ArrayList<>();

        Inventario inventario = new Inventario(categoriaTecnologia, categoriaAseo, categoriaComida, categoriaPapeleria, categoriaJuegueteria, categoriaDeportes);
        inventario.añadirProducto(producto1);
        inventario.añadirProducto(producto2);
        inventario.añadirProducto(producto3);
        Comprador comprador = new Comprador(null, null);
        CarritoCompras carritoCompras = new CarritoCompras(comprador);
        carritoCompras.añadirProducto(producto1);
        carritoCompras.añadirProducto(producto2);
        carritoCompras.añadirProducto(producto3);
        carritoCompras.calcularTotal();
        comprador.setCarritoCompras(carritoCompras);
        Fabrica fabrica = new Fabrica("FabricaPrincipal", 1);
        CuentaBancaria cuentaBancariaComprador = new CuentaBancaria(comprador);
        Vendedor vendedor = new Vendedor(cuentaBancariaComprador, inventario, fabrica);
        CuentaBancaria cuentaBancariaVendedor = new CuentaBancaria(vendedor);
        comprador.setCuentaBancaria(cuentaBancariaComprador);
        vendedor.setCuentaBancaria(cuentaBancariaVendedor);
        //Todo esto de acá es para simular procesos de compra.
        comprador.getCarritoCompras().añadirProducto(producto1);
        comprador.getCarritoCompras().añadirProducto(producto2);
        comprador.getCarritoCompras().añadirProducto(producto3);
        comprador.getCarritoCompras().calcularTotal();
        comprador.getCuentaBancaria().recargarCuenta(10000);
        
        
 //Productos instanciados para probar la muestra de productos por pantalla
        
        Producto.Categoria[] categorias = Producto.Categoria.values();
        
        for (int i = 0; i < 30; i++) {
            int cantidad = (i + 1) * 5;  // Solo un ejemplo de valor para cantidad
            int cantidadAlerta = (i + 1) * 2;  // Solo un ejemplo de valor para cantidadAlerta
            int ID = i + 1;
            String nombre = "Producto" + (i + 1);
            int precio = 10 + i * 3;  // Solo un ejemplo de precio
            String descripcion = "Descripcion del producto " + (i + 1);
            boolean retornable = (i % 2 == 0);  // Alterna retornabilidad

            // Alternar categorías cíclicamente
            Producto.Categoria categoria = categorias[i % categorias.length];

             inventario.añadirProducto(new Producto(
                     cantidad, 
                     cantidadAlerta, 
                     0, 
                     categoria, 
                     ID, 
                     nombre, 
                     precio, 
                     descripcion, 
                     retornable, 
                    null  // reseñas es null para el ejemplo
                 )); 
             
        }
        
        
        Tienda tienda = new Tienda("NombreTienda", inventario, false, null);
        

        // Crear una instancia de MainMenu
        MainMenu mainMenu = new MainMenu(comprador, vendedor, tienda);
        
        // Mostrar el menú principal
        mainMenu.display();
    }
}
