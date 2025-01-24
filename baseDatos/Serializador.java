package baseDatos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import tienda.Inventario;
import uiMain.MainMenu;

public class Serializador {
    private static File rutaTemp = new File("baseDatos\\temp");

    //Método encargado de serializar un objeto comprador:
    public static void serializar(MainMenu menu){
        FileOutputStream fos;
        ObjectOutputStream oos;
        File[] docs = rutaTemp.listFiles();
        PrintWriter pw;

        //Este for borra el contenido de los archivos al momento de guardar los objetos para evitar que haya redundancia en los archivos
        for (File file : docs){
            try {
                //Al crear este objeto PrintWriter y pasarle como parámetro la ruta de cada archivo, borra lo que haya en ellos automáticamente
                pw = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //Bucle for que se encarga de serializar todos nuestros objetos.
        for (File file : docs){
            if (file.getAbsolutePath().contains("comprador")){
                try {
                    fos = new FileOutputStream(file);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(menu.getComprador());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else if (file.getAbsolutePath().contains("vendedor")){
                try {
                    fos = new FileOutputStream(file);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(menu.getVendedor());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else if (file.getAbsolutePath().contains("tienda")){
                try {
                    fos = new FileOutputStream(file);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(menu.getTienda());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else if (file.getAbsolutePath().contains("inventario")){
                try {
                    fos = new FileOutputStream(file);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(menu.getInventario());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else if (file.getAbsolutePath().contains("notificacion")){
                try {
                    fos = new FileOutputStream(file);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(menu.getNotificacion());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void serializarInventarioStatic() { 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("inventarioStatic.dat"))) { 
            oos.writeObject(Inventario.getCategoriaTecnologia()); 
            oos.writeObject(Inventario.getCategoriaAseo()); 
            oos.writeObject(Inventario.getCategoriaComida()); 
            oos.writeObject(Inventario.getCategoriaPapeleria()); 
            oos.writeObject(Inventario.getCategoriaJuegueteria()); 
            oos.writeObject(Inventario.getCategoriaDeportes()); 
            oos.writeObject(Inventario.getListaCategorias()); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            } 
        }
    }


