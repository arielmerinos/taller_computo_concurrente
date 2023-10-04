package kas.concurrente;

import java.util.concurrent.atomic.AtomicBoolean;

public class TTASLock implements Lock{
    AtomicBoolean state = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (true) {
            while (state.get()) {} // Espera activamente si el bloqueo está adquirido
            if (!state.getAndSet(true)) { // Intenta adquirir el bloqueo si está libre
                return;
            }
        }   
    }

    @Override
    public void unlock() {
        state.set(false); // Libera el bloqueo
    }
    
}
