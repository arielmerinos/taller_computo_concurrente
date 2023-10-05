package kas.concurrente;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LockTest {
    static final int HILOS = 10;
    static final int ITERACIONES = 20;
    static final int MAX_VALOR = 100000;
    static final int TAM_POR_HILO = MAX_VALOR/HILOS;
    static final int RESTANTE = MAX_VALOR % HILOS;
    volatile Counter counter;
    Thread[] hilos;

    /**
     * Metodo que incremeta el contador
     * @param iteraciones El numero de iteraciones
     */
    void incrementaContador(final int iteraciones){
        for(int i = 0; i < iteraciones; ++i){
            counter.getAndIncrement();
        }
    }

    /**
     * Metodo que ejecuta la prueba
     * @param lock El candado a utilizar
     * @throws InterruptedException
     */
    void ejecutaPrueba(Lock lock) throws InterruptedException{
        for(int i = 0; i < ITERACIONES; ++i){
            counter = new Counter(lock);
            hilos = new Thread[HILOS];

            for(int j = 0, trabajoFaltante = RESTANTE; j < hilos.length; ++j, --trabajoFaltante){
                final int tam = TAM_POR_HILO + (trabajoFaltante> 0?1 : 0);
                hilos[j] = new Thread(() -> incrementaContador(tam));
            }

            for(Thread t : hilos){
                t.start();
            }

            for(Thread t : hilos){
                t.join();
            }
            System.out.println("Valor: " + counter.getValor());
            assertEquals(MAX_VALOR, counter.getValor());
        }
    }   

    @Test
    void tasLock() throws InterruptedException{
        System.out.println("HILOS " + HILOS);
        System.out.println("tasLock");
        long startTime = System.currentTimeMillis(); // Captura el tiempo de inicio
        ejecutaPrueba(new TASLock());
        long endTime = System.currentTimeMillis(); // Captura el tiempo de finalización
        long duration = endTime - startTime; // Calcula la duración en milisegundos
        System.out.println("Tiempo de ejecución: " + duration + " ms");

    }


    @Test
    void ttasLock() throws InterruptedException {
        System.out.println("ttasLock");
        long startTime = System.currentTimeMillis(); // Captura el tiempo de inicio
        ejecutaPrueba(new TTASLock());
        long endTime = System.currentTimeMillis(); // Captura el tiempo de finalización
        long duration = endTime - startTime; // Calcula la duración en milisegundos
        System.out.println("Tiempo de ejecución: " + duration + " ms");

    }

    
    @Test
    void backoffLock() throws InterruptedException {
        System.out.println("backoffLock");
        long startTime = System.currentTimeMillis(); // Captura el tiempo de inicio

        ejecutaPrueba(new BackoffLock());
        long endTime = System.currentTimeMillis(); // Captura el tiempo de finalización
        long duration = endTime - startTime; // Calcula la duración en milisegundos
        System.out.println("Tiempo de ejecución: " + duration + " ms");

    }
    
    @Test
    void clhLock() throws InterruptedException {
        System.out.println("clhLock");
        long startTime = System.currentTimeMillis(); // Captura el tiempo de inicio

        ejecutaPrueba(new CLHLock());
        long endTime = System.currentTimeMillis(); // Captura el tiempo de finalización
        long duration = endTime - startTime; // Calcula la duración en milisegundos
        System.out.println("Tiempo de ejecución: " + duration + " ms");

    }

//    @Test
//    void mcsLock() throws InterruptedException {
//        System.out.println("mcsLock");
//        long startTime = System.currentTimeMillis(); // Captura el tiempo de inicio
//
//        ejecutaPrueba(new MCSLock());
//        long endTime = System.currentTimeMillis(); // Captura el tiempo de finalización
//        long duration = endTime - startTime; // Calcula la duración en milisegundos
//        System.out.println("Tiempo de ejecución: " + duration + " ms");
//
//    }

    @Test
    void aLock() throws InterruptedException {
        System.out.println("alock");
        long startTime = System.currentTimeMillis(); // Captura el tiempo de inicio

        ejecutaPrueba(new ALock(HILOS));
        long endTime = System.currentTimeMillis(); // Captura el tiempo de finalización
        long duration = endTime - startTime; // Calcula la duración en milisegundos
        System.out.println("Tiempo de ejecución: " + duration + " ms");

    }
}

/**
 * Clase que fungira como contador
 * @author Kassandra Mirael
 * @version 1.0
 */
class Counter{
    volatile int valor;
    volatile Lock lock;

    Counter(Lock lock){
        this.valor = 0;
        this.lock = lock;
    }

    /**
     * Metodo que increnta el contador y retorna el nuevo valor
     * @return El nuevo valor
     */
    int getAndIncrement(){
        this.lock.lock();
        int res = this.valor++;
        this.lock.unlock();
        return res;
    }

    /**
     * Metodo que retorna el valor
     * @return El valor
     */
    int getValor(){
        return valor;
    }
}