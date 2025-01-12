package gestorAplicacion.pasarelaPago;

import gestorAplicacion.usuario.Usuario;

public class CuentaBancaria {
    private long saldo = 0;
    private Usuario usuario;

    public CuentaBancaria(){
    }
    // getters y setters
    
    public long getSaldo() {
        return this.saldo;
    }
    // metodos
    
    public CuentaBancaria(Usuario usuario){
        this.usuario = usuario;
    }

    public void transferirDinero(Usuario usuarioReceptor, double cantidadTransferir){
        this.saldo -= cantidadTransferir;
        usuarioReceptor.getCuentaBancaria().recargarCuenta(cantidadTransferir);
    }

    public void recargarCuenta(double nuevoSaldo){
        this.saldo += nuevoSaldo;
    }
}