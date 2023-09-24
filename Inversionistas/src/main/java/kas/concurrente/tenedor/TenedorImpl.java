package kas.concurrente.tenedor;

/**
 * Clase que implementa el tenedor
 */
public class TenedorImpl implements Tenedor {
    private int id;
    private volatile boolean isTaken;
    private int vecesTomado;

    public TenedorImpl(int id) {
        this.id = id;
        this.isTaken = false;
        this.vecesTomado = 0;
    }

    @Override
    public synchronized void tomar() {
        while (isTaken) {
            try {
                wait(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        isTaken = true;
        vecesTomado++;
    }

    @Override
    public synchronized void soltar() {
        isTaken = false;
        notify();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getVecesTomado() {
        return vecesTomado;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setVecesTomado(int vecesTomado) {
        this.vecesTomado = vecesTomado;
    }
}
