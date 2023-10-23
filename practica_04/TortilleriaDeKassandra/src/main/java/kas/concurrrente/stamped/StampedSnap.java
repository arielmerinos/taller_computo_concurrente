package kas.concurrrente.stamped;

import java.util.List;

/**
 * Clase que modela un Snapshot
 * @param <T> Tipo de dato de los elementos del arreglo
 * @see Snapshot
 */
public class StampedSnap<T> {
    /**
     * Sello temporal único para cada actualización
     */
    private static long timestampConter = 0;
    private long stamp;
    private T value;
    private List<T> snap;

    /**
     * Constructor de la clase
     * inicialmente el sello se inicializa en 0
     * @param value
     */
    public StampedSnap(T value){
        this.stamp = getNextTimestamp(); //asigna el siguiente sello temporal único para cada actualización
        this.value = value;
        this.snap = null;
    }

    public StampedSnap(long label, T value, List<T> snap){
        this.stamp = label;
        this.value = value;
        this.snap = snap;
    }

    public long getStamp(){
        return stamp;
    }

    public T getValue(){
        return value;
    }

    public List<T> getSnap(){
        return snap;
    }

    public void setStamp(long stamp){
        this.stamp = stamp;
    }

    public void setValue(T value){
        this.value = value;
    }

    public void setSnap(List<T> snap){
        this.snap = snap;
    }
    
    /**
     * Método para obtener el siguiente sello temporal único 
     */
    private synchronized static long getNextTimestamp(){
        return timestampConter++;
    }
}
