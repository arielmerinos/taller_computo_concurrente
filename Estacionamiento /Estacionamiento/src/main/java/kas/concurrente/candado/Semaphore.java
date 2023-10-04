package kas.concurrente.candado;

/**
 * Interfaz que modela un semaforo
 * @version 1.0
 * @author Kassandra Mirael
 */
public interface Semaphore {
    
    /**
     * Metodo que nos retorna el numero de hilos permitidos
     * dentro de la seccion critica.
     * @return El numero de hilos permitido
     */
    public int getPermitsOnCriticalSection();

    /**
     * Metodo que adquiere el semaforo.
     */
    public void acquire();

    /**
     * Metodo que libera el semaforo.
     */
    public void release();
}
