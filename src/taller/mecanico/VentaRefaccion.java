package taller.mecanico;

public class VentaRefaccion extends TipoVentas implements Cancelable {
    private String descripcion;
    private double precio;
    private int cantidad;
    
    public VentaRefaccion(String folio, String cliente, String descripcion, double precio, int cantidad){
        super(folio,cliente);
        if(descripcion.isBlank() || descripcion == null){
            throw new IllegalArgumentException("Se necesita una descripcion");
        }
        if(precio <= 0){
            throw new IllegalArgumentException("El precio unitario deve ser mayor a cero");
        }
        if(cantidad < 1){
            throw new IllegalArgumentException("La cantidad de piezas debe ser al menos 1");
        }

        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    @Override
    public double calcularCobro(){
        double total = precio * cantidad;
        if(cantidad >= 4){
            //5% de descuento si lleva 4 piezas o mas
            total *= 0.95;
        }
        return total;
    }

    //Especifar el tipo
    @Override 
    public String getTipo(){
        return "Refaccion";
    }

    @Override 
    public double cancelar(int horas) throws CancelacionFueraDePlazo{
        if(horas < 24){
            throw new CancelacionFueraDePlazo("Cancelacion rechasada Se requieren al menos 24 horas");
        }
        //retorno del pago por cancelacion
        return calcularCobro(); //Se devuelve el 100%
    }
}
