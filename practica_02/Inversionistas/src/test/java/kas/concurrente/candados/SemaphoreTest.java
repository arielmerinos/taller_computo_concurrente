package kas.concurrente.candados;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kas.concurrente.candadosImpl.Filtro;

public class SemaphoreTest {
    static final int RONDAS = 100000;
    static final int HILOS = 6;
    static final int MAX_HILOS_CONCURRENTES = 1;
    static int aprovacion = 0;

    Semaphore semaforo;
    Thread[] hilos;
    boolean esCorrecto;
    AtomicInteger atomicInteger;

    @BeforeEach
    public void setUp(){
        esCorrecto = true;
        atomicInteger = new AtomicInteger(0);
        initThreads();
    }

    void initFiltroSemaforo(){
        semaforo = new Filtro(HILOS, MAX_HILOS_CONCURRENTES);
    }

    void initThreads(){
        hilos = new Thread[HILOS];
        for(int i = 0; i < HILOS-1; ++i){
            hilos[i] = new Thread(this::adquiereRondas, String.format("%d",i));
        }
        hilos[HILOS-1] = new Thread(this::verifica);
    }

    void adquiereRondas(){
        long valor = 0;
        for(int i = 0; i < RONDAS; ++i){
            semaforo.acquire();
            this.atomicInteger.incrementAndGet();
            valor += this.simulaCS(Math.random()*100);
            if((i % 1000) == 0){
                this.duermeHilos(Math.random()*10);
            }
            this.atomicInteger.decrementAndGet();
            semaforo.release();
        }
         System.out.printf("%s valor alcanzado %d\n", Thread.currentThread().getName(), valor);
    }

    Integer simulaCS(Double iteraciones){
        int valor = 0;
        for(int j = 0; j < iteraciones; ++j){
            valor += j & 1;
        }
        return valor;
    }

    void verifica() {
        for(int i = 0; i < (HILOS * RONDAS); i++) {
            for(int j = 0; j < Math.random() * 100; j++) {
                esCorrecto = esCorrecto && (atomicInteger.get() <= MAX_HILOS_CONCURRENTES);
                if(atomicInteger.get() <= MAX_HILOS_CONCURRENTES) ++aprovacion;
            }
            if((i % 6000) == 0) {
                duermeHilos(Math.random() * 50);
            }
        }
        System.out.printf("%s verificacion finalizada %s\n", Thread.currentThread().getName(), esCorrecto);
    }

    void duermeHilos(Double aproxMillisec) {
        try {
            Thread.sleep(aproxMillisec.longValue());
        } catch(InterruptedException ie) {
            System.out.printf("%s  - Algo paso D:", Thread.currentThread().getName());
            throw new RuntimeException("D: :'v'.");
        }

    }

    @Test
    void semaforoTest() throws InterruptedException {
        initFiltroSemaforo();

        for(Thread t : hilos){
            t.start();
        }

        for(Thread t : hilos){
            t.join();
        }
        System.out.println("Aprovacion del "+ aprovacion);
        assertTrue(esCorrecto);
    }

}