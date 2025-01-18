package baseDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import tienda.Inventario;
import tienda.Tienda;
import uiMain.MainMenu;
import usuario.Comprador;
import usuario.Notificacion;
import usuario.Vendedor;

public class Deserializador {
    //Este atributo es para definir la ruta al directorio tem que contiene las clases.
    private static File rutaTemp = new File("baseDatos\\temp");

    //Este método se encarga de deserializar un objeto comprador.
    public static void deserealizar(MainMenu menu){
        File[] docs = rutaTemp.listFiles();
        FileInputStream fis;
        ObjectInputStream ois;
        //Bucle para deserealizar todos nuestros objetos.
        for (File file : docs){
            if (file.getAbsolutePath().contains("comprador")){
                try {
                    fis = new FileInputStream(file);
                    ois = new ObjectInputStream(fis);
                    menu.setComprador((Comprador)ois.readObject());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            } else if (file.getAbsolutePath().contains("inventario")){
                try {
                    fis = new FileInputStream(file);
                    ois = new ObjectInputStream(fis);
                    menu.setInventario((Inventario)ois.readObject());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            } else if (file.getAbsolutePath().contains("notificacion")){
                try {
                    fis = new FileInputStream(file);
                    ois = new ObjectInputStream(fis);
                    menu.setNotificacion((Notificacion)ois.readObject());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            } else if (file.getAbsolutePath().contains("tienda")){
                try {
                    fis = new FileInputStream(file);
                    ois = new ObjectInputStream(fis);
                    menu.setTienda((Tienda)ois.readObject());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            } else if (file.getAbsolutePath().contains("vendedor")){
                try {
                    fis = new FileInputStream(file);
                    ois = new ObjectInputStream(fis);
                    menu.setVendedor((Vendedor)ois.readObject());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
