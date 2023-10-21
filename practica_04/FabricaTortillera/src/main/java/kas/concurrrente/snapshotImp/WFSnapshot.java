package kas.concurrrente.snapshotImp;

import kas.concurrrente.snapshot.Snapshot;
import kas.concurrrente.stamped.StampedSnap;

/**
 * Clase que implementa un Snapshot
 * @author Kassandra Mirael
 * @version 1.0 
 */
public class WFSnapshot<T> implements Snapshot<T>{
    private StampedSnap<T>[] aTable;

    /**
     * MEtodo constructor
     * @param capacity La capacidad
     * @param init El valor de inicio por celda
     */
    public WFSnapshot(int capacity, T init){

    }

    @Override
    public void update(T value) {
        /**
         * Aqui va el codigo
         */
    }

    @Override
    public T[] scan() {
        /**
         * Aqui va el codigo
         */
        return null;
    }
    
    /**
     * Metodo que obtiene una copia de los valores del arreglo
     * @return La copia de los valores del arreglo
     */
    private StampedSnap<T>[] collect(){
        return null;
    }

    public StampedSnap<T>[] getATable(){
        return null;
    }

    /**
     * Metodo que obtiene el valor de acuerdo a la posicion aTable
     * @param pos La posicion de aTable
     * @return El valor
     */
    public T getStampedSnap(int pos){
        return null;
    }
}
