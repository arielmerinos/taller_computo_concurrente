
package kas.concurrente.candadosImpl;

import java.util.concurrent.atomic.AtomicIntegerArray;
import kas.concurrente.candados.Semaphore;

public class Filtro implements Semaphore {
    private int maxLevel;
    private AtomicIntegerArray level;
    private AtomicIntegerArray victim;
    private int maxHilosConcurrentes;
    private int permitsOnCriticalSection;

    public Filtro(int hilos, int maxHilosConcurrentes) {
        this.maxLevel = hilos - 1;
        this.level = new AtomicIntegerArray(hilos);
        this.victim = new AtomicIntegerArray(hilos);
        this.maxHilosConcurrentes = maxHilosConcurrentes;
        this.permitsOnCriticalSection = 0;
    }

    @Override
    public int getPermitsOnCriticalSection() {
        return permitsOnCriticalSection;
    }

    @Override
    public void acquire() {
        int me = Integer.parseInt(Thread.currentThread().getName());
        for (int i = 1; i <= maxLevel; ++i) {
            level.set(me, i);
            victim.set(i, me);
            for (int k = 0; k < level.length(); ++k) {
                if (k != me) {
                    while ((level.get(k) >= i && victim.get(i) == me));
                }
            }
        }
        // Critical section
        permitsOnCriticalSection++;
        if (permitsOnCriticalSection > maxHilosConcurrentes) {
            // Reset permits
            permitsOnCriticalSection = 0;
        }
    }

    @Override
    public void release() {
        int me = Integer.parseInt(Thread.currentThread().getName());
        permitsOnCriticalSection--;
        level.set(me, 0);
    }
}
