package kas.concurrente.candadosImpl;

import java.util.concurrent.atomic.AtomicIntegerArray;
import kas.concurrente.candado.Semaphore;

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

    public void acquire() {
        int me;
        String threadName = Thread.currentThread().getName();

        if (threadName.startsWith("Thread-")) {
            me = Integer.parseInt(threadName.substring(7));
        } else {
            me = -1; // Asignar un valor no válido como identificador
        }

        if (me >= 0) { // Verificar que el identificador sea válido
            for (int i = 1; i <= maxLevel; ++i) {
                level.set(me, i);
                victim.set(i, me);
                boolean canEnterCriticalSection = true;

                for (int k = 0; k < level.length(); ++k) {
                    if (k != me) {
                        while (level.get(k) >= i && victim.get(i) == me) {
                            canEnterCriticalSection = false;
                            break;
                        }
                    }
                }

                if (canEnterCriticalSection) {
                    break; // Sal del ciclo si puedes entrar a la sección crítica
                }
            }

            // Critical section
            permitsOnCriticalSection++;
            if (permitsOnCriticalSection > maxHilosConcurrentes) {
                // Reset permits
                permitsOnCriticalSection = 0;
            }
        } else {
            // Manejar el caso en que el nombre del hilo no es válido
            // Puedes imprimir un mensaje de error o tomar otra acción aquí
        }
    }

    @Override
    public void release() {
        int me;
        String threadName = Thread.currentThread().getName();

        if (threadName.startsWith("Thread-")) {
            me = Integer.parseInt(threadName.substring(7));
        } else {
            me = -1; // Asignar un valor no válido como identificador
        }

        if (me >= 0) { // Verificar que el identificador sea válido
            permitsOnCriticalSection--;
            level.set(me, 0);
        } else {
            // Manejar el caso en que el nombre del hilo no es válido
            // Puedes imprimir un mensaje de error o tomar otra acción aquí
        }
    }

}
