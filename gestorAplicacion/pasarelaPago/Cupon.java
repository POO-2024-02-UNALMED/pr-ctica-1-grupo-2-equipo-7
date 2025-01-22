package pasarelaPago;
import java.util.Random;

public interface Cupon {
    int VALORDESCUENTO = 11;

    public static int generarValorCupon(){
        Random random = new Random();
        int valorDescuentoAleatorio = random.nextInt(VALORDESCUENTO) + 5;
        return valorDescuentoAleatorio;
    }

    default boolean crearCupon(){
        Random random = new Random();
        Boolean generarCupon = random.nextBoolean();
        return generarCupon;
    }
}
