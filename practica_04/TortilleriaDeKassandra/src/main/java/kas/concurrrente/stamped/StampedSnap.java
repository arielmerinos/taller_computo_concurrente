package kas.concurrrente.stamped;

public class StampedSnap<T> {
    private long stamp;
    private T value;
    private T[] snap;

    public StampedSnap(T value){
        this.stamp = 0;
        this.value = value;
        this.snap = null;
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
}
