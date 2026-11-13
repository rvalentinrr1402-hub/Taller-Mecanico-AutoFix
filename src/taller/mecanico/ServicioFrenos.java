package taller.mecanico;

public class ServicioFrenos extends TipoVentas implements Cancelable{
    //todos los servicios son clases hijas de la clase TipoVentas
    //Freno tambien implenta la opcion de cancelar con la interfas Cancelable
    //(ejes, 1 o 2)
    private int ejes;
    public ServicioFrenos(String folio, String cliente, int ejes){
        super (folio,cliente);
        //solo se puede trabajar 1 o 2 ejes de un mismo veiculo
        if (ejes > 2 || ejes < 1) {
            throw new IllegalArgumentException("Ejes invalidos");
        }
        this.ejes = ejes;
    }
    //calculo del cobro total
    @Override
    public double calcularCobro(){
        double total = ejes * 650;
        if(ejes == 2){
            //Si se trabaja con los ejes se descuentan $100 de total
            total -= 100;
        }
        return total;
    }

    //Especifar el tipo
    @Override 
    public String getTipo(){
        return "Frenos";
    }

    @Override 
    public double cancelar(int horas) throws CancelacionFueraDePlazo{
        if(horas < 4){
            throw new CancelacionFueraDePlazo("Se requieren 4 horas");
        }
        //retorno del pago por cancelacion
        return calcularCobro()*0.70;
    }
}
