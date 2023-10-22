package kas.concurrrente.stamped;

import java.util.Date;

/**
 * CLase que modela un StampedSnap
 * @author Kassandra Mirael
 * @version 1.0
 */
public class StampedSnap<T> {
    /*
     * Guarda el selo temporal
     */
    private long stamp;
    /*
     * Guarda el valor genérico
     */
    private T value;
    /*
     * Guarda el arreglo de valores
     */
    private T[] snap;
    /*
     * Guarda el timestamp, útil para generar una marca de tiempo
     */
    private Date timestamp;


    public StampedSnap(T value){
        this.stamp = 0;
        this.value = value;
        this.snap = null;
        this.timestamp = new Date();
    }

    public StampedSnap(long label, T value, T[] snap){
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

    public T[] getSnap(){
        return snap;
    }

    public void setStamp(long stamp){
        this.stamp = stamp;
    }

    public void setValue(T value){
        this.value = value;
    }

    public void setSnap(T[] snap){
        this.snap = snap;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
