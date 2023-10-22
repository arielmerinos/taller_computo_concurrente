package kas.concurrrente.snapshotImp;

import kas.concurrrente.snapshot.Snapshot;

public class SeqSnapshot<T> implements Snapshot<T> {
    private T[] aValue;
    
    @SuppressWarnings("unchecked")
    public SeqSnapshot(int capacity, T init){
        aValue = (T[]) new Object[capacity];

        for(int i = 0; i < aValue.length; i++){
            aValue[i] = init;
        }
    }


    @Override
    public synchronized void update(T v) {
        int ID = Integer.parseInt(Thread.currentThread().getName());
        aValue[ID] = v;
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized T[] scan() {
        T[] result = (T[]) new Object[aValue.length];
        for(int i = 0; i < aValue.length; i++){
            result[i] = aValue[i];
        }
        return result;
    }
    
}
