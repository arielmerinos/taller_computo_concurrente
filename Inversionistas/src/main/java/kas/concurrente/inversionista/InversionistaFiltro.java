package kas.concurrente.inversionista;
import kas.concurrente.candados.Semaphore;
/**
 * Clase que modela al inversionista, pero esta vez
 * usando el filtro.
 * @version 1.1
 * @author Kassandra Mirael
 */
public class InversionistaFiltro extends Inversionista {

    private Semaphore semaforo;

    public InversionistaFiltro(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    @Override
    public void entraALaMesa() {
        try {
            semaforo.acquire();
            super.entraALaMesa();  
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void tomaTenedores() {
        getTenedorIzq().tomar();
        getTenedorDer().tomar();
    }

    @Override
    public void sueltaTenedores() {
        getTenedorIzq().soltar();
        getTenedorDer().soltar();
    }
    
}