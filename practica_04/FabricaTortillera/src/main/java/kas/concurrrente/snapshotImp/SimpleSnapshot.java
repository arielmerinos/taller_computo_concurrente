package kas.concurrrente.snapshotImp;

import java.util.Arrays;

import kas.concurrrente.snapshot.Snapshot;
import kas.concurrrente.stamped.StampedValue;

/**
 * Clase que implementa un Snapshot
 * @author Kassandra Mirael
 * @version 1.0
 */
public class SimpleSnapshot<T> implements Snapshot<T>{
    /*
     * Guarda el arreglo de valores con sus respectivos sellos de tiempo
     */
    private StampedValue<T>[] aTable;

    /**
     * Metodo constructor
     * @param capacity La capacidad
     * @param init El valor de inicio por celda
     */
    @SuppressWarnings("unchecked")
    public SimpleSnapshot(int capacity, T init) {
        aTable = (StampedValue<T>[]) new StampedValue[capacity];

        for(int i = 0; i < capacity; i++){
            aTable[i] = new StampedValue<>(init);
        }
    }

    /**
     * Metodo que escribe el valor v en el registro
     * del proceso que realiza la llamada
     * @param value La variable a escribir en el arreglo
     */
    @Override
    public void update(T value) {
        int me = Integer.parseInt(Thread.currentThread().getName());
        StampedValue<T> oldValue = aTable[me];
        StampedValue<T> newValue = new StampedValue<>((oldValue.getStamp())+1,value);
        aTable[me] = newValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T[] scan() {
        StampedValue<T>[] oldCopy, newCopy;
        oldCopy = collect();
        collect : while (true) {
            newCopy = collect();
            if(!Arrays.equals(oldCopy, newCopy)){
                oldCopy = newCopy;
                continue collect;
            }
            T[] result = (T[]) new Object[aTable.length];
            for(int j = 0; j < aTable.length; j++) {
                result[j] = newCopy[j].value();
            }
            return result;
        }
    }

    /**
     * Metodo que obtiene una copia de los valores del arreglo
     * @return La copia de los valores del arreglo
     */
    @SuppressWarnings("unchecked")
    private StampedValue<T>[] collect(){
        StampedValue<T>[] copy = (StampedValue<T>[]) new StampedValue[aTable.length];
        for(int j = 0; j < aTable.length; j++){
            copy[j] = aTable[j];
        }
        return copy;
    }
    
}
