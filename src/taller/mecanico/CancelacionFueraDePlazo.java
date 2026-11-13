package taller.mecanico;

//Errores propios del negocio
public class CancelacionFueraDePlazo extends Exception{
    public CancelacionFueraDePlazo(String mensaje){
        super(mensaje);
    }
}
