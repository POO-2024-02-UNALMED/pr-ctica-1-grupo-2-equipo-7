package compras;

import java.util.ArrayList;
import pasarelaPago.Factura;
import tienda.Producto.Categoria;

public class HistorialCompras {
    
    int cantidadDevueltos;
    private ArrayList<Factura> facturas = new ArrayList<>();
    
    //Estos atributos son definidos para la funcionalidad de
    //recomendaciones personalizadas (Simón) 
    private int cantidadTecnologia;
    private int cantidadAseo;
    private int cantidadComida;		//Estos atributos llevan la cuenta de cuántos
    private int cantidadPapeleria; 	//productos de cada categoría se han compradp
    private int cantidadJugueteria;
    private int cantidadDeportes;
    
    private Categoria[] cantegoriasMasCompradas = new Categoria[3];
    //Este atributo guarda las tres categorías más compradas

    public Factura buscarFactura(int ID){
        for(Factura factura : facturas){
            if(factura.getIDFactura() == ID){
                return factura;
            }
        }
        return null;
    }

    public void agregarFactura(Factura factura){
        facturas.add(factura);
    }

    public void actualizarCantidadDevueltos(int cantidad){
        cantidadDevueltos += cantidad;
    }

    public String mostrarFactura() {
        StringBuilder facturaStr = new StringBuilder();
        for (int factura = 0; factura < facturas.size(); factura++) {
            facturaStr.append(String.format("ID factura: %d\n", facturas.get(factura).getIDFactura()));
            facturaStr.append(String.format("%-20s %-20s %-20s\n", "Producto", "Cantidad", "ID Producto"));
            for (int i = 0; i < facturas.get(factura).getCarritoCompras().getListaItems().size(); i++) {
                facturaStr.append(String.format("%-20s %-20d %-20d\n",
                        facturas.get(factura).getCarritoCompras().getListaItems().get(i).getNombre(),
                        facturas.get(factura).getCarritoCompras().getCantidadPorProductos().get(i),
                        facturas.get(factura).getCarritoCompras().getListaItems().get(i).getID()));
            }
            facturaStr.append("\nPrecio Total de la Compra: " +
                    facturas.get(factura).getCarritoCompras().getPrecioTotal());
        }
        return facturaStr.toString();
    }
    
    public String mostrarFactura(int IDFactura){
        int factura = IDFactura - 1;
        StringBuilder mensaje = new StringBuilder(); // Se crea un StringBuilder para almacenar el mensaje que vayamos creando
        mensaje.append(String.format("%-15s %-15s\n", "Producto", "Cantidad"));
        for (int i = 0; i < facturas.get(factura).getCarritoCompras().getListaItems().size(); i ++){
            mensaje.append(String.format("%-15s %-15d\n", facturas.get(factura).getCarritoCompras().getListaItems().get(i).getNombre(), facturas.get(factura).getCarritoCompras().getCantidadPorProductos().get(i)));
        }
        mensaje.append("\nPrecio Total de la Compra: ")
               .append(facturas.get(factura).getCarritoCompras().getPrecioTotal()) // Se obtiene el precio total de la compra
               .append("\n");
        return mensaje.toString();
    }

	public int getCantidadTecnologia() {
		return cantidadTecnologia;
	}

	public void setCantidadTecnologia(int cantidadTecnologia) {
		this.cantidadTecnologia = cantidadTecnologia;
	}

	public int getCantidadAseo() {
		return cantidadAseo;
	}

	public void setCantidadAseo(int cantidadAseo) {
		this.cantidadAseo = cantidadAseo;
	}

	public int getCantidadComida() {
		return cantidadComida;
	}

	public void setCantidadComida(int cantidadComida) {
		this.cantidadComida = cantidadComida;
	}

	public int getCantidadPapeleria() {
		return cantidadPapeleria;
	}

	public void setCantidadPapeleria(int cantidadPapeleria) {
		this.cantidadPapeleria = cantidadPapeleria;
	}

	public int getCantidadJugueteria() {
		return cantidadJugueteria;
	}

	public void setCantidadJugueteria(int cantidadJugueteria) {
		this.cantidadJugueteria = cantidadJugueteria;
	}

	public int getCantidadDeportes() {
		return cantidadDeportes;
	}

	public void setCantidadDeportes(int cantidadDeportes) {
		this.cantidadDeportes = cantidadDeportes;
	}

    public ArrayList<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(ArrayList<Factura> facturas) {
        this.facturas = facturas;
    }
}
