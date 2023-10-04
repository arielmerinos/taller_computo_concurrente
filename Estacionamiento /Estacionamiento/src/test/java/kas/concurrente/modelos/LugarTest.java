package kas.concurrente.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LugarTest {
    Semaphore semaforo;
    Lugar lugar;
    List<Thread> hilos;
    static final int numHilos = 15;

    @BeforeEach
    void setUp() throws InterruptedException{
        lugar = new Lugar(1);
        semaforo = new Semaphore(1);
        initHilos();
    }

    @Test
    void constructorTest(){
        assertTrue(lugar.getId() == 1 && lugar.getDisponible() == true && lugar.getFiltroModificado() != null);
    }

    @Test
    void estacionaTest() throws InterruptedException{
        lugar.estaciona();
        assertTrue(lugar.getDisponible());
    }

    @Test
    void vecesEstacionadoTest() throws InterruptedException{
        for(Thread t : hilos){
            t.start();
        }

        for(Thread t : hilos){
            t.join();
        }
        assertEquals(numHilos, lugar.getVecesEstacionado());
    }

    void initHilos() throws InterruptedException {
    hilos = new ArrayList<>();

        for(int i = 0; i < numHilos; ++i){
            Thread t = new Thread(this::simulaCS);
            hilos.add(t);
        }
    }

    void simulaCS(){
        try{
            semaforo.acquire();
            lugar.estaciona();
            semaforo.release();
        }catch(InterruptedException e){
            e.printStackTrace();
        }   
    }
}



