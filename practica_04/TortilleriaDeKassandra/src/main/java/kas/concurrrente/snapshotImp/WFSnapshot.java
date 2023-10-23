package kas.concurrrente.snapshotImp;

import kas.concurrrente.snapshot.Snapshot;
import kas.concurrrente.stamped.StampedSnap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WFSnapshot<T> implements Snapshot<T> {
    private StampedSnap<T>[] aTable;

    public WFSnapshot(int capacity, T init) {
        aTable = new StampedSnap[capacity];

        for (int i = 0; i < capacity; i++) {
            aTable[i] = new StampedSnap<>(init);
        }
    }

    @Override
    public void update(T value) {
        int me = Integer.parseInt(Thread.currentThread().getName());
        StampedSnap<T> oldValue = aTable[me];
        /*
         * El cast es necesario porque collect devuelve un arreglo de StampedSnap<T>, sin embargo snap en StampedSnap
         * es de tipo List<T>, por lo que se debe hacer un cast a List<T> para que el compilador no se queje
         */
        StampedSnap<T> newValue = new StampedSnap<T>(oldValue.getStamp() + 1, value, (List<T>) Arrays.asList(collect()));
        aTable[me] = newValue;
    }

    @Override
    public List<T> scan() {
        StampedSnap<T>[] oldCopy, newCopy;
        oldCopy = collect();
        while (true) {
            newCopy = collect();
            boolean moved = false;
            for (int i = 0; i < aTable.length; i++) {
                if (oldCopy[i].getStamp() != newCopy[i].getStamp()) {
                    moved = true;
                    break;
                }
            }
            if (!moved) {
                return Arrays.stream(newCopy).map(StampedSnap::getValue).collect(Collectors.toList());
            }
            oldCopy = newCopy;
        }
    }

    private StampedSnap<T>[] collect() {
        return Arrays.copyOf(aTable, aTable.length);
    }
}
