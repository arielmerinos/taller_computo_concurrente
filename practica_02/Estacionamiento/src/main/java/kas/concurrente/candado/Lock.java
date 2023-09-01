package kas.concurrente.candado;

/**
 * Interfaz que modela un candado
 * @version 1.0
 * @author Kassandra Mirael 
 */
public interface Lock {
    
    /**
     * Metodo que bloquea el acceso a la
     * seccion critica.
     */
    public void lock();

    /**
     * Metodo que desbloque el acceso a la
     * seccion critica.
     */
    public void unlock();
    
}
