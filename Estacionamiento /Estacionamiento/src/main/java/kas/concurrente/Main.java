package kas.concurrente;

import kas.concurrente.candado.Semaphore;
import kas.concurrente.candadosImpl.Filtro;
import kas.concurrente.modelos.Estacionamiento;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Clase principal, la usaran para SUS pruebas
 * Pueden modigicar los valores estaticos para ver como funciona
 * NO USEN VALORES EXTREMEDAMENTE ALTOS, puede alentar mucho su compu
 * Usa 2 objetos, uno del tipo FiltroModificado
 * Y otro de tipo estacionamiento (Salvo que le quieran meter tercer piso)
 * PUNTO EXTRA SI IMPLEMENTAN 5 PISOS AL ESTACIONAMIENTO
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Main implements Runnable{
    public static final int NUM_CARROS = 10000;
    public static final int NUM_CARROS_PERMITIDOS = 10;
    public static final int TOTAL_DE_PISOS = 5;
    private Estacionamiento[] pisos;
    private Semaphore caseta;



    /**
     * Metodo constructor
     * Se inicializa el Filtro Modificado con _______
     * Se inicalizco el Estacionamiento con _______
     */
    public Main(){
        caseta = new Filtro(NUM_CARROS, 1);
        pisos = new Estacionamiento[TOTAL_DE_PISOS];
        for (int i = 0; i < TOTAL_DE_PISOS; i++) {
            pisos[i] = new Estacionamiento(NUM_CARROS_PERMITIDOS);
        }
    }

    /**
     * Una documentacion del main xD
     * Paso 0: Lee estas instrucciones
     * Paso 1: Crea el Objeto de tipo main
     * Paso 2: Crea Una estructura de datos que contenga a nuestros hilos
     * Paso 3: Genera con un ciclo, el cual inialice un numero igual de NUM_CARROS
     * Paso 4: No olvides agregarlos a la estructura e inicializarlos
     * Paso 5: Finalmente has un Join a tus hilos
     * @param args Los Argumentos
     * @throws InterruptedException Por si explota su compu al ponerle medio millon de hilos xD
     */
    public static void main(String[] args) throws InterruptedException{
        Main main = new Main();
        List<Thread> list = new ArrayList<>();
        for (int i=0;i<NUM_CARROS ;i++ ) {
            Thread t = new Thread(main,""+i);
            list.add(t);
            t.start();
        }

        for (Thread t:list) {
            t.join();
        }
    }

    /**
     * Aqui esta su primer seccion crítica
     * Paso 1: Keep calm and ...
     * Paso 2: Beware with the concurrent code
     * Paso 3: Obten el ID de tu hilo
     * Paso 4: TU CARRO (HILO) ENTRARA AL ESTACIONAMIENTO
     */
    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                caseta.acquire();
                int numPiso = random.nextInt(TOTAL_DE_PISOS);

                if (!pisos[numPiso].estaLleno()) {
                    int nombre = Integer.parseInt(Thread.currentThread().getName());
                    System.out.println("\u001B[0m" + "\033[34m" + "El carro " + nombre + " ha entrado al piso: "+ numPiso + "\u001B[0m");
                    pisos[numPiso].entraCarro(nombre);
                    caseta.release();
                    System.out.println("\u001B[0m" + "\033[31m" + "El carro " + nombre + " ha salido al piso: "+ numPiso  + "\u001B[0m");

                    break;
                }
                caseta.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
