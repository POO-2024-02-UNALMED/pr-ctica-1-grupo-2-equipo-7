package uiMain;

import java.util.Scanner;
import tienda.Producto;
import tienda.Tienda;
import usuario.Comprador;
import usuario.Vendedor;

public class CartMenu {
     private Comprador comprador;
  private Vendedor vendedor;
  private Tienda tienda;

  //constructor

  public CartMenu(Comprador comprador, Vendedor vendedor, Tienda tienda) {
    this.setComprador(comprador);
    this.setVendedor(vendedor);
    this.setTienda(tienda);
  }

  public void display() {
    Scanner scanner = new Scanner(System.in);
    int opcion;
    
    


    do {

      System.out.println("===== MENÚ CARRITO =====");
      System.out.println("1. Agregar productos/ver Inventario");
      System.out.println("2. Eliminar productos del carrito");
      System.out.println("3. Regresar");
      System.out.println("Seleccione una opción: ");
      opcion = scanner.nextInt();
      

      switch (opcion) {

      case 1:

         // Llamada a lógica para mostrar el catálogo
                System.out.println("===== CATÁLOGO =====");

                //Se guarda la matriz de productos en la variable catálogo
                Object[][] catalogo = tienda.getInventario().mostrarProductos();


                //Se recorre la matriz para mostrar los productos uno por uno
                //Examina si lo que hay en el índice dado es un objeto producto para
                //utilizar el método getNombre()
                for (int fila = 0; fila < catalogo.length; fila++) {
                  for (int columna = 0; columna < catalogo[fila].length; columna++) {
                    if (columna == 6) {
                      if (catalogo[fila][columna] instanceof Producto) {
                        System.out.println(((Producto) catalogo[fila][columna]).getNombre());
                      }else {
                        System.out.println(catalogo[fila][columna]);
                      }

                    } else {
                      if (catalogo[fila][columna] instanceof Producto) {
                        System.out.print(((Producto) catalogo[fila][columna]).getNombre());
                        System.out.print(" ");
                      }else {
                        System.out.print(catalogo[fila][columna]);
                          System.out.print(" ");
                      }

                    }
                  }
                }

                //Se le pregunta al usuario si desea ver recomendaciones en el catálogo
                //a partir de la segunda compra para poder acceder a su historial
					      if (comprador.getHistorialCompras().getFacturas().size() != 0){

						      System.out.println("Desea actualizar las recomendaciones? /n 1. Sí /n 2. No");
                  int respuesta = scanner.nextInt();

                  switch(respuesta){
                    case 1:
  
                      //Llamada a lógica para mostrar recomendaciones
                      //Falta implementar (Simón)

                      //Menú de selección de productos
                      boolean recomendaciones1 = true;
                      new ProductSelectionProcess(comprador, vendedor, tienda, catalogo, recomendaciones1).display();
                      break;

                    case 2:
                      
                      //lógica para ver sin recomendaciones (a partir de la segunda compra)

                      //Solo se mostrará la opción de calificar recomendaciones si para la selección
                      //actual se eligió la opción de mostrar recomendaciones en primer lugar         

                      //Menú de selección de productos
                      boolean recomendaciones2 = false;
                      new ProductSelectionProcess(comprador, vendedor, tienda, catalogo, recomendaciones2).display();
                      break;
                  }

					      } else {

                  //lógica para la primera compra

                  //Menú de selección de productos
                  
                  boolean recomendaciones = false;
                  new ProductSelectionProcess(comprador, vendedor, tienda, catalogo, recomendaciones).display();

                }
              break;

      case 2:
      System.out.println("A continuación te mostraremos tu carrito para que elijas qué quieres eliminar");
      String a = this.comprador.getCarritoCompras().toString();
      System.err.println(a);
      
      // Solicitar el nombre del producto a eliminar
      System.out.println("Por favor, ingresa el nombre del producto a eliminar:");
      
      String eliminar = scanner.next(); // Leer el nombre del producto
      
      // Solicitar la cantidad de productos a eliminar
      System.out.println("Ahora ingresa la cantidad:");
      int count = 0;
      while (true) {
          if (scanner.hasNextInt()) {
              count = scanner.nextInt();
              scanner.nextLine(); // Consumir el salto de línea pendiente después de nextInt()
              break; // Salir del bucle si la cantidad es válida
          } else {
              System.out.println("Por favor, ingresa un número válido para la cantidad.");
              scanner.next(); // Limpiar la entrada no válida
          }
      }
      
      // Buscar el producto en el carrito
      Producto producto = this.comprador.getCarritoCompras().busqueda(eliminar);
      
      // Validar si el producto existe
      if (producto == null) {
          System.out.println("El producto '" + eliminar + "' no se encuentra en el carrito.");
      } else {
          // Validar la cantidad
          if (count <= 0) {
              System.out.println("La cantidad debe ser mayor que 0.");
          } else {
              // Eliminar el producto
              String o=this.comprador.getCarritoCompras().eliminarproducto(producto, count);
              System.err.println(o);
              
          }
      }
      
      

        break;

      case 3:
      System.out.println("Volviendo al menú principal...");
        break;
      }

    } while (opcion != 3);
  }

  //getters y setters

  public Comprador getComprador() {
    return comprador;
  }

  public void setComprador(Comprador comprador) {
    this.comprador = comprador;
  }

  public Vendedor getVendedor() {
    return vendedor;
  }

  public void setVendedor(Vendedor vendedor) {
    this.vendedor = vendedor;
  }

  public Tienda getTienda() {
    return tienda;
  }

  public void setTienda(Tienda tienda) {
    this.tienda = tienda;
  }  
}
