package kas.concurrente.tenedor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenedorTest {
    static int RONDAS = 3;
    StringBuffer stringBuffer;
    Tenedor tenedor;
    Thread[] hilos;

    @BeforeEach
    void setUp(){
        System.out.println("Iniciando configuración de la prueba...");

        tenedor = new TenedorImpl(1);
        stringBuffer = new StringBuffer();
        hilos = new Thread[2];
        hilos[0] = new Thread(this::tomaYSueltaTenedor,""+obtenNumeroPar());
        hilos[1] = new Thread(this::tomaYSueltaTenedor,""+obtenNumeroImpar());

        System.out.println("Configuración de la prueba completada.");
    }

    int obtenNumeroPar(){
        int numero = (int) (2 * Math.random()*1000);
        System.out.println("Número par generado: " + numero);
        return numero;
    }

    int obtenNumeroImpar(){
        int numero = obtenNumeroPar() + 1;
        System.out.println("Número impar generado: " + numero);
        return numero;
    }

    void tomaYSueltaTenedor(){
        System.out.println("Iniciando tomaYSueltaTenedor...");

        for(int i = 0; i < RONDAS; ++i){
            System.out.println("Tomando tenedor...");
            tenedor.tomar();

            Thread t = Thread.currentThread();
            boolean esPar = Integer.parseInt(t.getName()) % 2 == 0;
            stringBuffer.append(esPar ? 'a' : 'c');
            stringBuffer.append(esPar ? 'b' : 'd');

            System.out.println("Soltando tenedor...");
            tenedor.soltar();
        }

        System.out.println("Finalizando tomaYSueltaTenedor...");
    }

    @Test
    void tenedorTest() throws InterruptedException{
        System.out.println("Iniciando tenedorTest...");

        hilos[0].start();
        hilos[1].start();
        
        System.out.println("Esperando a que los hilos terminen...");
        
        hilos[0].join();
        hilos[1].join();

        System.out.println("Hilos terminados, realizando aserciones...");

        assertTrue(stringBuffer.toString().matches("^(ab|cd)+$"));
        assertEquals(2*RONDAS, tenedor.getVecesTomado());

        System.out.println("Finalizando tenedorTest.");
    }
}
