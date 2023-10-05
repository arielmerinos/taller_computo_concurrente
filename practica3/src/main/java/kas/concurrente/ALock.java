package kas.concurrente;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;

public class ALock implements Lock {
    ThreadLocal<Integer> mySlotIndex = new ThreadLocal<Integer>() {
        protected Integer initialValue() {
            return 0;
        }
    };

    private final int size;
    private final AtomicInteger tail = new AtomicInteger(0);
    private final AtomicBoolean[] flags;

    public ALock(int capacity) {
        size = capacity;
        flags = new AtomicBoolean[capacity];
        for (int i = 0; i < capacity; i++) {
            flags[i] = new AtomicBoolean();
        }
        flags[0].set(true);
    }

    @Override
    public void lock() {
        int slot = tail.getAndIncrement() % size;
        mySlotIndex.set(slot);

        while (!flags[slot].get()) {
            Thread.yield();
        }
    }

    @Override
    public void unlock() {
        int slot = mySlotIndex.get();
        flags[slot].set(false);
        flags[(slot + 1) % size].set(true);
    }
}