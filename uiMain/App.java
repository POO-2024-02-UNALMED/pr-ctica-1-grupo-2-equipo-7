package uiMain;

import compras.CarritoCompras;
import compras.Reseña;
import fabrica.Fabrica;
import java.util.ArrayList;
import pasarelaPago.CuentaBancaria;
import tienda.Inventario;
import tienda.Producto;
import tienda.Producto.Categoria;
import tienda.Tienda;
import usuario.Comprador;
import usuario.Notificacion;
import usuario.Vendedor;
// import baseDatos.Deserializador;



public class App {
    public static void main(String[] args) {
//         //Aqui se realiza la serializacion para enviar la tienda ya cargada a el MainMenu (Esto es solo un ejemplo)
        ArrayList<Reseña> reseñas1 = new ArrayList<>();
        ArrayList<Reseña> reseñas2 = new ArrayList<>();
        ArrayList<Reseña> reseñas3 = new ArrayList<>();

        Producto producto1 = new Producto(9, 10, 0, Producto.Categoria.TECNOLOGIA, 1, "Laptop", 1200, "Laptop de alto rendimiento", true, reseñas1);
        Producto producto2 = new Producto(200, 20, 0, Producto.Categoria.ASEO, 2, "Jabon", 5, "Jabón antibacterial", false, reseñas2);
        Producto producto3 = new Producto(100, 15, 0, Producto.Categoria.COMIDA, 3, "Galletas", 3, "Galletas de chocolate", false, reseñas3);
        //producto producto4= new Producto(100, 15, 0, Producto.Categoria.COMIDA, 2, "Galletas", 3, "Galletas de chocolate", false, reseñas3);
       

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
        Comprador comprador = new Comprador("Carlos Bustamante" ,null, null);
        CarritoCompras carritoCompras = new CarritoCompras(comprador);
        carritoCompras.añadirProducto(producto1, 2);
        carritoCompras.añadirProducto(producto2, 2);
        carritoCompras.añadirProducto(producto3, 2);
        carritoCompras.calcularTotal();
        comprador.setCarritoCompras(carritoCompras);
        Fabrica fabrica = new Fabrica();
        CuentaBancaria cuentaBancariaComprador = new CuentaBancaria(comprador);
        Vendedor vendedor = new Vendedor("Enrique Iglesias", cuentaBancariaComprador, inventario, fabrica);
        CuentaBancaria cuentaBancariaVendedor = new CuentaBancaria(vendedor);
        comprador.setCuentaBancaria(cuentaBancariaComprador);
        vendedor.setCuentaBancaria(cuentaBancariaVendedor);
        //Todo esto de acá es para simular procesos de compra.
         comprador.getCuentaBancaria().recargarCuenta(10000);
         comprador.getValorCupones().add(15);
         comprador.cantidadCupones +=1;
        
        
 //Productos instanciados para probar la muestra de productos por pantalla
        
        Producto.Categoria[] categorias = Producto.Categoria.values();
        
        for (int i = 0; i < 100; i++) {
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
        Notificacion notificacion = new Notificacion(null, null, vendedor);

        Categoria[] tuki = Categoria.values();

        for (Categoria a : tuki){
            System.out.println(a);
        }
    

        // Crear una instancia de MainMenu
        MainMenu mainMenu = new MainMenu(comprador, vendedor, tienda, inventario, notificacion);

        //INSTRUCCIONES PARA SEREALIZAR: Primero ejecuten la tienda comentando el mainMenu que se encuentra abajo (el que no tiene parametros).
        //Después de ejecutar cierran la aplicación dandole a la opción de salir en el menú principal. Después de ejecutar la aplicación comenten todo lo que se encuentra arriba
        //(a excepción del main y el nombre de la clase App obviamente) y descomenten el mainMenu que se encuentra abajo (el que no tiene parametros). Después de eso pueden ejecutar
        //todo serializado ya que primero se necesitaban crear los objetos. Si desean empezar desde cero repitan el proceso.
        //NOTA: actualmente la serialización no funciona correctamente para todas aquellas funcionalidades que requieren de Inventario ya que la serialización no puede almacenar
        //datos de atributos estáticos o métodos estáticos, la única forma de hacerlo por ahora sería convirtiendo todos esos atributos estáticos y métodos estáticos en no estáticos
        //Pero eso lo consultaré luego con ustedes.

        // Deserializador.deserializarInventarioStatic();
        // MainMenu mainMenu = new MainMenu();
        
        // Mostrar el menú principal
        mainMenu.display();
    }
}
