package kas.concurrrente.snapshotImp;

import kas.concurrrente.snapshot.Snapshot;

import java.util.ArrayList;
import java.util.List;

/*
 * Clase que implementa la interfaz Snapshot
 * Esta implementación es secuencial porque todos los métodos están sincronizados
 * lo que significa que sólo un hilo puede ejecutarlos a la vez
 * @param <T> Tipo de dato de los elementos del arreglo
 * @see Snapshot
 */
public class SeqSnapshot<T> implements Snapshot<T> {
    /**
     * Arreglo de registros, almacena todos los registros
     * @see Snapshot
     */
    private List<T> aValue;

    /**
     * Constructor de la clase
     * inicializa el arreglo con el valor init para cada elemento hasta llegar a la capacidad
     * @param capacity Capacidad del arreglo
     * @param init Valor inicial de los elementos del arreglo
     */
    public SeqSnapshot(int capacity, T init){
        aValue = new ArrayList<>(capacity);
        for(int i = 0; i < capacity; i++){
            aValue.add(init);
        }
    }

    @Override
    public synchronized void update(T v) {
        int ID = Integer.parseInt(Thread.currentThread().getName());
        aValue.set(ID, v);
    }

    /**
     * Metodo que construye una vista instantanea
     * devuelve una copia del arreglo de registros
     */
    @Override
    public synchronized List<T> scan() {
        return new ArrayList<>(aValue);
    }
}
