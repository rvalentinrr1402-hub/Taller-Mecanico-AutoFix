package taller.mecanico;

public class ServicioAfinacion extends TipoVentas implements Cancelable{
    //CAntidad de aceite 1-8
    private int litrosAceite;
    public ServicioAfinacion(String folio,String cliente,int litrosAceite){
        super(folio,cliente);//la verificacion del contenido se hace aqui
        if(litrosAceite<1|| litrosAceite>8){
            throw new IllegalArgumentException("Cantidad de aceite fuera de lo permitido");
        }
        this.litrosAceite=litrosAceite;
    }

    //cobro por mano de hobra y por cada litro
    @Override
    public double calcularCobro(){
        return 1000+(litrosAceite*90);
    }
    //Asignacion del tipo de servicio
    @Override 
    public String getTipo(){
        return "Afinacion";
    }
    //cobro por mano de hobra y por cada litro
    @Override
    public double cancelar(int horas) throws CancelacionFueraDePlazo{
        //La afinación exige 2 horas o más y devuelve el 90%;
        if (horas < 2) {
            throw new CancelacionFueraDePlazo("Se requiere de almenos 2 horas, intento con " + horas);
        }
        //si las horas de anticipacion se cumplen se ejecuta el reembolso
        return calcularCobro()*0.90;
    }

}
