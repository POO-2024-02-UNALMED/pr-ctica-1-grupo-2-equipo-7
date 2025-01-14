package tienda;

import java.util.ArrayList;
import java.util.List;

import tienda.Producto.Categoria;


public class Inventario {
    private static ArrayList<Producto> categoriaTecnologia;
    private static ArrayList<Producto> categoriaAseo;
    private static ArrayList<Producto> categoriaComida;
    private static ArrayList<Producto> categoriaPapeleria;
    private static ArrayList<Producto> categoriaJuegueteria;
    private static ArrayList<Producto> categoriaDeportes;
    
    //Atributos añadidos para la funcionalidad de recomendaciones (Simón)
    private ArrayList<Producto> productosTotal;
    private Object[][] catalogo;

    public Inventario(ArrayList<Producto> categoriaTecnologia, ArrayList<Producto> categoriaAseo, ArrayList<Producto> categoriaComida, 
                      ArrayList<Producto> categoriaPapeleria, ArrayList<Producto> categoriaJuegueteria, ArrayList<Producto> categoriaDeportes) {
        this.categoriaTecnologia = categoriaTecnologia;
        this.categoriaAseo = categoriaAseo;
        this.categoriaComida = categoriaComida;
        this.categoriaPapeleria = categoriaPapeleria;
        this.categoriaJuegueteria = categoriaJuegueteria;
        this.categoriaDeportes = categoriaDeportes;
        this.productosTotal = new ArrayList<Producto>();
    }
    public ArrayList<Producto> obtenerTodosLosProductos() {
        ArrayList<Producto> todosLosProductos = new ArrayList<>();
        todosLosProductos.addAll(categoriaTecnologia);
        todosLosProductos.addAll(categoriaAseo);
        todosLosProductos.addAll(categoriaComida);
        todosLosProductos.addAll(categoriaPapeleria);
        todosLosProductos.addAll(categoriaJuegueteria);
        todosLosProductos.addAll(categoriaDeportes);
        return todosLosProductos;
    }
    public String generarReporte() {
        StringBuilder reporte = new StringBuilder();

        reporte.append("Reporte de Inventario:\n");
        reporte.append(generarReportePorCategoria("Tecnología", categoriaTecnologia));
        reporte.append(generarReportePorCategoria("Aseo", categoriaAseo));
        reporte.append(generarReportePorCategoria("Comida", categoriaComida));
        reporte.append(generarReportePorCategoria("Papelería", categoriaPapeleria));
        reporte.append(generarReportePorCategoria("Juguetería", categoriaJuegueteria));
        reporte.append(generarReportePorCategoria("Deportes", categoriaDeportes));

        return reporte.toString();
    }
    // Genera un reporte de los productos de una categoría específica.
    // nombreCategoria El nombre de la categoría.
    // categoria La lista de productos de esa categoría.
    // retorna un String con el detalle de los productos de la categoría.
    
    private String generarReportePorCategoria(String nombreCategoria, ArrayList<Producto> categoria) {
        StringBuilder reporte = new StringBuilder();
        reporte.append(nombreCategoria).append(":\n");

        for (Producto producto : categoria) {
            String estado = "No vendido";
            if (producto.getCantidadVendida() > 0) {
                estado = "Vendido: " + producto.getCantidadVendida() + " unidades";
            }
            reporte.append("- ").append(producto.getNombre())
                    .append(" | Estado: ").append(estado)
                    .append(" | Cantidad en stock: ").append(producto.getCantidad())
                    .append("\n");
        }
        return reporte.toString();
    }
    
    
    //Se añade un producto dado a la lista total de productos (productosTotal)
    // y se añade a la lista de su categoría respectiva
    public void añadirProducto(Producto producto) {
    	productosTotal.add(producto);
    	
    	Categoria categoria = producto.getCategoria();
    	
    	switch(categoria) {
    	
    	case TECNOLOGIA:
    		categoriaTecnologia.add(producto);
    		break;
    		
    	case ASEO:
    		categoriaAseo.add(producto);
    		break;
    		
    	case COMIDA:
    		categoriaComida.add(producto);
    		break;
    		
    	case PAPELERIA:
    		categoriaPapeleria.add(producto);
    		break;
    		
    	case JUGUETERIA:
    		categoriaJuegueteria.add(producto);
    		break;
    		
    	case DEPORTES:
    		categoriaDeportes.add(producto);
    		break;
    		
    	}
    		
    }
    
    public Object[][] mostrarProductos(){
    	
    	//Se crea una matriz para que al usuario se le muestren
    	//Los productos a modo de tabla
    	catalogo = new Object[6][7];
    	
    	//Se crean la primera fila y columna, que guardan las coordenadas de los productos
    	catalogo[0][0] = "";
    	
    	for (int i = 1; i <=5; i++) {
    		catalogo[i][0] = i;
    	}
    	
    	for (int i = 1; i <=6; i++) {
    		catalogo[0][i] = (char)('A' + i-1);
    	}
    	
    	// Se añaden los productos al resto de espacios de la matriz
    	
    	int fila = 1;
    	int columna = 1;
    	
    	for (int i = 0; i < 30; i++) {
    		catalogo[fila][columna] = productosTotal.get(i);
    		if (columna != 6) {
    			columna += 1;
    		} else {
    			columna = 1;
    			if (fila != 5) {
    				fila +=1;
    			}
    		}
 
    	}
    	
    	
    	return catalogo;
    }

    public static  Boolean verificarproducto(Producto producto, int unidades) {
    	Categoria Categoria=producto.categoria;
    	String nombre= Categoria.getNombre();
        boolean verificacion = false;

    	if(nombre.equalsIgnoreCase("tecnologia")) {
    		if(categoriaTecnologia.contains(producto) && producto.getCantidad() >= unidades) {
    			verificacion = true;
    		}
    	}else if(nombre.equalsIgnoreCase("aseo")) {
    		if(categoriaAseo.contains(producto) && producto.getCantidad() >= unidades) {
    			verificacion = true;
    		}
    	}else if (nombre.equalsIgnoreCase("comida")) {
    		if (categoriaComida.contains(producto) && producto.cantidad >=unidades) {
    			verificacion = true;
    		}
    	}else if(nombre.equalsIgnoreCase("papeleria")) {
    		if (categoriaPapeleria.contains(producto)&& producto.getCantidad() >= unidades) {
    			verificacion = true;
    		}
    	}else if(nombre.equalsIgnoreCase("juegueteria")) {
    		if (categoriaJuegueteria.contains(producto)&& producto.getCantidad() >= unidades) {
    			verificacion = true;
    		}
    	}else if (nombre.equalsIgnoreCase("deportes")) {
    		if (categoriaDeportes.contains(producto)&&producto.getCantidad() >= unidades) {
    			verificacion = true;
    		}
    	}
		return verificacion;
    	}
    public static Producto buscarProductoMaseconomico() {//busca el producto mas economico que haya disponible
        Producto maseconomico = null;

        // Lista de todas las categorías
        List<List<Producto>> categorias = List.of(
            Inventario.categoriaTecnologia,
            Inventario.categoriaAseo,
            Inventario.categoriaComida,
            Inventario.categoriaPapeleria,
            Inventario.categoriaJuegueteria,
            Inventario.categoriaDeportes
        );

        // Recorrer cada categoría
        for (List<Producto> categoria : categorias) {
            for (Producto producto : categoria) {
                if (maseconomico == null || producto.getPrecio() < maseconomico.getPrecio() && producto.getCantidad()>=1) {
                    maseconomico = producto;
                }
            }
        }

        return maseconomico;
    }
    public static Producto buscarProductoMenosVendido() {//para poder usar el impulso(con impulso nos referimos a la tactica para vender el producto mas economico
        Producto menosVendido = null;

        // Lista de todas las categorías
        List<List<Producto>> categorias = List.of(
            Inventario.categoriaTecnologia,
            Inventario.categoriaAseo,
            Inventario.categoriaComida,
            Inventario.categoriaPapeleria,
            Inventario.categoriaJuegueteria,
            Inventario.categoriaDeportes
        );

        // Recorrer cada categoría
        for (List<Producto> categoria : categorias) {
            for (Producto producto : categoria) {
                if (menosVendido == null || producto.getCantidadVendida() < menosVendido.getCantidadVendida()) {
                    menosVendido = producto;
                }
            }
        }

        return menosVendido;
    }
    
    

    // Getters y Setters

    public ArrayList<Producto> getCategoriaTecnologia() {
        return categoriaTecnologia;
    }

    public void setCategoriaTecnologia(ArrayList<Producto> categoriaTecnologia) {
        this.categoriaTecnologia = categoriaTecnologia;
    }

    public ArrayList<Producto> getCategoriaAseo() {
        return categoriaAseo;
    }

    public void setCategoriaAseo(ArrayList<Producto> categoriaAseo) {
        this.categoriaAseo = categoriaAseo;
    }

    public ArrayList<Producto> getCategoriaComida() {
        return categoriaComida;
    }

    public void setCategoriaComida(ArrayList<Producto> categoriaComida) {
        this.categoriaComida = categoriaComida;
    }

    public ArrayList<Producto> getCategoriaPapeleria() {
        return categoriaPapeleria;
    }

    public void setCategoriaPapeleria(ArrayList<Producto> categoriaPapeleria) {
        this.categoriaPapeleria = categoriaPapeleria;
    }

    public ArrayList<Producto> getCategoriaJuegueteria() {
        return categoriaJuegueteria;
    }

    public void setCategoriaJuegueteria(ArrayList<Producto> categoriaJuegueteria) {
        this.categoriaJuegueteria = categoriaJuegueteria;
    }

    public ArrayList<Producto> getCategoriaDeportes() {
        return categoriaDeportes;
    }

    public void setCategoriaDeportes(ArrayList<Producto> categoriaDeportes) {
        this.categoriaDeportes = categoriaDeportes;
    }
    

    // metodos
    
    public void reabastecerProductos(int cantidad, Producto producto) {
        producto.reabastecerCantidad(cantidad);
    }
     
  

    public ArrayList<Producto> getProductosTotal(){
    	return productosTotal;
    }
    
    public void setProductosTotal(ArrayList<Producto> productos) {
     productosTotal = productos;
    }

	public Object[][] getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(Object[][] catalogo) {
		this.catalogo = catalogo;
	}

}
