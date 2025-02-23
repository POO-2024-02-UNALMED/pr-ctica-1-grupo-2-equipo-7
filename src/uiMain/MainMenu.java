package uiMain;

import baseDatos.Deserializador;
import baseDatos.Serializador;
import compras.CarritoCompras;
import compras.HistorialCompras;
import fabrica.Fabrica;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import pasarelaPago.Transaccion;
import tienda.Inventario;
import tienda.Producto;   
import tienda.Producto.Categoria;
import tienda.Tienda;
import usuario.Comprador;
import usuario.Notificacion;
import usuario.Usuario;
import usuario.Vendedor;

public class MainMenu {
    private Comprador comprador;
    private Vendedor vendedor;
    private Tienda tienda;
    private Inventario inventario;
    private Notificacion notificacion;
    private Fabrica fabrica;
    private Object[][] catalogo;
    private boolean recomendaciones;
    private String fila;
    private String columna;
    private Producto productoSeleccionado;
    private String[] filas = {"1", "2", "3", "4", "5", "6"};
	private String[] columnas = {"A", "B", "C", "D", "E", "F"};

    public MainMenu(Comprador comprador, Vendedor vendedor, Tienda tienda, Inventario inventario, Notificacion notificacion){
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.tienda = tienda;
        this.inventario = inventario;
        this.notificacion = notificacion;
        this.catalogo = null;
        this.recomendaciones = false;
        this.fila = null;
        this.columna = null;
    }

    public MainMenu(){
      Deserializador.deserealizar(this);
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println();
            System.out.println("===== MENÚ PRINCIPAL =====");
            System.out.println("1. Menú Comprador");
            System.out.println("2. Menú Vendedor");
            System.out.println("3. Salir\n");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    buyerMenuDisplay();
                    break;
                case 2:
                    sellerMenuDisplay();
                    break;
                case 3:
                    Serializador.serializar(this);
                    Serializador.serializarInventarioStatic();
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    System.out.println();
                    break;
                default:
                    System.out.println();
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 3);
    }

   

    public void buyerMenuDisplay(){
      Scanner scanner = new Scanner(System.in);
      int opcion;

      do {
          System.out.println();
          System.out.println("===== MENÚ COMPRADOR =====");
          System.out.println("1. Gestionar Carrito/Ver Catálogo");
          System.out.println("2. Consultar cuenta bancaria");
          System.out.println("3. Realizar Devolución");
          System.out.println("4. Realizar Compra");
          System.out.println("5. Gestionar cupones");
          System.out.println("6. Ver historial de compras");
          System.out.println("7. Ver Notificaciones");
          System.out.println("8. Volver al Menú Principal\n");
          System.out.print("Seleccione una opción: ");
          opcion = scanner.nextInt();

          switch (opcion) {
              case 1: //Menu para acceder al carrito y al catálogo
                  System.out.println();
                  cartMenuDisplay();
                  break;
              case 2: // Consultar cuenta bancaria
                  System.out.println();
                  System.out.println("========= CUENTA BANCARIA =========");
                  System.out.println("¿Que deseas hacer con tu cuenta bancaria?");
                  System.out.println("1. Recargar cuenta");
                  System.out.println("2. Consultar saldo");
                  System.out.println("3. Volver al menú comprador\n");
                  System.out.print("Seleccione una opción: ");
                  int opcionCuenta = scanner.nextInt();
                  if (opcionCuenta == 1){
                      System.out.print("Ingrese el monto a recargar: ");
                      long monto = scanner.nextLong();
                      comprador.getCuentaBancaria().recargarCuenta(monto);
                      System.out.println("Recarga exitosa. Su saldo actual es de: " + comprador.getCuentaBancaria().getSaldo());
                  }
                  else if (opcionCuenta == 2){
                      System.out.println(comprador.consultarCuentaBancaria());
                  }else if (opcionCuenta == 3){
                      System.out.println("Volviendo al Menú Comprador...");
                  }else {
                      System.out.println("Opción no válida. Intente nuevamente.");
                  }
                  break;
              case 3: // Menú de devoluciones
                  System.out.println();
                  returnMenuDisplay();
                  break;
              case 4:

              System.out.println();
                  if (comprador.getCarritoCompras().getListaItems().size() == 0){ // Se verifica si el carrito de compras no esta vacío
                      System.out.println("Error. Su carrito de compras está vacío, por favor gestione su carrito de compras.\n");
                  }
                  else if (verificacionCompra() == false){ // Se verifica si el usuario cuenta con saldo suficiente para realizar la compra.
                      System.out.println("Error. No cuentas con el saldo suficiente para hacer la compra.\n");
                  }
                  else{
                      comprador.getCarritoCompras().calcularTotal(); // Se calcula el valor total de la compra
                      buyProcessDisplay(); // Se realiza el proceso de compra
                  }
                  break;
              case 5:
                  System.out.println();
                  voucherMenuDisplay();
                  break;
              case 6:
                  System.out.println();

                  if (comprador.getHistorialCompras().getFacturas().size() == 0){ // Se verifica si el usuario no ha realizado compras
                      System.out.println("Usted no ha realizado compras hasta el momento.\n");
                  }
                  else{
                      System.out.println("Historial de compras:\n");
                      System.out.println(comprador.mostrarHistorialCompras());
                  }

                  
                  break;
              case 7:
                  System.out.println();
                  if (comprador.getNotificaciones().size() == 0){
                      System.out.println("Usted no tiene notificaciones..."); // Se verifica si el usuario cuenta con notificaciones.
                  }
                  else{
                      System.out.println(comprador.mostrarNotificaciones() + "\n"); // Si es así entonces se muestran las notificaciones.
                  }
                  break;
              case 8:
                  System.out.println("Volviendo al Menú Principal...");
                  break;
              default:
                  System.out.println();
                  System.out.println("Opción no válida. Intente nuevamente.");
          }
      } while (opcion != 8);
  }

  public void cartMenuDisplay(){
    Scanner scanner = new Scanner(System.in);
    int opcion;


    do {

    System.out.println("===== MENÚ CARRITO =====");
    System.out.println("1. Agregar productos/ver Catálogo");
    System.out.println("2. Eliminar productos del carrito");
    System.out.println("3. Ver el carrito");
    System.out.println("4. Regresar");
    System.out.print("Seleccione una opción: ");
    opcion = scanner.nextInt();
    

    switch (opcion) {

    case 1:
    //El método se encarga de mostrar por pantalla los productos, pero es necesario que
    //retorne la matriz donde esto sucede para la debida creación del menú de selección
    if (comprador.getHistorialCompras().getFacturas().size() == 0){
        Object[][] catalogo = mostrarCatalogo(null, false);  
        //lógica para la primera compra

                //Menú de selección de productos
                
                recomendaciones = false;
                productSelectionProcess();
    } else{

            //Se le pregunta al usuario si desea ver recomendaciones en el catálogo
            //a partir de la segunda compra para poder acceder a su historial

                            System.out.println("\nDesea actualizar las recomendaciones? 1. Sí - 2. No");
                int respuesta = scanner.nextInt();

                switch(respuesta){
                case 1:

                    //Llamada a lógica para mostrar recomendaciones
                    catalogo = mostrarCatalogo(comprador.getHistorialCompras(), false);

                    //Menú de selección de productos
                    recomendaciones = true;
                    productSelectionProcess();
                    break;

                case 2:
                    
                    //lógica para ver sin recomendaciones (a partir de la segunda compra)

                    //Solo se mostrará la opción de calificar recomendaciones si para la selección
                    //actual se eligió la opción de mostrar recomendaciones en primer lugar 
                    mostrarCatalogo(null, false);        

                    //Menú de selección de productos
                    recomendaciones = false;
                    productSelectionProcess();
                    break;
                }

                        

                

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
    System.out.println(this.comprador.getCarritoCompras());
    
    case 4:
    System.out.println("Volviendo al menú del carrito...");
    break;
    }
      

    } while (opcion != 4);
  }


  public boolean verificacionCompra(){
      comprador.getCarritoCompras().calcularTotal();
      Transaccion testsTransaccion = new Transaccion(comprador, null, null);
      boolean test = testsTransaccion.verificarSaldo();
      return test;
    }

    // Proceso de reembolso
    public void returnMenuDisplay(){
        Usuario comprador = this.comprador;
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== MENÚ DE DEVOLUCIONES =====");

        System.out.print("¿Conoce el ID de la factura y del producto a devolver? (S/N): ");
        Boolean conoceIdFactura = scanner.nextLine().trim().toUpperCase().equals("S") ? true : false;

        System.out.println();

        if (!conoceIdFactura) {
            System.out.println("Por favor, consiga la información necesaria en el numeral 6 del MENÚ COMPRADOR.\n");
            return; 
        }

        System.out.print("Ingrese el ID de la factura donde se encuentra el producto: ");
        int idFactura = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese el ID del producto a devolver: ");
        int idProducto = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese la cantidad a devolver: ");
        int cantidadRetornar = Integer.parseInt(scanner.nextLine());
        System.out.println();

        String resultado = comprador.devolverProducto(idFactura, idProducto, cantidadRetornar, vendedor); //Proceso de reembolso en si

        switch (resultado) {
            case "FacturaInvalida":
                System.out.println("La factura ingresada no fue encontrada.\n");
                break;
            case "ProductoInvalido":
                System.out.println("El producto ingresado no cumple con los requisitos para devolución.\n" +
                                    "Razones posibles:\n" +
                                    "- No se encontró en la factura.\n" +
                                    "- No es un producto retornable.\n" +
                                    "- La cantidad especificada para devolver no es válida.");
                break;
            default:
            System.out.println("La devolución se ha procesado correctamente, en sus notificaciones encontrará más información.\n");
        }
    }

    // Esto es para realizar la compra
    public void buyProcessDisplay(){
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("================");
            System.out.println("¿Desea usted aplicar un cupón de descuento en su compra?");
            System.out.println("1. Si");
            System.out.println("2. No");
            System.out.println("3. Regresar al menú del comprador");
            System.out.print("\nSeleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    if (comprador.getValorCupones().size() == 0){ // Se comprueba si el usuario dispone de cupones.
                        System.out.println("\nLo sentimos pero usted no dispone de ningún cupón. Intente realizar la compra nuevamente sin intentar aplicar cupón de descuento.");
                        opcion = 2; // Forzar salida del ciclo después del pago
                        break;
                    }
                    else{
                        System.out.println("\nActualmente usted cuenta con " + comprador.getValorCupones().size() + " cupones. Estos cupones son los siguientes:");
                        System.out.println(comprador.mostrarCupones()); // Se muestra por pantalla y en orden los cupones disponibles.

                        System.out.println("\nPor favor seleccione el cupón que usted desea utilizar:");
                        int cupon;
                        System.out.print("Seleccione una opción: ");
                        cupon = scanner.nextInt();

                        int precioTotal = comprador.getCarritoCompras().getPrecioTotal(); // Se obtiene el precio total de la compra (sin el descuento generado por el cupón).
                        double descuento = comprador.getValorCupones().get(cupon - 1) / 100.0; // Se calcula el descuento que se le aplicará al precio total de la compra.
                        double precioConDescuento = precioTotal - (precioTotal * descuento); // Se aplica el descuento al precio total de la compra.
                        comprador.getCarritoCompras().setDescuentoAplicadoCompra(comprador.getValorCupones().get(cupon - 1)); // Se guarda el descuento aplicado a la compra en el carrito de compras.

                        System.out.println("\nEl precio total de la compra es de: " + precioTotal + " pesos. Pero con descuento queda en: " + (int)precioConDescuento + " pesos.");

                        comprador.getCarritoCompras().setPrecioTotal((int)precioConDescuento); // Se actualiza el precio total de la compra del carrito de compras con el descuento.
                        comprador.getCarritoCompras().restarProductosAlComprar(); // Se restan la cantidad de productos que el usuario va a comprar del atributo cantidad de cada instancia de Producto en el carrito de compras.
                        System.out.println("Ahora vamos a ver que descuentos obtuviste por fidelidad y mayoreo ");
                        
                        String pmd=comprador.getCarritoCompras().descuentoporproductomenosvendido();
                        if(pmd!= null){
                            System.out.println(comprador.getCarritoCompras().descuentoporproductomenosvendido());
                        }
                        
                       String  verpunt=comprador.getCarritoCompras().verificardescuentopuntos();
                       if(verpunt!= null){
                        System.out.println(verpunt);
                       }
                       String descmember=comprador.getCarritoCompras().descuentomembresia();
                       if(descmember!=null){
                        System.out.println(descmember);
                       }
                       //comprador.getCarritoCompras().calcularTotal();
                       System.out.println("\nEl precio total de la compra ahora es de: " + comprador.getCarritoCompras().getPrecioTotal()+ " pesos. Ahora se prosigue con el pago.");
                        ((Usuario) comprador).pago(comprador, vendedor, (int)precioConDescuento, "compra"); // Se inicia el proceso de pago, ligadura estática.
                        comprador.getValorCupones().remove(cupon - 1); // Se elimina el cupón utilizado.
                        comprador.cantidadCupones -= 1; // Se disminuye la cantidad de cupones en 1.
                        System.out.println("\n\n====== COMPRA ======");
                        System.out.println("Resumen de la compra:");
                        System.out.println(comprador.getHistorialCompras().mostrarFactura(comprador.getHistorialCompras().getFacturas().size())); // Se muestra por pantalla la factura usando el método mostrarFactura y lo que se pasa por parámetro es el ID de la factura. En este caso puntual coincide con el ultimo elemento de la lista que almacena las facturas, es decir el tamaño de la lista.
                        System.out.println("\n¡Muchas gracias por su compra!");
                        if (comprador.cantidadCupones != comprador.getValorCupones().size()){
                            System.out.println("Felicidades. Durante la compra te ganaste un cupón del " + comprador.getValorCupones().get(comprador.cantidadCupones) + "% de descuento para una compra en el futuro.");
                            comprador.cantidadCupones += 1;
                        }
                        System.out.println("Saldo restante en su cuenta: " + comprador.getCuentaBancaria().getSaldo() + "\n");

                        Notificacion notificacion = new Notificacion("Se le informa que su compra por " + comprador.getCarritoCompras().getPrecioTotal() + " pesos ha sido realizada exitosamente.", "¡Compra realizada exitosamente!", comprador);
                        comprador.recibirNotificacion(notificacion); // Se envía una notificación al comprador informando que la compra ha sido realizada exitosamente.

                        Notificacion notificacion2 = new Notificacion("Se le informa que el usuario " + comprador.getNombre() + " ha realizado una compra por " + comprador.getCarritoCompras().getPrecioTotal() + "pesos.", "Se le informa de una nueva compra", vendedor);
                        vendedor.recibirNotificacion(notificacion2);
                        ArrayList<Boolean> cantidadProductos = new ArrayList<>(); // Se crea este ArrayList para almacenar si toca o no enviar una notificación al vendedor.
                        
                        for (Producto producto : comprador.getCarritoCompras().getListaItems()){
                            boolean verificacion = producto.verificarCantidadProductos();
                            cantidadProductos.add(verificacion);
                            producto.setCantidadVendida(producto.getCantidadVendida() + comprador.getCarritoCompras().getCantidadPorProducto(producto));// Se actualiza la cantidad vendida de cada producto en el inventario.
                            
                            // Esto es para solucionar el problema de la funcionalidad de Alejandro al serializarse
                            Inventario.ajusteProductos(producto, "compra");
                        }
    
                        for (int i = 0; i < cantidadProductos.size(); i++){
                            if (cantidadProductos.get(i) == true){ // Se envía notificación al vendedor en caso de que sea true.
                                Notificacion notificacionVendedor = new Notificacion("Se le informa que el producto " + comprador.getCarritoCompras().getListaItems().get(i).getNombre() + " esta por agotarse o se ha agotado en el inventario.", "Producto por agotarse", vendedor);
                                vendedor.recibirNotificacion(notificacionVendedor);
                            }
                        }

                        comprador.setCarritoCompras(new CarritoCompras(comprador)); // Inicializar un nuevo carrito de compras en forma de "vaciar" el carrito de compras para una compra futura.
                        opcion = 3; // Forzar salida del ciclo después del pago
                        break;
                    }
                case 2:
                    comprador.getCarritoCompras().restarProductosAlComprar();
                    int precioTotal = comprador.getCarritoCompras().getPrecioTotal();
                    ((Usuario)comprador).pago(comprador, vendedor, precioTotal, "compra");//Proceso de pago, Ligadura estatica
                    System.out.println("Ahora vamos a ver que descuentos obtuviste por fidelidad y mayoreo ");
                        
                    String pmd=comprador.getCarritoCompras().descuentoporproductomenosvendido();
                    if(pmd!= null){
                        System.out.println(comprador.getCarritoCompras().descuentoporproductomenosvendido());
                    }
                    
                   String  verpunt=comprador.getCarritoCompras().verificardescuentopuntos();
                   if(verpunt!= null){
                    System.out.println(verpunt);
                   }
                   String descmember=comprador.getCarritoCompras().descuentomembresia();
                   if(descmember!=null){
                    System.out.println(descmember);
                   }
                   //comprador.getCarritoCompras().calcularTotal();
                   System.out.println("\nEl precio total de la compra ahora es de: " + comprador.getCarritoCompras().getPrecioTotal()+ " pesos. Ahora se prosigue con el pago.");
                    System.out.println("====== COMPRA ======");
                    System.out.println("Resumen de la compra:");
                    System.out.println(comprador.getHistorialCompras().mostrarFactura(comprador.getHistorialCompras().getFacturas().size()));
                    System.out.println("\n¡Muchas gracias por su compra!");
                    if (comprador.cantidadCupones != comprador.getValorCupones().size()){
                        System.out.println("Felicidades. Durante la compra te ganaste un cupón del " + comprador.getValorCupones().get(comprador.cantidadCupones) + "% de descuento para una compra en el futuro.");
                        comprador.cantidadCupones += 1;
                    }
                    System.out.println("Saldo restante en su cuenta: " + comprador.getCuentaBancaria().getSaldo() + "\n");

                    Notificacion notificacion = new Notificacion("Se le informa que su compra por " + comprador.getCarritoCompras().getPrecioTotal() + " pesos ha sido realizada exitosamente.", "¡Compra realizada exitosamente!", comprador);
                    comprador.recibirNotificacion(notificacion); // Se envía una notificación al comprador informando que la compra ha sido realizada exitosamente.

                    Notificacion notificacion2 = new Notificacion("Se le informa que el usuario " + comprador.getNombre() + " ha realizado una compra por " + comprador.getCarritoCompras().getPrecioTotal() + "pesos.", "Se le informa de una nueva compra", vendedor);
                    vendedor.recibirNotificacion(notificacion2);

                    ArrayList<Boolean> cantidadProductos = new ArrayList<>(); // Se crea este ArrayList para almacenar si toca o no enviar una notificación al vendedor.
                    for (Producto producto : comprador.getCarritoCompras().getListaItems()){
                        boolean verificacion = producto.verificarCantidadProductos();
                        cantidadProductos.add(verificacion);
                        producto.setCantidadVendida(producto.getCantidadVendida() + comprador.getCarritoCompras().getCantidadPorProducto(producto));// Se actualiza la cantidad vendida de cada producto en el inventario.
                        
                            // Esto es para solucionar el problema de la funcionalidad de Alejandro al serializarse
                            Inventario.ajusteProductos(producto, "compra");
                    }

                    for (int i = 0; i < cantidadProductos.size(); i++){
                        if (cantidadProductos.get(i) == true){ // Se envía notificación al vendedor en caso de que sea true.
                            Notificacion notificacionVendedor = new Notificacion("Se le informa que el producto " + comprador.getCarritoCompras().getListaItems().get(i).getNombre() + " esta por agotarse o se ha agotado en el inventario.", "Producto por agotarse", vendedor);
                            vendedor.recibirNotificacion(notificacionVendedor);
                        }
                    }

                    comprador.setCarritoCompras(new CarritoCompras(comprador)); // Inicializar un nuevo carrito de compras en forma de "vaciar" el carrito de compras para una compra futura.
                    opcion = 3; // Forzar la salida del ciclo después del pago
                    break;
                case 3:
                    System.out.println("Regresando al menú del comprador...\n");
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente nuevamente.");
            }
        } while (opcion != 3);
    }

    public void voucherMenuDisplay(){
      Scanner scanner = new Scanner(System.in);
      int opcion;

      do {
          System.out.println("===== MENÚ CUPONES =====");
          System.out.println("Usted actualmente dispone de " + comprador.getValorCupones().size() + " cupones. ¿Que desea hacer?:");
          System.out.println("1. Ver cupones disponibles");
          System.out.println("2. Eliminar cupones");
          System.out.println("3. Regresar al menú del comprador");
          System.out.print("\nSeleccione una opción: ");
          opcion = scanner.nextInt();
          System.out.println();

          switch (opcion) {
              case 1:
                  System.out.println("Cupones disponibles:");
                  if (comprador.getValorCupones().size() == 0){
                      System.out.println("No tiene cupones disponibles.\n");
                  }
                  else{
                      System.out.println(comprador.mostrarCupones());
                  }
                  opcion = 3; // Forzar salida del ciclo después de ver los cupones
                  break;
              case 2:
                  if (comprador.getValorCupones().size() == 0){
                      System.out.println("No tiene cupones disponibles para eliminar.\n");
                  }
                  else{
                      System.out.println("Cupones disponibles:");
                      System.out.println(comprador.mostrarCupones());
                      System.out.print("¿Cuál cupón deseas eliminar?: ");
                      int cuponEliminar = scanner.nextInt();
                      comprador.eliminarCupones(cuponEliminar);
                      comprador.cantidadCupones -= 1;
                      System.out.println("El cupón ha sido eliminado exitosamente.");
                  }
                  opcion = 3; 
                  break; // Forzar salida del ciclo después de ver los cupones
              case 3:
                  System.out.println("Regresando al menú del comprador...");
                  break;
              default:
                  System.out.println("Opción no válida. Intente nuevamente.");
          }
      } while (opcion != 3);
  }

  public Object[][] mostrarCatalogo(HistorialCompras historial, boolean reemplazo){

    //Reemplazo se usa para saber si se mostrará el catálogo luego que el usuario haya calificado
    //alguna recomendación, en cuyo caso no se realizará el proceso de las recomendaciones nuevamente, 
    //sino que se mostrará el catálogo como ha sido guardado hasta entonces, ya que en este caso solo se
    //modifica un elemento

     // Llamada a lógica para mostrar el catálogo
     System.out.println(String.format("%105s", "===== CATÁLOGO ===== \n"));

     //Se guarda la matriz de productos en la variable catálogo

     if (historial == null){
        catalogo = tienda.getInventario().crearCatalogo();
     } else {
        if (reemplazo == false){
        catalogo = tienda.recomendarProductos(comprador);
        }
    }
     


     //Se recorre la matriz para mostrar los productos uno por uno
     //Examina si lo que hay en el índice dado es un objeto producto para
     //utilizar el método getNombre()
     for (int fila = 0; fila < catalogo.length; fila++) {
       for (int columna = 0; columna < catalogo[fila].length; columna++) {
         if (columna == 7) {
           if (catalogo[fila][columna] instanceof Producto) {
             String salida = String.format("%-22s",((Producto) catalogo[fila][columna]).getNombre());
             System.out.println(salida);
            
           }else {
             
               String salida = String.format("%-22s",catalogo[fila][columna]);
               System.out.println(salida);
           
           }

         } else {
           if (columna >= 2){

             if (catalogo[fila][columna] instanceof Producto) {

               String salida = String.format("%-22s",((Producto) catalogo[fila][columna]).getNombre());
               System.out.print(salida);
               System.out.print(" ");
             }else {

               String salida = String.format("%-22s",catalogo[fila][columna]);
               System.out.print(salida);
                 System.out.print(" ");
             }
             

           } else {
             
               System.out.print(catalogo[fila][columna]);
               System.out.print("      ");
             
           }
           

         }
       }
     }

     return catalogo;

  }

  public void productSelectionProcess(){
    Scanner scanner1 = new Scanner(System.in);
		
		
		String llevar="1";
		String opcion;
		
		
		
		
		do {
			
			System.out.println(" \nIngrese las coordenadas del producto que desea \n \n"
								+ "Ingrese primero la fila (número) y luego la columna (letra) \n"
								+ "en la que se encuentra el producto deseado \n");

			System.out.println("Ingrese la fila para continuar o 0 para salir: ");
			opcion = scanner1.nextLine();
			
			

			if (opcion.equals("0")) {
				break;
			}

			if (Arrays.asList(filas).contains(opcion)){
				fila = opcion;


				System.out.println("Ingrese la columna (en mayúscula) para continuar o 0 para salir: ");
				opcion = scanner1.nextLine();
				

				if (opcion.equals("0")) {
					break;
				}

				if (Arrays.asList(columnas).contains(opcion)){
					columna = opcion;
					productoSeleccionado = (Producto) catalogo[Integer.parseInt(fila)][Arrays.asList(columnas).indexOf(columna)+2];

					

					System.out.println("Producto seleccionado: " + productoSeleccionado.getNombre() + "\n");
					


					if (recomendaciones == true){
						
						//A partir de la segunda compra ya se tiene acceso al historial, por lo que
						//Se llama al método sobrecargado de display de ProductSelectionMenu
						//Que permite calificar los productos recomendados

						boolean retorno = productSelectionMenu(comprador.getHistorialCompras());

												
						if (retorno == false){
							break;
						} else {
							mostrarCatalogo(comprador.getHistorialCompras(), false);
							continue;
						}

					} else {

						//En la primera compra no hay historial, por lo que no se pueden hacer recomendaciones

						//Este display retorna un valor booleano dependiendo de la opción que se escoja,
						//Esto con el fin de saber si se debe volver al menú de selección de productos o
						//Al menú del carrito directamente
						boolean retorno = productSelectionMenu();

						if (retorno == false){
							break;
						} else {
							
							mostrarCatalogo(null, false);
							continue;
						}
						
					}

		
						} else {
							System.out.println("Columna inválida, intente de nuevo");
							continue;
					}

				} else {
					System.out.println("Fila inválida, intente de nuevo");
					continue;
				}

			} while (opcion != "0");

  }

  public boolean productSelectionMenu(){

    //Si retorna true, se devuelve al menú de selección de productos 
		//Si no, vuelve al menú del carrito

        Scanner scanner = new Scanner(System.in);
        Scanner cantidad=new Scanner(System.in);
        int opcion;
        String llevar;


        do{

        System.out.println("¿Qué desea hacer?");
						System.out.println("1. Agregar al carrito");
						System.out.println("2. Ver información del producto");
						System.out.println("3. Regresar/Seleccionar otro producto");
						System.out.println("Seleccione una opción: ");
					    opcion = scanner.nextInt();

						switch (opcion) {
						case 1:
                            
                            System.out.println("Ingresa la cantidad a llevar (máximo 5): ");
                            llevar=cantidad.nextLine();
                            int numerico=Integer.parseInt(llevar);
                            if (numerico == 1 || numerico == 2 || numerico == 3 || numerico == 4 || numerico == 5){
                                
                            }else{
                                llevar="1";
                                System.out.println("Cantidad inválida , se te asignará una por default que es 1");
                            }
                                if (productoSeleccionado.getCantidad() <= 0){
                                    System.out.println("Error. No hay más productos disponibles.");
                                    continue;
                                }else{
                                    String o = this.comprador.getCarritoCompras().añadirProducto(productoSeleccionado, Integer.parseInt(llevar));
                                    System.out.println(o);
                                    return false;
                                }
						case 2:
                            System.out.println(productoSeleccionado.toStringdif());
							continue;
						case 3:
                            return true; 
						default:
							System.out.println("Opción inválida, intente de nuevo");
							continue;
        }
       
    } while(opcion != 3);

    return false;

  }

  public boolean productSelectionMenu(HistorialCompras historial){

    //Si retorna true, se devuelve al menú de selección de productos 
		//Si no, vuelve al menú del carrito

        Scanner scanner = new Scanner(System.in);
        Scanner cantidad=new Scanner(System.in);
        String opcion;
        String llevar;

        int categoriasARecomendar = 0;

        //Revisa cuántas categorías hay almacenadas en categoriasMascompradas del historial
        //ejemplo: puede que guarde [TECNOLOGIA, null, null] porque solo se han
        //comprado productos de la categoría TECNOLOGIA
        for (int i = 0; i < 3; i++){

            if (historial.getCategoriasMasCompradas()[i] != null){

                categoriasARecomendar ++;
               }
           }

           switch (categoriasARecomendar){

            case 1 :

            do{

                if (fila.equals("1")){
                    System.out.println("¿Qué desea hacer?");
                    System.out.println("1. Agregar al carrito");
                    System.out.println("2. Ver información del producto");
                    System.out.println("3. Seleccionar otro producto");
                    System.out.println("4. Calificar recomendación");
                    System.out.println("Seleccione una opción: ");
                    opcion = scanner.nextLine();
    
                    switch (opcion) {
                    case "1":
    
                    System.out.println("Ingresa la cantidad a llevar (máximo 5): ");
                    llevar=cantidad.nextLine();
                    int numerico=Integer.parseInt(llevar);
                    if (numerico == 1 || numerico == 2 || numerico == 3 || numerico == 4 || numerico == 5){
                        
                    }else{
                        llevar="1";
                        System.out.println("Cantidad inválida , se te asignará una por default que es 1");
                    }
                    if (productoSeleccionado.getCantidad() <= 0){
                        System.out.println("Error. No hay mas productos disponibles.");
                        continue;
                    }
                    else{
                        this.comprador.getCarritoCompras().añadirProducto(productoSeleccionado, Integer.parseInt(llevar));
                        System.out.println("Producto añadido correctamente");
                        return false;
                    }
                    case "2":
                        System.out.println(productoSeleccionado.toStringdif());
                        continue;
                    case "3":
                        return true;
                    case "4":
                        System.out.println("Le parece adecuada esta recomendación? 1. Sí - 2. No");
                        String calificacion = scanner.nextLine();
                        if (calificacion.equals("1")){

                            Producto reemplazo = Inventario.getListaCategorias().get(productoSeleccionado.getCategoria().ordinal()).get(10);
                            ArrayList<Producto> categoriaDeProductoAReemplazar =  Inventario.getListaCategorias().get(reemplazo.getCategoria().ordinal());

                            //Se añade un nuevo producto de la misma categoría al inicio de la fila
                            catalogo[Integer.parseInt(fila)][2] = reemplazo;

                            //Se mueve el reemplazo al final de la lista de su respectiva categoría
                            //para que no se repita en recomendaciones futuras
                            categoriaDeProductoAReemplazar.remove(reemplazo);
                            categoriaDeProductoAReemplazar.add(reemplazo);

                            System.out.println("Gracias por tu calificación! Se ha actualizado el catálogo con una nueva recomendación.");
                            System.out.println("al inicio de la fila\n");
                            System.out.println("Producto reemplazado en fila: " + fila + " columna: " +  "A\n");
    
                            mostrarCatalogo(historial, true);
                            productSelectionProcess();
                            return false;
                        
                        } else if (calificacion.equals("2")){

                            Random random = new Random();

                            //Se escoge de manera aleatoria una categoría diferente a la del producto seleccionado
                            int categoriaNoDeseada = productoSeleccionado.getCategoria().ordinal();
                            int categoriaDeseada = random.nextInt(6);

                            while (categoriaDeseada == categoriaNoDeseada){
                                categoriaDeseada = random.nextInt(6);
                            }

                            ArrayList<Producto> categoriaDeReemplazo = Inventario.getListaCategorias().get(categoriaDeseada);

                            Producto reemplazo = categoriaDeReemplazo.get(10);
                            
                            //Se mueve el reemplazo al final de la lista de su respectiva categoría
                            //para que no se repita en recomendaciones futuras
                            ArrayList<Producto> categoriaDeProductoAReemplazar =  Inventario.getListaCategorias().get(reemplazo.getCategoria().ordinal());
                            categoriaDeProductoAReemplazar.remove(reemplazo);
                            categoriaDeProductoAReemplazar.add(reemplazo);


                             //Se reemplaza el producto escogido por una nueva recomendación de diferente categoría
                             //entre las otras dos más compradas
                            catalogo[Integer.parseInt(fila)][Arrays.asList(columnas).indexOf(columna)+2] = reemplazo;

                            System.out.println("Gracias por tu calificación! Se ha actualizado el catálogo con una nueva recomendación");
                            System.out.println("de una categoría diferente en la misma posición\n");
                            System.out.println("Producto reemplazado en fila: " + fila + " columna: " +  columna + "\n");
    
                            mostrarCatalogo(historial, true);
                            productSelectionProcess();
                            return false;
                        } else {
                            System.out.println("Opción inválida, intente de nuevo");
                            continue;
                        }

                    default:
                        System.out.println("Opción inválida, intente de nuevo");
                        continue;
                    }
                } else {
                    
                    productSelectionMenu();
                    return false;
                    
                }
    
            } while(opcion != "3");
            
            return false;
            case 2:
            
            do{

                if (fila.equals("1") || fila.equals("2")){
                    System.out.println("¿Qué desea hacer?");
                    System.out.println("1. Agregar al carrito");
                    System.out.println("2. Ver información del producto");
                    System.out.println("3. Seleccionar otro producto");
                    System.out.println("4. Calificar recomendación");
                    System.out.println("Seleccione una opción: ");
                    opcion = scanner.nextLine();
    
                    switch (opcion) {
                    case "1":
    
                    System.out.println("Ingresa la cantidad a llevar (máximo 5): ");
                    llevar=cantidad.nextLine();
                    int numerico=Integer.parseInt(llevar);
                    if (numerico == 1 || numerico == 2 || numerico == 3 || numerico == 4 || numerico == 5){
                        
                    }else{
                        llevar="1";
                        System.out.println("Cantidad inválida , se te asignará una por default que es 1");
                    }
                        this.comprador.getCarritoCompras().añadirProducto(productoSeleccionado, Integer.parseInt(llevar));
                        System.out.println("Producto añadido correctamente");
                        return false;
                    case "2":
                        System.out.println(productoSeleccionado.toStringdif());
                        continue;
                    case "3":
                        return true;
                    case "4":
                        System.out.println("Le parece adecuada esta recomendación? 1. Sí - 2. No");
                        String calificacion = scanner.nextLine();
                        if (calificacion.equals("1")){

                            Producto reemplazo = Inventario.getListaCategorias().get(productoSeleccionado.getCategoria().ordinal()).get(10);
                            ArrayList<Producto> categoriaDeProductoAReemplazar =  Inventario.getListaCategorias().get(reemplazo.getCategoria().ordinal());

                            //Se añade un nuevo producto de la misma categoría al inicio de la fila
                            catalogo[Integer.parseInt(fila)][2] = reemplazo;

                            //Se mueve el reemplazo al final de la lista de su respectiva categoría
                            //para que no se repita en recomendaciones futuras
                            categoriaDeProductoAReemplazar.remove(reemplazo);
                            categoriaDeProductoAReemplazar.add(reemplazo);

                            System.out.println("Gracias por tu calificación! Se ha actualizado el catálogo con una nueva recomendación.");
                            System.out.println("al inicio de la fila\n");
                            System.out.println("Producto reemplazado en fila: " + fila + " columna: " +  "A\n");
    
                            mostrarCatalogo(historial, true);
                            productSelectionProcess();
                            return false;
                        
                        } else if (calificacion.equals("2")){

                            //Se consiguien las dos categorías más compradas
                            Categoria[] categoriasMasCompradas = historial.getCategoriasMasCompradas();

                            Categoria categoriaDeReemplazo;


                            //El producto será reemplazado por uno de la otra categoría más comprada
                            if (fila.equals("1")){
                                categoriaDeReemplazo = categoriasMasCompradas[1];
                            } else {
                                categoriaDeReemplazo = categoriasMasCompradas[0];
                            }

                            Producto reemplazo = Inventario.getListaCategorias().get(categoriaDeReemplazo.ordinal()).get(10);
                            
                            //Se mueve el reemplazo al final de la lista de su respectiva categoría
                            //para que no se repita en recomendaciones futuras
                            ArrayList<Producto> categoriaDeProductoAReemplazar =  Inventario.getListaCategorias().get(reemplazo.getCategoria().ordinal());
                            categoriaDeProductoAReemplazar.remove(reemplazo);
                            categoriaDeProductoAReemplazar.add(reemplazo);


                             //Se reemplaza el producto escogido por una nueva recomendación de diferente categoría
                             //entre las otras dos más compradas
                            catalogo[Integer.parseInt(fila)][Arrays.asList(columnas).indexOf(columna)+2] = reemplazo;

                            System.out.println("Gracias por tu calificación! Se ha actualizado el catálogo con una nueva recomendación");
                            System.out.println("de una categoría diferente en la misma posición\n");
                            System.out.println("Producto reemplazado en fila: " + fila + " columna: " +  columna + "\n");
    
                            mostrarCatalogo(historial, true);
                            productSelectionProcess();
                            return false;
                        } else {
                            System.out.println("Opción inválida, intente de nuevo");
                            continue;
                        }

                    default:
                        System.out.println("Opción inválida, intente de nuevo");
                        continue;
                    }
                } else {
                    
                    productSelectionMenu();
                    return false;
                    
                }
    
            } while(opcion != "3");


            return false;
            case 3:

            do{

                if (fila.equals("1") || fila.equals("2") || fila.equals("3")){
                    System.out.println("¿Qué desea hacer?");
                    System.out.println("1. Agregar al carrito");
                    System.out.println("2. Ver información del producto");
                    System.out.println("3. Seleccionar otro producto");
                    System.out.println("4. Calificar recomendación");
                    System.out.println("Seleccione una opción: ");
                    opcion = scanner.nextLine();
    
                    switch (opcion) {
                    case "1":
    
                    System.out.println("Ingresa la cantidad a llevar (máximo 5): ");
                    llevar=cantidad.nextLine();
                    int numerico=Integer.parseInt(llevar);
                    if (numerico == 1 || numerico == 2 || numerico == 3 || numerico == 4 || numerico == 5){
                        
                    }else{
                        llevar="1";
                        System.out.println("Cantidad inválida , se te asignará una por default que es 1");
                    }
                        this.comprador.getCarritoCompras().añadirProducto(productoSeleccionado, Integer.parseInt(llevar));
                        System.out.println("Producto añadido correctamente");
                        return false;
                    case "2":
                        System.out.println(productoSeleccionado.toStringdif());
                        continue;
                    case "3":
                        return true;
                    case "4":
                        System.out.println("Le parece adecuada esta recomendación? 1. Sí - 2. No");
                        String calificacion = scanner.nextLine();
                        if (calificacion.equals("1")){

                            Producto reemplazo = Inventario.getListaCategorias().get(productoSeleccionado.getCategoria().ordinal()).get(10);
                            ArrayList<Producto> categoriaDeProductoAReemplazar =  Inventario.getListaCategorias().get(reemplazo.getCategoria().ordinal());

                            //Se añade un nuevo producto de la misma categoría al inicio de la fila
                            catalogo[Integer.parseInt(fila)][2] = reemplazo;

                            //Se mueve el reemplazo al final de la lista de su respectiva categoría
                            //para que no se repita en recomendaciones futuras
                            categoriaDeProductoAReemplazar.remove(reemplazo);
                            categoriaDeProductoAReemplazar.add(reemplazo);

                            System.out.println("Gracias por tu calificación! Se ha actualizado el catálogo con una nueva recomendación.");
                            System.out.println("al inicio de la fila\n");
                            System.out.println("Producto reemplazado en fila: " + fila + " columna: " +  "A\n");
    
                            mostrarCatalogo(historial, true);
                            productSelectionProcess();
                            return false;
                        
                        } else if (calificacion.equals("2")){

                            //Se crea una lista con las otras dos categorías más compradas
                            //para reemplazar este objeto por uno de alguna de estas
                            ArrayList<Categoria> categoriaAEscoger = new ArrayList<>();
                            for (int i = 0; i < 3; i++){
                                categoriaAEscoger.add(historial.getCategoriasMasCompradas()[i]);
                             } 
                             categoriaAEscoger.remove(productoSeleccionado.getCategoria());

                             //Se decide aleatoriamente de cuál de las otras dos categorías será el producto reemplazo
                             Random random = new Random();
                             Producto reemplazo = Inventario.getListaCategorias().get(categoriaAEscoger.get(random.nextInt(2)).ordinal()).get(10);

                             //Se mueve el reemplazo al final de la lista de su respectiva categoría
                            //para que no se repita en recomendaciones futuras
                            ArrayList<Producto> categoriaDeProductoAReemplazar =  Inventario.getListaCategorias().get(reemplazo.getCategoria().ordinal());
                            categoriaDeProductoAReemplazar.remove(reemplazo);
                            categoriaDeProductoAReemplazar.add(reemplazo);


                             //Se reemplaza el producto escogido por una nueva recomendación de diferente categoría
                             //entre las otras dos más compradas
                            catalogo[Integer.parseInt(fila)][Arrays.asList(columnas).indexOf(columna)+2] = reemplazo;

                            System.out.println("Gracias por tu calificación! Se ha actualizado el catálogo con una nueva recomendación");
                            System.out.println("de una categoría diferente en la misma posición\n");
                            System.out.println("Producto reemplazado en fila: " + fila + " columna: " +  columna + "\n");
    
                            mostrarCatalogo(historial, true);
                            productSelectionProcess();
                            return false;
                        } else {
                            System.out.println("Opción inválida, intente de nuevo");
                            continue;
                        }

                    default:
                        System.out.println("Opción inválida, intente de nuevo");
                        continue;
                    }
                } else {
                    
                    productSelectionMenu();
                    return false;
                    
                }
    
            } while(opcion != "3");

           }


        

        return false;


  }

  public void sellerMenuDisplay(){
    Scanner scanner = new Scanner(System.in);
    int opcion;

    do {
        System.out.println();
        System.out.println("===== MENÚ VENDEDOR =====");
        System.out.println("1. Generar reporte de ventas");
        System.out.println("2. Consultar cuenta bancaria");
        System.out.println("3. Ver notificaciones");
        System.out.println("4. Volver al Menú Principal\n");
        System.out.print("Seleccione una opción: ");
        opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea pendiente

        switch (opcion) {
            case 1:
                System.out.println();
                System.out.println(inventario.generarReporte());
                System.out.println("A continucacion elija los productos que quiere crear en la fabrica para reponer en el inventario.\n- Primero elija el producto y posteriormente la cantidad, puede elegir otro producto y repite el proceso.\n- Asegurese que este correcto el nombre y la cantidad de cada producto ya que si se equivoca tiene que ingresar todo de nuevo.\n- Seleccione un maximo de 50 unidades por orden.\nEscriba fin para terminar la orden y enviarla ó para salir");

                String resultado;
                do {
                    resultado = vendedor.crearOrdenFabricacion();
                    System.out.println(resultado); // Imprime siempre el mensaje, ya sea de éxito o error
                } while (!resultado.startsWith("Orden creada con éxito.") && !resultado.equals("No se seleccionaron productos. La orden no se creó."));
                
                if (!vendedor.getOrdenesPendientes().isEmpty()) {
                    String mensajeFabricaNotif = "Se han entregado los productos " ;
                    String asuntoVendedor = "Orden De produccion";
                    vendedor.recibirNotificacion(new Notificacion(mensajeFabricaNotif, asuntoVendedor, vendedor));
                    vendedor.getOrdenesPendientes().removeAll(vendedor.getOrdenesPendientes());
                }
                break;
            case 2:
                System.out.println();
                System.out.println("========= CUENTA BANCARIA =========");
                System.out.println(vendedor.consultarCuentaBancaria()); 
                break;
            case 3:
            System.out.println();
            if (vendedor.getNotificaciones().size() == 0){
                System.out.println("Usted no tiene notificaciones..."); // Se verifica si el usuario cuenta con notificaciones.
            }
            else{
                System.out.println();
                System.out.println(vendedor.mostrarNotificaciones() + "\n"); // Si es así entonces se muestran las notificaciones.
            }
                break;
            case 4:
                System.out.println();
                System.out.println("Volviendo al Menú     Principal...");
            default:
                System.out.println();
                System.out.println("Opción no válida. Intente nuevamente.");
            }
                break;
    } while (opcion != 4);
}

//getters y setters

public Comprador getComprador() {
    return this.comprador;
  }
  public void setComprador(Comprador value) {
    this.comprador = value;
  }

  public Vendedor getVendedor() {
    return this.vendedor;
  }
  public void setVendedor(Vendedor value) {
    this.vendedor = value;
  }

  public Tienda getTienda() {
    return this.tienda;
  }
  public void setTienda(Tienda value) {
    this.tienda = value;
  }

  public Inventario getInventario() {
    return this.inventario;
  }
  public void setInventario(Inventario value) {
    this.inventario = value;
  }

  public Notificacion getNotificacion() {
    return this.notificacion;
  }
  public void setNotificacion(Notificacion value) {
    this.notificacion = value;
  }


}




