package kas.concurrente.modelos;

/**
 * En esta clase se simula el estacionamiento en si
 * Posee un arreglo de tipo Lugar
 * Posee un entero de lugaresDisponibles
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Estacionamiento {

    /**
     * Metodo constructor
     * @param capacidad La capacidad del estacionamiento
     */
    public Estacionamiento(int capacidad){
        /**
         * Aqui va tu codigo
         */
    }

    public Lugar[] getLugares() {
        return null;
    }

    public void setLugares(Lugar[] lugares) {
    }

    public int getLugaresDisponibles() {
        return -1;
    }

    public void setLugaresDisponibles(int lugaresDisponibles) {
    }

    /**
     * Metodo que nos indica si esta lleno el estacionamiento
     * @return true si esta lleno, false en otro caso
     */
    public boolean estaLleno(){
        return false;
    }

    /**
     * Metodo que inicaliza los lugares del arreglo
     * Este es un método optativo
     */
    public void inicializaLugares(){
        /**
         * Aqui va tu codigo
         */
    }

    /**
     * Metodo en el que se simula la entrada de un carro
     * Imprime un texto que dice que el carro a entrado de color AZUL
     * @param nombre El nombre del carro
     * @throws InterruptedException Si llega a fallar
     */
    public void entraCarro(int nombre) throws InterruptedException{
        /**
         * Aqui va tu codigo
         */
    }

    /**
     * Metodo que asigna el lugar, una vez asignado ESTACIONA su nave
     * @param lugar El lugar que correspone
     * @throws InterruptedException
     */
    public void asignaLugar(int lugar) throws InterruptedException {
        /**
         * Aqui va tucodigo
         */
    }

    /**
     * Se obtiene un lugar de forma pseudoAleatoria
     * @return Retorna el indice del lugar
     */
    public int obtenLugar(){
        /**
         * Aqui va tu codigo
         */
        return -1;
    }
}