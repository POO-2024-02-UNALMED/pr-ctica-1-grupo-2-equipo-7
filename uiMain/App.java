package uiMain;

import compras.CarritoCompras;
import compras.Reseña;
import fabrica.Fabrica;
import java.util.ArrayList;
import pasarelaPago.CuentaBancaria;
import tienda.Inventario;
import tienda.Producto;
import tienda.Tienda;
import usuario.Comprador;
import usuario.Notificacion;
import usuario.Vendedor;
import baseDatos.Deserializador;



public class App {
    public static void main(String[] args) {
//         //Aqui se realiza la serializacion para enviar la tienda ya cargada a el MainMenu (Esto es solo un ejemplo)
        

        //INSTRUCCIONES PARA SEREALIZAR: Primero ejecuten la tienda comentando el mainMenu que se encuentra abajo (el que no tiene parametros).
        //Después de ejecutar cierran la aplicación dandole a la opción de salir en el menú principal. Después de ejecutar la aplicación comenten todo lo que se encuentra arriba
        //(a excepción del main y el nombre de la clase App obviamente) y descomenten el mainMenu que se encuentra abajo (el que no tiene parametros). Después de eso pueden ejecutar
        //todo serializado ya que primero se necesitaban crear los objetos. Si desean empezar desde cero repitan el proceso.
        //NOTA: actualmente la serialización no funciona correctamente para todas aquellas funcionalidades que requieren de Inventario ya que la serialización no puede almacenar
        //datos de atributos estáticos o métodos estáticos, la única forma de hacerlo por ahora sería convirtiendo todos esos atributos estáticos y métodos estáticos en no estáticos
        //Pero eso lo consultaré luego con ustedes.

        Deserializador.deserializarInventarioStatic();
        MainMenu mainMenu = new MainMenu();
        
        // Mostrar el menú principal
        mainMenu.display();
    }
}
