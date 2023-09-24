package kas.concurrente.candados;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import kas.concurrente.candadosImpl.PetersonLock;

public class PetersonLockTest {
    static final int ITERACIONES = 100; 
    static final int MAX_VALOR = 100;
    static int aprovacion = 0;
    Lock lock;
    Counter counter; 

    @BeforeEach
    void setUp() {
        lock = new PetersonLock();
    }

    void incrementaContador(){
        for(int i=0; i<MAX_VALOR; ++i){
            counter.obtenEIncrementa();
        }
    }

    @Test
    void lockTest() throws InterruptedException {
        Thread[] threads = new Thread[2];
        for(int i = 0; i < ITERACIONES; i++) {
            counter = new Counter(lock);
            threads[0] = new Thread(this::incrementaContador, "0");
            threads[1] = new Thread(this::incrementaContador, "1");
            threads[0].start();
            threads[1].start();
            threads[0].join();
            threads[1].join();

            valoresIguales(2 * MAX_VALOR, counter.getValor());
        }
        if(aprovacion >= 70) System.out.println("Aprovacion del "+ aprovacion +"% \nPRUEBA SUPERADA ;D");
        assertEquals(true,aprovacion>=70);
    }

    void valoresIguales(int valor, int esperado){
        if(valor != esperado){
                System.out.println("Los valores no concuerdan");
        }else{
            ++aprovacion;
        }
    }
}

class Counter {
    private volatile int valor;
    private Lock lock;

    Counter(Lock lock) {
        this.valor = 0;
        this.lock = lock;
    }

    int obtenEIncrementa(){
        this.lock.lock();
        int res = this.valor;
        valor = this.valor + 1;
        this.lock.unlock();
        return res;
    }

    int getValor(){
        return valor;
    }
}