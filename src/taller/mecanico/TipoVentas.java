package taller.mecanico;


public abstract class TipoVentas {
    //El taller tiene 4 tipos de ventas, todas necesitan un folio y el nombre del cliente
    protected String folio;
    protected String cliente;
    //datos inaccesibles desde fuera
    
    public TipoVentas(String folio, String cliente){
        //se requiere que ningun folio o nombre de cliente este vacio
        if(folio == null || folio.isBlank()){
            //Excepción propia de la clase
            throw new IllegalArgumentException("El folio no puede estar vacío");
        }
        if(cliente == null || cliente.isBlank()){
            //Excepción propia de la clase
            throw new IllegalArgumentException("Se requiere de un cliente que no este vacío");
        }
        //Si uno de los dos esta bacio o no contiene algun caracter
        //el constructor no construye (la creacion del objeto se cansela 
        //pero el codigo se sigue ejecutando de manera que no afecte las demas funciones)
        this.folio = folio;
        this.cliente = cliente;
    }
    //getters para poder realisar la imprecion
    public String getFolio(){
        return folio;
    }
    public String getCliente(){
        return cliente;
    }
    //metodos abstractos para el cobro
    public abstract double calcularCobro();
    //y para la obtencion del tipo
    public abstract String getTipo();

    //sobrecarga toString para la imprecion del folio
    public String toString(){
        //Imprimir cada venta con su folio, tipo, cliente y cobro.
        return "Folio: " + folio + "\ntipo: " + getTipo() 
        + "\nCliente: " + cliente + "\nTotal: $" + calcularCobro();
    }
}
