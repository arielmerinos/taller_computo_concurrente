package kas.concurrente;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LockTest {
    static final int HILOS = 51;
    static final int ITERACIONES = 50;
    static final int MAX_VALOR = 1000000;
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

            assertEquals(MAX_VALOR, counter.getValor());
        }
    }   

    @Test
    void tasLock() throws InterruptedException{
        System.out.println("HILOS " + HILOS);
        ejecutaPrueba(new TASLock());
    }


    @Test
    void ttasLock() throws InterruptedException {
        ejecutaPrueba(new TTASLock());
    }

    
    @Test
    void backoffLock() throws InterruptedException {
        ejecutaPrueba(new BackoffLock());
    }
    
    @Test
    void clhLock() throws InterruptedException {
        ejecutaPrueba(new CLHLock());
    }
    
    @Test
    void mcsLock() throws InterruptedException {
        ejecutaPrueba(new MCSLock());
    }
    
    @Test
    void aLock() throws InterruptedException {
        ejecutaPrueba(new ALock(HILOS));
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