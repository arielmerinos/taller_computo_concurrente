package kas.concurrrente.stamped;

/**
 * Metodo que modela un StampedValue
 * @author Kassandra Mirael
 */
public class StampedValue<T> {
    private long stamp;
    private T value;

    public static StampedValue MIN_VALUE = new StampedValue(null);

    public StampedValue(T init){
        this.stamp = 0;
        this.value = init;
    }

    public StampedValue(long stamp, T value){
        this.stamp = stamp;
        this.value = value;
    }

    public static StampedValue max(StampedValue x, StampedValue y){
        return (x.stamp > y.stamp) ? x:y;
    }

    public long getStamp(){
        return stamp;
    }

    public T value(){
        return value;
    }

    public void setStamp(long stamp){
        this.stamp = stamp;
    }

    public void setValue(T value){
        this.value = value;
    }
}