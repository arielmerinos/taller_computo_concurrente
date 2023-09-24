package kas.concurrente;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kas.concurrente.candados.Semaphore;
import kas.concurrente.candadosImpl.Filtro;
import kas.concurrente.inversionista.Inversionista;
import kas.concurrente.inversionista.InversionistaFiltro;
import kas.concurrente.tenedor.Tenedor;
import kas.concurrente.tenedor.TenedorImpl;

public class MesaTest {
    static final int TAM_MESA = 5;
 // static final int DIEZ_SEC = 10000;
    static final int DIEZ_SEC = 10000;
    static int VECES_COMIDOD_ESPERADO = 5;
    Inversionista[] inversionistas;
    Thread[] hilos;
    Tenedor[] tenedores;

    Semaphore semaforo;

    @BeforeEach
    void setUp(){
        System.out.println("---- Configurando el entorno de prueba ----");
        inicializaTenedores();
    }

    void inicializaTenedores(){
        System.out.println("Inicializando tenedores...");
        tenedores = new Tenedor[TAM_MESA];
        for(int i = 0; i < TAM_MESA; i++){
            tenedores[i] = new TenedorImpl(i);
        }
        System.out.println("Tenedores inicializados.");
    }

    @Test
    void todosHanIntentadoComerFiltro() throws InterruptedException {
        System.out.println("---- Iniciando prueba: todosHanIntentadoComerFiltro ----");
        inicializaInversionistasYCubierto(InversionistaFiltro.class);

        boolean losTenedoresFueronTomadosAlMenosUnaVez = true;

        for(Tenedor t: tenedores){
            System.out.println("Veces que el tenedor " + t.getId() + " fue tomado: " + t.getVecesTomado());
            losTenedoresFueronTomadosAlMenosUnaVez = losTenedoresFueronTomadosAlMenosUnaVez && t.getVecesTomado() > 0;
        }

        assertTrue(losTenedoresFueronTomadosAlMenosUnaVez);
        System.out.println("---- Finalizando prueba: todosHanIntentadoComerFiltro ----");
    }

    @Test
    void todosComieronFiltro() throws InterruptedException {
        System.out.println("---- Iniciando prueba: todosComieronFiltro ----");
        inicializaInversionistasYCubierto(InversionistaFiltro.class);

        boolean comieronTodasLasVeces = true;

        for(Inversionista in : inversionistas){
            System.out.println("Veces que el inversionista " + in.getId() + " ha comido: " + in.getVecesComido());
            comieronTodasLasVeces = comieronTodasLasVeces && (in.getVecesComido() >= VECES_COMIDOD_ESPERADO);

            System.out.println("Comieron todas las veces: " + comieronTodasLasVeces);
        }

        assertTrue(comieronTodasLasVeces);
        System.out.println("---- Finalizando prueba: todosComieronFiltro ----");
    }

    void inicializaInversionistasYCubierto(Class<? extends Inversionista> clazz) throws InterruptedException{
        System.out.println("Inicializando inversionistas y cubiertos...");
        inicializaInversionistas(clazz);
        initHilos();
        ejecutaHilos();
        System.out.println("Inversionistas y cubiertos inicializados.");
    }

    void inicializaInversionistas(Class<? extends Inversionista> clazz){
        System.out.println("Creando semáforo y asignándolo a inversionistas...");
        semaforo = new Filtro(TAM_MESA, TAM_MESA-1);
        inversionistas = new Inversionista[TAM_MESA];

        try{
            Class[] cArg = new Class[] {Semaphore.class};
            for(int i = 0; i < TAM_MESA; ++i){
                inversionistas[i] = clazz.getDeclaredConstructor(cArg).newInstance(semaforo);
                inversionistas[i].setId(i);
                inversionistas[i].setTenedorIzq(tenedores[i]);
                inversionistas[i].setTenedorDer(tenedores[(i+1)%TAM_MESA]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Semáforo creado y asignado a inversionistas.");
    }

    void initHilos(){
        System.out.println("Inicializando hilos...");
        hilos = new Thread[TAM_MESA];
        for(int i = 0; i< TAM_MESA; ++i){
            hilos[i] = new Thread(inversionistas[i],""+i);
        }
        System.out.println("Hilos inicializados.");
    }

    void ejecutaHilos() throws InterruptedException {
        System.out.println("Ejecutando hilos...");
        
        for(Thread t : hilos){
            System.out.println("Iniciando hilo: " + t.getName());
            t.start();
            System.out.println("Hilo iniciado: " + t.getName());
        }
    
        System.out.println("Dormir hilo principal por " + DIEZ_SEC + " milisegundos...");
        Thread.sleep(DIEZ_SEC);
    
        System.out.println("Interrumpiendo hilos...");
        for(Thread t : hilos){
            System.out.println("Interrumpiendo hilo: " + t.getName());
            t.interrupt();
            System.out.println("Hilo interrumpido: " + t.getName());
        }
    
        System.out.println("Uniendo hilos...");
        for(Thread t : hilos){
            System.out.println("Uniendo hilo: " + t.getName());
            t.join();
            System.out.println("Hilo unido: " + t.getName());
        }
    
        System.out.println("Hilos ejecutados.");
    }    
}
