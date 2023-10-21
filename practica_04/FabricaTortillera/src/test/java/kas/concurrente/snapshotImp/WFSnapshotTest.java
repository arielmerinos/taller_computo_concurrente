package kas.concurrente.snapshotImp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kas.concurrrente.snapshot.Snapshot;
import kas.concurrrente.snapshotImp.WFSnapshot;

public class WFSnapshotTest {
    WFSnapshot<Integer> snap;
    Thread[] hilos;
    final int capacidad = 10;

    /**
     * Inicializamos los hilos
     */
    void initHilos(){
       hilos = new Thread[capacidad];
       for(int i = 0; i < capacidad; ++i){
        hilos[i] = new Thread(this::simulaCS,""+i);
       }
    }

    /**
     * Metodo que simula la CS
     * Aumenta en 1 con update
     */
    void simulaCS(){
        int id = Integer.parseInt(Thread.currentThread().getName());
        snap.update(snap.getATable()[id].getValue() +1);
    }
    
    @BeforeEach
    void setUp(){
        snap = new WFSnapshot<>(capacidad, 1);
    }

    @Test
    void constructorTest(){
        boolean res = true;
        for(int i = 0; i < snap.getATable().length; ++i){
            res = res & (snap.getATable()[i].getValue() == 1);
        }
        assertTrue(snap.getATable().length > 0 && res);
    }

    @Test
    void ejecucionBasica() throws InterruptedException{
        for(Thread t : hilos){
            t.start();
        }

        for(Thread t : hilos){
            t.join();
        }
        int total = 0;
        for(int i = 0; i < snap.getATable().length; ++i){
            total += snap.getATable()[i].getValue();
        }
        assertEquals(20,total);
    }
}
