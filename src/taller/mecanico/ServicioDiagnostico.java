package taller.mecanico;

public class ServicioDiagnostico extends TipoVentas{

    private int horas;
    private boolean frecuente;

    public ServicioDiagnostico(String folio, String cliente, int horas, boolean frecuente){
        super (folio,cliente);
        //validacion para las horas del diagnostico
        if (horas > 6 || horas < 1) {
            throw new IllegalArgumentException("El diagostico requiere de 1 a 6 horas");
        }
        this.horas = horas;
        this.frecuente = frecuente;
    }
    //calculo del cobro total
    @Override
    public double calcularCobro(){
        double total = horas * 300;
        if(frecuente){
            //Si el cliente es frecuente la primera hora no se cobra
            total -= 300;
        }
        return total;
    }

    //Especifar el tipo
    @Override 
    public String getTipo(){
        return "Diagnostico";
    }
}
