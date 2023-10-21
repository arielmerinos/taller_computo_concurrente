package kas.concurrrente.snapshot;

/**
 * Interfaz que modela un Snapshot
 * @author Kassandra Mirael
 */
public interface Snapshot<T> {
    
    /**
     * Metodo que escribe el valor v en el registro
     * del proceso que realiza la llamada
     * @param value La variable a escribir en el arreglo
     */
    public void update(T value);

    /**
     * Metodo que construyee una vista instantanea
     * del arreglo de registros
     * @return El arreglo de registros
     */
    public T[] scan();
}
