package kas.concurrente.tenedor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenedorTest {
    static int RONDAS = 300;
    StringBuffer stringBuffer;
    Tenedor tenedor;
    Thread[] hilos;

    @BeforeEach
    void setUp(){
        tenedor = new TenedorImpl(1);
        stringBuffer = new StringBuffer();
        hilos = new Thread[2];
        hilos[0] = new Thread(this::tomaYSueltaTenedor,""+obtenNumeroPar());
        hilos[1] = new Thread(this::tomaYSueltaTenedor,""+obtenNumeroImpar());
    }

    int obtenNumeroPar(){
        return (int) (2 * Math.random()*1000);
    }

    int obtenNumeroImpar(){
        return obtenNumeroPar()+1;
    }

    void tomaYSueltaTenedor(){
        for(int i = 0; i < RONDAS; ++i){
            tenedor.tomar();
            Thread t = Thread.currentThread();

            boolean esPar = Integer.parseInt(t.getName()) % 2 == 0;
            stringBuffer.append(esPar ? 'a' : 'c');
            stringBuffer.append(esPar ? 'b' : 'd');

            tenedor.soltar();
        }
    }

    @Test
    void tenedorTest() throws InterruptedException{
        hilos[0].start();
        hilos[1].start();
        hilos[0].join();
        hilos[1].join();
        assertTrue(stringBuffer.toString().matches("^(ab|cd)+$"));
        assertEquals(2*RONDAS, tenedor.getVecesTomado());
    }
    
}
