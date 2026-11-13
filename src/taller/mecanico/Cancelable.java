package taller.mecanico;

//Cancelaciones. Se pueden cancelar la afinación, los frenos y la refacción; el diagnóstico no
//se usara una interfas cancelable solo a los servicios que tengan esa opcion
public interface Cancelable {
    double cancelar(int anticipacion) throws CancelacionFueraDePlazo;
}
