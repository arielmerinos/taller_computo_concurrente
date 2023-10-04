package kas.concurrente;

import java.util.concurrent.atomic.AtomicBoolean;

public class TASLock implements Lock {
    AtomicBoolean state = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (state.getAndSet(true)) {} // Bucle hasta obtener el bloqueo
    }

    @Override
    public void unlock() {
        state.set(false); // Libera el bloqueo
    }
    
}
