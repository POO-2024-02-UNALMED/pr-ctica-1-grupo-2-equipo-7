package uiMain;

import compras.CarritoCompras;
import fabrica.Fabrica;
import java.util.ArrayList;
import java.util.Random;
import pasarelaPago.CuentaBancaria;
import tienda.Inventario;
import tienda.Producto;
import tienda.Tienda;
import usuario.Comprador;
import usuario.Notificacion;
import usuario.Vendedor;
// import baseDatos.Deserializador;


public class App {
    public static void main(String[] args) {
        //Aqui se realiza la serializacion para enviar la tienda ya cargada a el MainMenu (Esto es solo un ejemplo)
        
       

        ArrayList<Producto> categoriaTecnologia = new ArrayList<>();
        ArrayList<Producto> categoriaAseo = new ArrayList<>();
        ArrayList<Producto> categoriaComida = new ArrayList<>();
        ArrayList<Producto> categoriaPapeleria = new ArrayList<>();
        ArrayList<Producto> categoriaJuegueteria = new ArrayList<>(); 
        ArrayList<Producto> categoriaDeportes = new ArrayList<>();

        Inventario inventario = new Inventario(categoriaTecnologia, categoriaAseo, categoriaComida, categoriaPapeleria, categoriaJuegueteria, categoriaDeportes);
        Comprador comprador = new Comprador("Carlos Bustamante" ,null, null);
        CarritoCompras carritoCompras = new CarritoCompras(comprador);
        comprador.setCarritoCompras(carritoCompras);
        Fabrica fabrica = new Fabrica(inventario, null);
        CuentaBancaria cuentaBancariaComprador = new CuentaBancaria(comprador);
        Vendedor vendedor = new Vendedor("Enrique Iglesias", cuentaBancariaComprador, inventario, fabrica);
        fabrica.setVendedor(vendedor);
        CuentaBancaria cuentaBancariaVendedor = new CuentaBancaria(vendedor);
        comprador.setCuentaBancaria(cuentaBancariaComprador);
        vendedor.setCuentaBancaria(cuentaBancariaVendedor);
        //Todo esto de acá es para simular procesos de compra.
         comprador.getCuentaBancaria().recargarCuenta(10000);
        
        
 //Productos instanciados para probar la muestra de productos por pantalla
        
        Producto.Categoria[] categorias = Producto.Categoria.values();
        String[] nombresTecnologia = {"Celular", "Laptop", "Tableta ", "Audífonos", "Cámara", "Smartwatch", "Teclado", 
            "Mouse", "Monitor", "Drone", "Impresor", "Router", "Smart TV", "Cargador", "Auriculares", "Memoria Flash",
            "Bafle", "Reproductor Blu-ray", "Consola Gamium", "Proyector HD"};

        String[] nombresAseo = {"Jabón Líquido", "Shampoo", "Cepillo Dental", "Pasta Dental", "Desinfectante", "Esponja", 
            "Toallas", "Gel Antibacterial", "Cera de Piso", "Limpiavidrios", "Desodorante", "Hilo Dental", "Enjuague Bucal", "Lavaplatos",
            "Detergente", "Ambientador", "Papel Higiénico", "Toallas", "Cepillo", "Clorox"};

        String[] nombresComida = {"Manzana", "Queso", "Leche", "Yogur", "Pan", "Aceite", "Cereal", 
            "Galletas", "Mantequilla", "Pasta", "Miel", "Jugo de Naranja", "Avena", "Mermelada", 
            "Agua", "Frijoles", "Atún", "Sopa de Pollo", "Barra de Granola", "Palomitas"};

        String[] nombresPapeleria = {"Cuaderno A4", "Lápiz HB", "Bolígrafo Azul", "Borrador Mágico", "Libreta", "Carpeta", "Borrador", "Tijeras",
            "Pegamento", "Cinta", "Regla", "Marcadores", "Lapices", "Bloc de Dibujo", "Corrector",
            "Papel de Colores", "Grapadora", "Perforadora", "Cartulina", "Compás"};

        String[] nombresJugueteria = {"Muñeca ", "Auto Rayo", "Pelota Saltarina", "Lego ", "Puzzle", "Figura de Acción", "Bicicleta",
            "Patinete", "Dron Junior", "Set de Tren", "Juguete de Cocina", "Castillo de Princesa", "Helicóptero RC", "Avión de Pasajeros", "Torre de Bloques",
            "Rompecabezas", "Bate de Béisbol", "Robot Interactivo", "Tabla de Skate", "Cubo Rubik"};

        String[] nombresDeportes = {"Balón de Fútbol", "Raqueta de Tenis", "Gorra de Running", "Tennis", "Guantes de Boxeo", "Pesa Kettlebell", "Bolsa de Deporte",
            "Gafas de Natación", "Bicicleta de Montaña", "Patineta Freestyle", "Mancuerna Ajustable", "Camiseta de futbol", "Pantalón de Yoga", "Protector Bucal", "Cuerda para Saltar",
            "Banco de Pesas", "Chaleco Reflectivo", "Casco de Ciclismo", "Balón de Baloncesto", "Reloj Deportivo"};

        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int cantidad = (i + 1) * 5;  // Ejemplo de valor para cantidad
            int cantidadAlerta = (i + 1) * 2;  // Ejemplo de valor para cantidadAlerta
            int ID = i + 1;
            int precio = 10 + i * 3;  // Ejemplo de precio
            String descripcion = "Descripcion del producto " + (i + 1);
            boolean retornable = (i % 2 == 0);  // Alterna retornabilidad

            // Determina la categoría y nombre del producto
            Producto.Categoria categoria = categorias[i % categorias.length];
            String nombre;
            
            switch (categoria) {
                case TECNOLOGIA:
                    nombre = nombresTecnologia[random.nextInt(nombresTecnologia.length)];
                    break;
                case ASEO:
                    nombre = nombresAseo[random.nextInt(nombresAseo.length)];
                    break;
                case COMIDA:
                    nombre = nombresComida[random.nextInt(nombresComida.length)];
                    break;
                case PAPELERIA:
                    nombre = nombresPapeleria[random.nextInt(nombresPapeleria.length)];
                    break;
                case JUGUETERIA:
                    nombre = nombresJugueteria[random.nextInt(nombresJugueteria.length)];
                    break;
                case DEPORTES:
                    nombre = nombresDeportes[random.nextInt(nombresDeportes.length)];
                    break;
                default:
                    nombre = "Producto" + (i + 1);
            }

            inventario.añadirProducto(new Producto(
                    cantidad, 
                    cantidadAlerta, 
                    0, 
                    0, 
                    categoria, 
                    ID, 
                    nombre, 
                    precio, 
                    descripcion, 
                    retornable
            ));
        }

        
        Tienda tienda = new Tienda("NombreTienda", inventario, false, null);
        Notificacion notificacion = new Notificacion(null, null, vendedor);


//         // Crear una instancia de MainMenu
        MainMenu mainMenu = new MainMenu(comprador, vendedor, tienda, inventario, notificacion);

        //INSTRUCCIONES PARA SEREALIZAR: Primero ejecuten la tienda comentando el mainMenu que se encuentra abajo (el que no tiene parametros).
        //Después de ejecutar cierran la aplicación dandole a la opción de salir en el menú principal. Después de ejecutar la aplicación comenten todo lo que se encuentra arriba
        //(a excepción del main y el nombre de la clase App obviamente) y descomenten el mainMenu que se encuentra abajo (el que no tiene parametros). Después de eso pueden ejecutar
        //todo serializado ya que primero se necesitaban crear los objetos. Si desean empezar desde cero repitan el proceso.
        //NOTA: actualmente la serialización no funciona correctamente para todas aquellas funcionalidades que requieren de Inventario ya que la serialización no puede almacenar
        //datos de atributos estáticos o métodos estáticos, la única forma de hacerlo por ahora sería convirtiendo todos esos atributos estáticos y métodos estáticos en no estáticos
        //Pero eso lo consultaré luego con ustedes.

    //     Deserializador.deserializarInventarioStatic();
    //    MainMenu mainMenu = new MainMenu();
        
        // Mostrar el menú principal
        mainMenu.display();
    }
}
