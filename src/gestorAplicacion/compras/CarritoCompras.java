package compras;

import java.io.Serializable;
import java.util.ArrayList;
import tienda.Inventario;
import tienda.Producto;
import usuario.Usuario;

public class CarritoCompras implements Serializable{

	private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
    private Usuario usuario; // atributo usado con el fin de contener al propietario
	private ArrayList<Producto> listaItems = new ArrayList<>(); // Acá se almacenan los productos que el usuario va a comprar.
	private ArrayList<Integer> cantidadPorProducto = new ArrayList<>(); //Acá se almacena la cantidad de productos que el usuario va a comprar.
    private int precioTotal = 0; 
	private int descuentoAplicadoCompra = 0; // Este atributo es para guardar el descuento que se le aplicó a la compra.
	private double descuentoPorproductos = 0;

	public CarritoCompras() {
	}

	public CarritoCompras(Usuario usuario) {
		this.usuario=usuario;
	}
    // getters y setters
    	public int getPrecioTotal() {
      		return this.precioTotal;
    	}
    public double getDescuentoPorProductos() {
    	return this.descuentoPorproductos;
    }
    public void setDescuentoPorProductos(double descuento) {
    	this.descuentoPorproductos=descuento;
    }
	public int getCantidadPorProducto(Producto producto){
		int indice = this.listaItems.indexOf(producto);
		return this.cantidadPorProducto.get(indice);
	}
        public void setPrecioTotal(int value) {
      		this.precioTotal = value;
    	}
	public ArrayList<Producto> getListaItems() {
      		return this.listaItems;
    	}


    	public ArrayList<Integer> getCantidadPorProductos() {
      		return this.cantidadPorProducto;
    	}


    	public int getDescuentoAplicadoCompra() {
      		return this.descuentoAplicadoCompra;
    	}
	
    	public void setDescuentoAplicadoCompra(int value) {
      		this.descuentoAplicadoCompra = value;
    	}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}

    	public Usuario getUsuario() {
    		return this.usuario;
    	}
	// metodos
	public Producto busqueda(String nombre){
		for(Producto producto : listaItems){
			if (producto.getNombre().equalsIgnoreCase(nombre)){
				return producto;
			}else{

			}
		}
				return null;


	}
    	public void calcularTotal(){
    		this.precioTotal = 0;
    		for(Producto producto : listaItems){
    			this.precioTotal += producto.getPrecio() * getCantidadPorProducto(producto); 

    		}
			if(descuentoAplicadoCompra != 0){
				this.precioTotal -= this.precioTotal * descuentoAplicadoCompra / 100.0;
			}
    	}
	public void restarCantidadPorProducto(Producto producto, int cantidad){
		int indice = this.listaItems.indexOf(producto);
		this.cantidadPorProducto.set(indice, this.cantidadPorProducto.get(indice) - cantidad);
		if(this.cantidadPorProducto.get(indice) == 0){
			this.listaItems.remove(indice);
			this.cantidadPorProducto.remove(indice);
		}
		calcularTotal();
	}


	public Producto buscarProducto(int idProducto){
		for(Producto producto : listaItems){
			if(producto.getID() == idProducto){
				return producto;
			}
		}
		return null;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Carrito de ").append(this.getUsuario().getNombre()).append(":\n\n")
		  .append("Lista de productos:\n");
	
		for (int i = 0; i < this.getListaItems().size(); i++) {
			sb.append(this.getListaItems().get(i))
			  .append("cantidad: ").append(this.cantidadPorProducto.get(i)).append("\n\n");
		}
		calcularTotal();
		sb.append("total: " + this.precioTotal + "\n");
	
		return sb.toString();
	}
	

	public String añadirProducto(Producto producto){
		// POR FAVOR NO MOVER MÁS ESTE MÉTODO QUE YA QUEDA FUNCIONANDO BIEN
		// Si no se le pasa ningún número, se asume que se quiere añadir 1 producto
		boolean estado= Inventario.verificarproducto(producto, 1);//aqui esta la verificacion del stock del inventario de la tienda
		if(estado==true ) {
			if(this.listaItems.contains(producto)){
				int indice = this.listaItems.indexOf(producto);
				int cantidad = this.cantidadPorProducto.get(indice);
				if(cantidad == 0){
					return"Error. La cantidad mínima de productos que se pueden añadir es 1";
					
				}else{
					this.cantidadPorProducto.set(indice, cantidad + 1);
					return "Proceso exitoso";
				}
			}else{
				this.listaItems.add(producto);
				this.cantidadPorProducto.add(1); // Si no se le pasa ningún número, se asume que se quiere añadir 1 producto
			}
		}else {
			return "No hay suficiente producto en stock";
		}
		
		return null;
	}

	public String añadirProducto(Producto producto, int cantidadProductoAñadir){//metodo sobrecargado , en este el usuario si brinda la cantidad de productos que desea añadir
		// POR FAVOR NO MOVER MÁS ESTE MÉTODO QUE YA QUEDA FUNCIONANDO BIEN
		boolean estado= Inventario.verificarproducto(producto,cantidadProductoAñadir);
		if(estado==true ) {
		if (cantidadProductoAñadir > 5){
			return "Error. La cantidad máxima de productos que se pueden añadir son 5";
		}else if(this.listaItems.contains(producto)){
			int indice = this.listaItems.indexOf(producto);
			int cantidad = this.cantidadPorProducto.get(indice);
			if(cantidad + cantidadProductoAñadir > 5){
				return "Error. La cantidad máxima de productos que se pueden añadir son 5";
			
			}else{

				this.cantidadPorProducto.set(indice, cantidad + cantidadProductoAñadir);
				return "proceso exitoso ";
			}
		} else if (!this.listaItems.contains(producto)){
			this.listaItems.add(producto);
			this.cantidadPorProducto.add(cantidadProductoAñadir);
			return "proceso exitoso";
		}
		}else {
			return "No hay suficiente producto en stock";
			
		}
		return null;
		
		
	}

	//NOTA: ESTE MËTODO SOLO SE VA A USAR PARA LA FUNCIONALIDAD DE COMPRA
	public void restarProductosAlComprar(){
		 int contador=0;
		 this.getUsuario().setVacescomprado(1);
		 Usuario usuario = this.getUsuario();
		 int suma=0;//recorremos la lista de las unidades que ha comprado el usuario para empezar a asignarle puntos
		 for (int numero : cantidadPorProducto) {
	            suma += numero;
	        }
		if(suma<=5) {
			
		
		contador+=1;//se le van dando puntos al usuario por compra hecha
		
		
		}else if(suma>5 && suma <= 10) {
			contador+=2;
			
		}else if(suma>10 && suma <= 15) {
			contador+=4;
		}else if(suma>15 && suma <= 20) {
			contador+=5;
		}else if(suma>20 ) {
			contador+=6;
		}
		usuario.setPuntos(contador);
		for (int i = 0; i < listaItems.size(); i++){
           Producto producto = this.listaItems.get(i);
           int cantidadComprada = this.cantidadPorProducto.get(i);
           this.usuario.setVacescomprado(1);
           producto.setCantidad(producto.getCantidad() - cantidadComprada); // Esto es para restar la cantidad de productos comprados al inventario
		}
	}
	//METODOS SOBRECARGADOS PARA ELIMINAR LOS PRODUCTOS DEL CARRITO
		public String eliminarproducto(Producto producto) {//SIN ENTREGAR CANTIDAD
			int indice=this.listaItems.indexOf(producto);
			this.listaItems.remove(indice);
			this.cantidadPorProducto.remove(indice);
			
			ArrayList<Producto> listaItemsdelete = new ArrayList<>();
			listaItemsdelete.add(producto);
			return "proceso exitoso";
		}
		public String eliminarproducto(Producto producto, int cantidad) {//ENTREGANDO CANTIDAD
			int indice =this.listaItems.indexOf(producto);
			int o=this.cantidadPorProducto.get(indice);
			if(this.cantidadPorProducto.get(indice)>cantidad) {
				o=o-cantidad;
				this.cantidadPorProducto.set(indice, o);
				return "La cantidad del producto ahora es " + String.valueOf(o);
			     
			    }else if(o==cantidad) {
				this.cantidadPorProducto.remove(indice);
				this.listaItems.remove(indice);
				return "El producto ha sido eliminado en su totalidad";
				
		}else {
		}	return " ERROR: la cantidad que deseas eliminar es mayor que la disponible ";
		}
		//TODA LA FUNCIONALIDAD DE DESCUENTOS
		public String verificardescuentopuntos() {
			 int  puntos=this.usuario.getPuntos();// primer sistema de descuentos basado en los puntos del usuario, el usuario adquiere puntos segun las unidades que compre
			 double descuento=0.0;
			   if(this.usuario.getPuntos()>5 &&  this.usuario.getPuntos() < 10) {
				   descuento=0.05;
				   this.descuentoAplicadoCompra=(int) (this.descuentoAplicadoCompra+(this.precioTotal*descuento));
				   this.precioTotal=this.precioTotal-this.descuentoAplicadoCompra;
				   this.usuario.setPuntos(0);
				 
				   return "Gracias a tu fidelidad obtuviste un descuento de " + this.descuentoAplicadoCompra + " usando tus puntos";
				  
			   }else if(puntos >=10 && puntos<20) {
				   descuento=0.10;
				   this.descuentoAplicadoCompra=(int) (this.descuentoAplicadoCompra+(this.precioTotal*descuento));
				   this.precioTotal=this.precioTotal-this.descuentoAplicadoCompra;
				   this.usuario.setPuntos(0);
				  
				   return "Gracias a tu fidelidad obtuviste un descuento de " + this.descuentoAplicadoCompra + " usando tus puntos";
				 
				   
			   }else if(puntos>=20 && puntos<30) {
				   descuento=0.15;
				   this.descuentoAplicadoCompra=(int) (this.descuentoAplicadoCompra+(this.precioTotal*descuento));
				   this.precioTotal=this.precioTotal-this.descuentoAplicadoCompra;
				   this.usuario.setPuntos(0);
				 
				   return "Gracias a tu fidelidad obtuviste un descuento de " + this.descuentoAplicadoCompra + " usando tus puntos";
				 
			   }else if(puntos>=30) {
				   descuento=0.20; 
				   this.descuentoAplicadoCompra=(int) (this.descuentoAplicadoCompra+(this.precioTotal*descuento));
				   this.precioTotal=this.precioTotal-this.descuentoAplicadoCompra;
				   this.usuario.setPuntos(0);
				  
				   return "Gracias a tu fidelidad obtuviste un descuento de " + this.descuentoAplicadoCompra + " usando tus puntos";
				  
			   }
			return null;
		}
		public String descuentomembresia() {
			if(this.getUsuario().getMembresia()==null) {//el usuario no posee membresia entonces se le asigna una membresia y el incentivo es mejor que si ya la tuviese 
				if(this.usuario.getVacescomprado()>=3 && this.usuario.getVacescomprado()<6) {
					usuario.setMembresia("Bronce");
					Producto  economico=Inventario.buscarProductoMaseconomico();
					for (Producto producto : this.listaItems) {
						if(producto==economico) {// aqui buscamos el producto mas barato del inventario 
							int descuento = producto.aplicardescuento(producto, 0.05);
							this.descuentoPorproductos+=descuento;
						   
							return "Felicidades, ahora eres miembro bronce, por esto recibes un descuento de " + String.valueOf(descuento) + " en el producto " + producto.getNombre();
							
							
						
						}
						} Producto  economico1=this.buscarProductoMaseconomico();// aqui el mas barato del carrito del cliente
						for (Producto producto : this.listaItems) {
							if(producto==economico1) {
								int descuento = producto.aplicardescuento(producto, 0.05);
								this.descuentoPorproductos+=descuento;
								
								return "Felicidades, ahora eres miembro bronce, por esto recibes un descuento de " + String.valueOf(descuento) + " en el producto " + producto.getNombre();
						
					}
				}
					
				}else if(this.usuario.getVacescomprado()>=6 && this.usuario.getVacescomprado()<12) {
					usuario.setMembresia("Oro");
					Producto  economico=Inventario.buscarProductoMaseconomico();
					String   o= this.añadirProducto(economico, 1);
					for (Producto producto : this.listaItems) {
						if(producto==economico) {// aqui buscamos el producto mas barato del inventario para obsequiarselo
							int descuento = producto.aplicardescuento(producto, 1);
							this.descuentoPorproductos+=1;
							
							return "Felicidades, ahora eres miembro Oro, por esto recibes un obsequio de " +producto.getNombre() + " totalmente gratis";
					
					
					
					
				
						}
					}
				
				}else if(this.usuario.getVacescomprado()>=12) {
					usuario.setMembresia("Platino");
					double descuento=0.07;
					this.descuentoAplicadoCompra=(int) (this.descuentoAplicadoCompra+(this.precioTotal*descuento));
					this.precioTotal=this.precioTotal-this.descuentoAplicadoCompra;
					Producto  economico=Inventario.buscarProductoMaseconomico();
					String   o= this.añadirProducto(economico, 1);
					for (Producto producto : this.listaItems) {
						if(producto==economico) {// aqui buscamos el producto mas barato del inventario para obsequiarselo
							int descuento1 = producto.aplicardescuento(producto, 1);
							this.descuentoPorproductos+=descuento1;
				   
							return "Gracias a tu fidelidad eres un miembro platino y  obtuviste un descuento de " + this.descuentoAplicadoCompra + ", ademas de obtener el siguiente obsequio totalmentre gratis: "+ producto.getNombre();
					
						}
					}
				}
			}else if(usuario.getMembresia() != null) {
				if(usuario.getMembresia()=="Bronce") {
					if(this.listaItems.size()>10) {//el usuario posee membresia y esta llevando al mayoreo
						Producto  economico1=this.buscarProductoMaseconomico();// aqui el mas barato del carrito del cliente
						
						for (Producto producto : this.listaItems) {
							if(producto==economico1) {
								int descuento = producto.aplicardescuento(producto, 0.05);
								this.descuentoPorproductos+=descuento;
							 double retorno =   this.getPrecioTotal()*0.02;
							 usuario.getCuentaBancaria().recargarCuenta(retorno);//EL RETORNO DEL DINERO NO SE RESTA DEL CARRITO, SE LE GIRA AL CLIENTE Y YA 
								return " Por ser un cliente Bronce y llevar una compra mayorista hoy te daremos un descuento de " + String.valueOf(descuento) + "en el producto "+ producto.getNombre() + " y un reembolso del 0.02 para la rentabilidad";
						 
					}
				}
						
			}else if(this.listaItems.size()< 10) {//el usuario solo posee membresia y no lleva al mayoreo
				Producto  economico1=this.buscarProductoMaseconomico();// aqui el mas barato del carrito del cliente
				
				for (Producto producto : this.listaItems) {
					if(producto==economico1) {
						int descuento = producto.aplicardescuento(producto, 0.02);
						this.descuentoPorproductos+=descuento;
					   return "Por ser un cliente Bronce hoy te daremos un descuento de " + String.valueOf(descuento) + "en el producto "+ producto.getNombre();
				
			}

				}
			}
			}else if(usuario.getMembresia()=="Oro") {
				if(this.listaItems.size()>10) {//el usuario posee membresia y esta llevando al mayoreo
					Producto  economico1=this.buscarProductoMaseconomico();// aqui el mas barato del carrito del cliente
					
					for (Producto producto : this.listaItems) {
						if(producto==economico1) {
						 int descuento = producto.aplicardescuento(producto, 0.10);
						 this.descuentoPorproductos+=1;
						 double retorno =   this.getPrecioTotal()*0.04;
						 usuario.getCuentaBancaria().recargarCuenta(retorno);
							return "Por ser un cliente Oro y llevar una compra mayorista hoy te daremos un descuento de " + String.valueOf(descuento) + "en el producto "+ producto.getNombre() + " y un reembolso del 0.04 para la rentabilidad";
						
				}
					}
				}else if(this.listaItems.size()< 10) {//el usuario solo posee membresia y no lleva al mayoreo
					Producto  economico1=this.buscarProductoMaseconomico();
					
					for (Producto producto : this.listaItems) {
						if(producto==economico1) {
							int descuento = producto.aplicardescuento(producto, 0.05);
							this.descuentoPorproductos+=descuento;
						   return "Por ser un cliente Oro hoy te daremos un descuento de " + String.valueOf(descuento) + "en el producto "+ producto.getNombre();
			}
			}
				}
			}else if(usuario.getMembresia()=="Platino") {
				if(this.listaItems.size()>10) {//el usuario posee membresia y esta llevando al mayoreo
					Producto  economico1=this.buscarProductoMaseconomico();
					
					for (Producto producto : this.listaItems) {
						if(producto==economico1) {
						 int descuento = producto.aplicardescuento(producto, 0.15);
						 this.descuentoPorproductos+=descuento;
						 double retorno =   this.getPrecioTotal()*0.067;
						 usuario.getCuentaBancaria().recargarCuenta(retorno);
							return "Por ser un cliente Platino y llevar una compra mayorista hoy te daremos un descuento de " + String.valueOf(descuento) + "en el producto "+ producto.getNombre() + " y un reembolso del 0.067 para la rentabilidad";
						
				}
					}
				
			}else if(this.listaItems.size()< 10) {//el usuario solo posee membresia y no lleva al mayoreo
				Producto  economico1=this.buscarProductoMaseconomico();
				
				for (Producto producto : this.listaItems) {
					if(producto==economico1) {
						int descuento = producto.aplicardescuento(producto, 0.10);
					   this.descuentoPorproductos+=1;
					   return "Por ser un cliente Platino hoy te daremos un descuento de " + String.valueOf(descuento) + "en el producto "+ producto.getNombre();
		}
		}
			}
			}
				
			}
		 return null;
		
	  }
		 public String descuentoporproductomenosvendido() {//si el usuario lleva el producto menos vendido de la tienda le damos un descuento 
			   Producto productoMenossVendido= Inventario.buscarProductoMenosVendido();
			   for (Producto producto : this.listaItems) {
		           if(producto==productoMenossVendido) {
		        	   int descuento=producto.aplicardescuento(producto, 0.10);
		        	   this.descuentoPorproductos=descuento;
		        	   
		        	   return "Por impulso de producto has obtenido un descuento de " + String.valueOf(descuento) + " en el producto " + producto.getNombre();
		        	   
		        	   
		        	   
		        	   
		       }
			   }
			   return null;
		 }
		 public  Producto buscarProductoMaseconomico() {//para buscar el producto mas economico en el carrito del usuario
		       Producto maseconomico = null;




		     
		    
		         for (Producto producto : this.listaItems) {
		               if (maseconomico == null || producto.getPrecio() < maseconomico.getPrecio()) {
		                   maseconomico = producto;
		               }
		           }
				return maseconomico;
		       }
		 
    
}
