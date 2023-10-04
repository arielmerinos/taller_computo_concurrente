package kas.concurrente.modelos;

import java.util.Random;

/**
 * En esta clase se simula el estacionamiento en si
 * Posee un arreglo de tipo Lugar
 * Posee un entero de lugaresDisponibles
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Estacionamiento {

    private volatile Lugar[] lugares;
    private volatile int lugaresDisponibles;
    /**
     * Metodo constructor
     * @param capacidad La capacidad del estacionamiento
     */
    public Estacionamiento(int capacidad){
        lugares = new Lugar[capacidad];
        lugaresDisponibles = capacidad;
        inicializaLugares();
    }

    public Lugar[] getLugares() {
        return lugares;
    }

    public void setLugares(Lugar[] lugares) {
        this.lugares = lugares;
    }

    public int getLugaresDisponibles() {
        int lugarDisp = 0;
        for (Lugar lugar : lugares) {
            if (lugar.isDisponible()) {
                lugarDisp++;
            }
        }
        this.lugaresDisponibles = lugarDisp;
        return this.lugaresDisponibles;
    }

    public void setLugaresDisponibles(int lugaresDisponibles) {
        this.lugaresDisponibles = lugaresDisponibles;

    }

    /**
     * Metodo que nos indica si esta lleno el estacionamiento
     * @return true si esta lleno, false en otro caso
     */
    public boolean estaLleno(){
        return getLugaresDisponibles() == 0;
    }

    /**
     * Metodo que inicaliza los lugares del arreglo
     * Este es un método optativo
     */
    public void inicializaLugares(){
        for (int i = 0; i < lugares.length; i++) {
            lugares[i] = new Lugar(i);
        }
    }

    /**
     * Metodo en el que se simula la entrada de un carro
     * Imprime un texto que dice que el carro a entrado de color AZUL
     * @param nombre El nombre del carro
     * @throws InterruptedException Si llega a fallar
     */
    public void entraCarro(int nombre) throws InterruptedException{
        if (!estaLleno()) {
            asignaLugar(obtenLugar());
            System.out.println("\u001B[34mEl carro " + nombre + " ha entrado\u001B[0m");
        }
    }

    /**
     * Metodo que asigna el lugar, una vez asignado ESTACIONA su nave
     * @param lugar El lugar que correspone
     * @throws InterruptedException
     */
    public void asignaLugar(int lugar) throws InterruptedException {
        lugares[lugar].estaciona();
    }

    /**
     * Se obtiene un lugar de forma pseudoAleatoria
     * @return Retorna el indice del lugar
     */
    public int obtenLugar(){
        return (int) (Math.random() * lugares.length);

//        return new Random().nextInt(lugares.length + 1);

    }
}