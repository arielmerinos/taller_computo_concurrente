package kas.concurrente.candadosImpl;

import kas.concurrente.candado.Lock;

/**
 * Clase que implementa el candado usando el Legendario
 * algoritmo de PeterGod.
 * @version 1.0
 * @author Kassandra Mirael
 */
public class PetersonLock implements Lock {
    private volatile boolean[] flag;
    private volatile int victim;

    public PetersonLock() {
        flag = new boolean[2];
        victim = -1;
    }

    @Override
    public void lock() {
        int i = Integer.parseInt(Thread.currentThread().getName());
        System.out.println("i = " + i);
        int j = 1 - i;
        flag[i] = true;
        victim = i;
        while (flag[j] && victim == i);
    }

    @Override
    public void unlock() {
        int i = Integer.parseInt(Thread.currentThread().getName());
        flag[i] = false;
    }
}
