package kas.concurrente.candadosImpl;

import kas.concurrente.candados.Semaphore;

/**
 * Clase que modela el Algoritmo del Filtro Modificado
 * @version 1.0
 * @author Kassandra Mirael
 */
public class Filtro implements Semaphore {

    /**
     * Constructor del Filtro
     * @param hilos El numero de Hilos Permitidos
     * @param maxHilosConcurrentes EL numero de hilos concurrentes simultaneos
     */
    public Filtro(int hilos, int maxHilosConcurrentes) {
        /**
         * AQUI VA TU CODIGO
         */
    }

    @Override
    public int getPermitsOnCriticalSection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPermitsOnCriticalSection'");
    }

    @Override
    public void acquire() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'acquire'");
    }

    @Override
    public void release() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'release'");
    }
    
}
