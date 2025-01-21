package tienda;

import java.util.ArrayList;
import java.util.List;

import compras.HistorialCompras;
import tienda.Producto.Categoria;
import java.io.Serializable;



public class Inventario implements Serializable{
    private static final long serialVersionUID = 1L; // Atributo obligatorio por implementar Serializable
    private static ArrayList<Producto> categoriaTecnologia;
    private static ArrayList<Producto> categoriaAseo;
    private static ArrayList<Producto> categoriaComida;
    private static ArrayList<Producto> categoriaPapeleria;  //Atributos añadidos para la funcionalidad de recomendaciones (Simón)
    private static ArrayList<Producto> categoriaJuegueteria;
    private static ArrayList<Producto> categoriaDeportes;

    //Necesario para realizar las recomendaciones
    private static ArrayList<ArrayList<Producto>> listaCategorias = new ArrayList<>();
    
    private ArrayList<Producto> productosTotal;
    private Object[][] catalogo;

    public Inventario(ArrayList<Producto> categoriaTecnologia, ArrayList<Producto> categoriaAseo, ArrayList<Producto> categoriaComida, 
                     ArrayList<Producto> categoriaPapeleria, ArrayList<Producto> categoriaJuegueteria, ArrayList<Producto> categoriaDeportes) {
       
        Inventario.categoriaTecnologia = categoriaTecnologia;
        Inventario.categoriaAseo = categoriaAseo;
        Inventario.categoriaComida = categoriaComida;
        Inventario.categoriaPapeleria = categoriaPapeleria;
        Inventario.categoriaJuegueteria = categoriaJuegueteria;
        Inventario.categoriaDeportes = categoriaDeportes;

        Inventario.listaCategorias.add(categoriaTecnologia);
        Inventario.listaCategorias.add(categoriaAseo);
        Inventario.listaCategorias.add(categoriaComida);
        Inventario.listaCategorias.add(categoriaPapeleria);
        Inventario.listaCategorias.add(categoriaJuegueteria);
        Inventario.listaCategorias.add(categoriaDeportes);

        this.productosTotal = new ArrayList<Producto>();
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
    
    public Object[][] crearCatalogo(){
    	
    	//Se crea una matriz para que al usuario se le muestren
    	//Los productos a modo de tabla
    	catalogo = new Object[6][8];
    	
    	
    	catalogo[0][0] = "--         ";
        catalogo[0][1] = "ID";

        //Se añade una columna vacía al princpio para ser utilizada luego con las recomendaciones

        for (int i = 1; i <= 5; i++) {
            catalogo[i][0] = "--         ";
        }

        //Se crean la primera fila y columna, que guardan las coordenadas de los productos
    	
    	for (int i = 1; i <=5; i++) {
    		catalogo[i][1] = i;
    	}
    	
    	for (int i = 2; i <=7; i++) {
    		catalogo[0][i] = (char)('A' + i-2);
    	}

        
    	
    	// Se añaden los productos al resto de espacios de la matriz
    	
    	int fila = 1;
    	int columna = 2;
    	
    	for (int i = 0; i < 30; i++) {
    		catalogo[fila][columna] = productosTotal.get(i);
    		if (columna != 7) {
    			columna += 1;
    		} else {
    			columna = 2;
    			if (fila != 5) {
    				fila +=1;
    			}
    		}
 
    	}
    	
    	
    	return catalogo;
    }

    //Método sobrecargado para cuando se escojan las recomendaciones
    public Object[][] crearCatalogo(HistorialCompras historial){

        Categoria categoriaRecomendada1;
        ArrayList<Producto> productosRecomendados1;
        Categoria categoriaRecomendada2;
        ArrayList<Producto> productosRecomendados2;
        Categoria categoriaRecomendada3;
        ArrayList<Producto> productosRecomendados3;

        int fila;
        int columna;

        Object[][] catalogo = new Object[6][8];
        catalogo[0][0] = "--         ";
        catalogo[0][1] = "ID";

        //Se añade una columna vacía al princpio, dependiendo de cuántas categorías
        //Se vayan a recomendar, se añadirá la palabra RECOMENDADO al inicio de sus
        //filas respectivas

        for (int i = 1; i <= 5; i++) {
            catalogo[i][0] = "--         ";
        }

        //Se crean la primera fila y columna, que guardan las coordenadas de los productos
    	
    	for (int i = 1; i <=5; i++) {
    		catalogo[i][1] = i;
    	}
    	
    	for (int i = 2; i <=7; i++) {
    		catalogo[0][i] = (char)('A' + i-2);
    	}

        int categorias = 0;
        //Revisa cuántas categorías hay almacenadas en categoriasMas compradas
        //ejemplo: puede que guarde [TECNOLOGIA, null, null] porque solo se han
        //comprado productos de la categoría TECNOLOGIA
        for (int i = 0; i < 3; i++){

            if (historial.getCategoriasMasCompradas()[i] != null){

                categorias ++;
               }
           }
           

           //Este switch controla la cantidad de filas del catálogo que se
           //verán modificadas por la cantidad de recomendaciones que se vayan
           //a hacer
        switch(categorias){
            case 1:
                catalogo[1][0] = "RECOMENDADO";

                Categoria categoriaRecomendada = historial.getCategoriasMasCompradas()[0];
                ArrayList<Producto> productosRecomendados = listaCategorias.get(categoriaRecomendada.ordinal());

    

                for (int i = 0; i <= 5; i++){
                    catalogo[1][i+2] = productosRecomendados.get(i);
                    productosTotal.remove(productosRecomendados.get(i));
                }

                // Se añaden los productos al resto de espacios de la matriz
    	
    	        fila = 2;
    	        columna = 2;

                //Luego de añadir una fila de productos recomendados, quedan
                //24 espacios disponibles para productos no recomendados
    	
    	        for (int i = 0; i < 24; i++) {
    		    catalogo[fila][columna] = productosTotal.get(i);
    		    if (columna != 7) {
    			    columna += 1;
    		    } else {
    			    columna = 2;
    			    if (fila != 5) {
    				    fila +=1;
    			}
    		}
 
    	    } break;

             case 2:

             catalogo[1][0] = "RECOMENDADO";
             catalogo[2][0] = "RECOMENDADO";

                categoriaRecomendada1 = historial.getCategoriasMasCompradas()[0];
                productosRecomendados1 = listaCategorias.get(categoriaRecomendada1.ordinal());


                for (int i = 0; i <= 5; i++){
                    catalogo[1][i+2] = productosRecomendados1.get(i);
                    productosTotal.remove(productosRecomendados1.get(i));
                }

                categoriaRecomendada2 = historial.getCategoriasMasCompradas()[1];
                productosRecomendados2 = listaCategorias.get(categoriaRecomendada2.ordinal());

                for (int i = 0; i <= 5; i++){
                    catalogo[2][i+2] = productosRecomendados2.get(i);
                    productosTotal.remove(productosRecomendados2.get(i));
                }

                // Se añaden los productos al resto de espacios de la matriz
    	
    	        fila = 3;
    	        columna = 2;

                //Luego de añadir dos filas de productos recomendados, quedan
                //18 espacios disponibles para productos no recomendados
    	
    	        for (int i = 0; i < 18; i++) {
    		    catalogo[fila][columna] = productosTotal.get(i);
    		    if (columna != 7) {
    			    columna += 1;
    		    } else {
    			    columna = 2;
    			    if (fila != 5) {
    				    fila +=1;
    			}
    		}
 
    	    }   break;

            case 3:
            catalogo[1][0] = "RECOMENDADO";
            catalogo[2][0] = "RECOMENDADO";
            catalogo[3][0] = "RECOMENDADO";

               categoriaRecomendada1 = historial.getCategoriasMasCompradas()[0];
               productosRecomendados1 = listaCategorias.get(categoriaRecomendada1.ordinal());


               for (int i = 0; i <= 5; i++){
                   catalogo[1][i+2] = productosRecomendados1.get(i);
                  productosTotal.remove(productosRecomendados1.get(i));
                  productosTotal.add(productosRecomendados1.get(i));
               }

               categoriaRecomendada2 = historial.getCategoriasMasCompradas()[1];
               productosRecomendados2 = listaCategorias.get(categoriaRecomendada2.ordinal());

               for (int i = 0; i <= 5; i++){
                   catalogo[2][i+2] = productosRecomendados2.get(i);
                   productosTotal.remove(productosRecomendados2.get(i));
                   productosTotal.add(productosRecomendados2.get(i));
               }

               categoriaRecomendada3 = historial.getCategoriasMasCompradas()[2];
               productosRecomendados3 = listaCategorias.get(categoriaRecomendada3.ordinal());

               for (int i = 0; i <= 5; i++){
                   catalogo[3][i+2] = productosRecomendados3.get(i);
                   productosTotal.remove(productosRecomendados3.get(i));
                   productosTotal.add(productosRecomendados3.get(i));
               }

               // Se añaden los productos al resto de espacios de la matriz
       
               fila = 4;
               columna = 2;

               //Luego de añadir dos filas de productos recomendados, quedan
               //18 espacios disponibles para productos no recomendados
       
               for (int i = 0; i < 12; i++) {
               catalogo[fila][columna] = productosTotal.get(i);
               if (columna != 7) {
                   columna += 1;
               } else {
                   columna = 2;
                   if (fila != 5) {
                       fila +=1;
               }
           }


        }
    }
        return catalogo;

    }


    public static Boolean verificarproducto(Producto producto, int unidades) {

        //Este método lo cambie ya que si bien funcionaba correctamente, al serializar cambia la cosa. La serialización lo que hace es guardar los valores y atributos de las instancias
        //pero al deserializar se crean nuevas instancias con un espacio de memoria diferente al que tenian antes de ser serializados. El método contains que estabamos usando antes lo
        //que hace es comparar los espacios de memoria que ocupan los productos, entonces en el atributo equals que heredan todos los objetos de la clase Object al deserializarse va a 
        //tener el valor del espacio de memoria que contenía antes de ser serializado incluso si ahora ocupa un espacio de memoria diferente. Por lo cual esta solución lo que hace es 
        //verificar de que el producto exista por su nombre, así funciona correctamente. En resumen, el que hizo este método (que creo que fue Santiago) no se preocupe que todo sigue 
        //funcionando normal :)
        Categoria categoria = producto.getCategoria();
        String nombre = categoria.getNombre();
        boolean verificacion = false;
    
        if (nombre.equalsIgnoreCase("tecnologia")) {
            for (Producto p : categoriaTecnologia) {
                if (p.getNombre().equals(producto.getNombre()) && p.getCantidad() >= unidades) {
                    verificacion = true;
                    break;
                }
            }
        } else if (nombre.equalsIgnoreCase("aseo")) {
            for (Producto p : categoriaAseo) {
                if (p.getNombre().equals(producto.getNombre()) && p.getCantidad() >= unidades) {
                    verificacion = true;
                    break;
                }
            }
        } else if (nombre.equalsIgnoreCase("comida")) {
            for (Producto p : categoriaComida) {
                if (p.getNombre().equals(producto.getNombre()) && p.getCantidad() >= unidades) {
                    verificacion = true;
                    break;
                }
            }
        } else if (nombre.equalsIgnoreCase("papeleria")) {
            for (Producto p : categoriaPapeleria) {
                if (p.getNombre().equals(producto.getNombre()) && p.getCantidad() >= unidades) {
                    verificacion = true;
                    break;
                }
            }
        } else if (nombre.equalsIgnoreCase("jugueteria")) {
            for (Producto p : categoriaJuegueteria) {
                if (p.getNombre().equals(producto.getNombre()) && p.getCantidad() >= unidades) {
                    verificacion = true;
                    break;
                }
            }
        } else if (nombre.equalsIgnoreCase("deportes")) {
            for (Producto p : categoriaDeportes) {
                if (p.getNombre().equals(producto.getNombre()) && p.getCantidad() >= unidades) {
                    verificacion = true;
                    break;
                }
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

    public static ArrayList<Producto> getCategoriaTecnologia() {
        return categoriaTecnologia;
    }

    public static void setCategoriaTecnologia(ArrayList<Producto> categoriaTecnologia) {
        Inventario.categoriaTecnologia = categoriaTecnologia;
    }

    public static ArrayList<Producto> getCategoriaAseo() {
        return categoriaAseo;
    }

    public static void setCategoriaAseo(ArrayList<Producto> categoriaAseo) {
        Inventario.categoriaAseo = categoriaAseo;
    }

    public static ArrayList<Producto> getCategoriaComida() {
        return categoriaComida;
    }

    public static void setCategoriaComida(ArrayList<Producto> categoriaComida) {
        Inventario.categoriaComida = categoriaComida;
    }

    public static ArrayList<Producto> getCategoriaPapeleria() {
        return categoriaPapeleria;
    }

    public static void setCategoriaPapeleria(ArrayList<Producto> categoriaPapeleria) {
        Inventario.categoriaPapeleria = categoriaPapeleria;
    }

    public static ArrayList<Producto> getCategoriaJuegueteria() {
        return categoriaJuegueteria;
    }

    public static void setCategoriaJuegueteria(ArrayList<Producto> categoriaJuegueteria) {
        Inventario.categoriaJuegueteria = categoriaJuegueteria;
    }

    public static ArrayList<Producto> getCategoriaDeportes() {
        return categoriaDeportes;
    }

    public static void setCategoriaDeportes(ArrayList<Producto> categoriaDeportes) {
        Inventario.categoriaDeportes = categoriaDeportes;
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
