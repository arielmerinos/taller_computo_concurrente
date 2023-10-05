package kas.concurrente;

import java.util.concurrent.atomic.AtomicReference;

public class CLHLock implements Lock {
    class QNode {
        boolean locked = false;
    }

    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myPred;
    ThreadLocal<QNode> myNode;

    public CLHLock() {
        tail = new AtomicReference<QNode>(null);
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
        myPred = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return null;
            }
        };
    }
    
    @Override
    public void lock() {
        System.out.println(Thread.currentThread().getName() + " - Intentando adquirir el bloqueo...");
        
        QNode qnode = myNode.get();
        qnode.locked = true;
        QNode pred = tail.getAndSet(qnode);
        myPred.set(pred);
        if (pred != null) {
            System.out.println(Thread.currentThread().getName() + " - Esperando a que el predecesor libere el bloqueo...");
            while (pred.locked) {
                // 4. Utilizando una espera adaptativa
                Thread.yield(); // Hace que el thread actual vuelva a la disposición para permitir que otros threads se ejecuten.
            } 
        }

        System.out.println(Thread.currentThread().getName() + " - Bloqueo adquirido.");
    }
    

    @Override
    public void unlock() {
        System.out.println(Thread.currentThread().getName() + " - Intentando liberar el bloqueo...");
        
        QNode qnode = myNode.get();
        qnode.locked = false;

        System.out.println(Thread.currentThread().getName() + " - Bloqueo liberado.");

        // 2. y 3. Reinicializando myNode y haciendo que apunte al nodo predecesor
        myNode.set(myPred.get());

        // Introduce un tiempo de espera después de liberar el bloqueo
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}