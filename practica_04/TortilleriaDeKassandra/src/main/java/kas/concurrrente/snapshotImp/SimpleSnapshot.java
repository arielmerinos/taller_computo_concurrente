package kas.concurrrente.snapshotImp;

import java.util.Arrays;
import java.util.List;

import kas.concurrrente.snapshot.Snapshot;
import kas.concurrrente.stamped.StampedValue;

public class SimpleSnapshot<T> implements Snapshot<T>{
    /**
     * Arreglo de registros, almacena todos los registros 
     * del arreglo de procesos que realizan la llamada a update
     */
    private StampedValue<T>[] aTable;

    /**
     * Constructor de la clase
     * inicializa el arreglo con el valor init para cada elemento hasta llegar a la capacidad
     * y un sello termporal de 0
     * @param capacity Capacidad del arreglo
     * @param init Valor inicial de los elementos del arreglo
     */
    public SimpleSnapshot(int capacity, T init) {
        aTable = (StampedValue<T>[]) new StampedValue[capacity];

        for(int i = 0; i < capacity; i++){
            aTable[i] = new StampedValue<>(init);
        }
    }

    /**
     * Actualiza el valor en el arreglo para el hilo actual 
     * incrementando el sello temporal del valor anterior en 1
     */
    @Override
    public void update(T value) {
        int me = Integer.parseInt(Thread.currentThread().getName());
        StampedValue<T> oldValue = aTable[me];
        StampedValue<T> newValue = new StampedValue<>((oldValue.getStamp())+1 ,value);
        aTable[me] = newValue;
    }

    /**
     * Metodo que construye una vista instantanea
     * devuelve una copia del arreglo de registros
     * 
     * Toma repeditamente copias del arreglo hasta que dos copias consecutivas sean iguales
     * lo que indica que no se han realizado cambios en el arreglo
     * luego devuelve una copia de los valores del arreglo
     */
    @Override
    public List<T> scan() {
        StampedValue<T>[] oldCopy, newCopy;
        oldCopy = collect();
        collect : while (true) {
            newCopy = collect();
            if(!Arrays.equals(oldCopy, newCopy)){
                oldCopy = newCopy;
                continue collect;
            }
            List<T> result = Arrays.asList((T[]) newCopy);
            return result;
        }
    }

    /**
     * Metodo que obtiene una copia de los valores del arreglo
     * @return La copia de los valores del arreglo
     */
    private StampedValue<T>[] collect(){
        StampedValue<T>[] copy = (StampedValue<T>[]) new StampedValue[aTable.length];
        for(int j = 0; j < aTable.length; j++){
            copy[j] = aTable[j];
        }
        return copy;
    }
    
}
