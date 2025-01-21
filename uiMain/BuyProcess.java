package uiMain;

import compras.CarritoCompras;
import java.util.ArrayList;
import java.util.Scanner;
import pasarelaPago.Transaccion;
import tienda.Producto;
import usuario.Comprador;
import usuario.Notificacion;
import usuario.Vendedor;

public class BuyProcess {
    private Comprador comprador;
    private Vendedor vendedor;

    public BuyProcess(Comprador comprador, Vendedor vendedor){
        this.comprador = comprador;
        this.vendedor = vendedor;
    }

    public boolean verificacionCompra(){
        comprador.getCarritoCompras().calcularTotal();
        Transaccion testsTransaccion = new Transaccion(comprador, null, null);
        boolean test = testsTransaccion.verificarSaldo();
        return test;
    }

    public void display(){
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

                        System.out.println("\nEl precio total de la compra es de: " + precioTotal + " pesos. Pero con descuento queda en: " + (int)precioConDescuento + " pesos. Ahora se prosigue con el pago.");

                        comprador.getCarritoCompras().setPrecioTotal((int)precioConDescuento); // Se actualiza el precio total de la compra del carrito de compras con el descuento.
                        comprador.getCarritoCompras().restarProductosAlComprar(); // Se restan la cantidad de productos que el usuario va a comprar del atributo cantidad de cada instancia de Producto en el carrito de compras.
                        
                        comprador.pago(comprador, vendedor, (int)precioConDescuento, "compra"); // Se inicia el proceso de pago.
                        comprador.getValorCupones().remove(cupon - 1); // Se elimina el cupón utilizado.
                        comprador.cantidadCupones -= 1; // Se disminuye la cantidad de cupones en 1.
                        System.out.println();
                        System.out.println("====== COMPRA ======");
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

                        ArrayList<Boolean> cantidadProductos = new ArrayList<>(); // Se crea este ArrayList para almacenar si toca o no enviar una notificación al vendedor.
                        for (Producto producto : comprador.getCarritoCompras().getListaItems()){
                            boolean verificacion = producto.verificarCantidadProductos();
                            cantidadProductos.add(verificacion);
                            producto.setCantidadVendida(producto.getCantidadVendida() + comprador.getCarritoCompras().getCantidadPorProducto(producto));// Se actualiza la cantidad vendida de cada producto en el inventario.
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
                    comprador.pago(comprador, vendedor, precioTotal, "compra");
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

                    ArrayList<Boolean> cantidadProductos = new ArrayList<>(); // Se crea este ArrayList para almacenar si toca o no enviar una notificación al vendedor.
                    for (Producto producto : comprador.getCarritoCompras().getListaItems()){
                        boolean verificacion = producto.verificarCantidadProductos();
                        cantidadProductos.add(verificacion);
                        producto.setCantidadVendida(producto.getCantidadVendida() + comprador.getCarritoCompras().getCantidadPorProducto(producto));// Se actualiza la cantidad vendida de cada producto en el inventario.
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
    }
