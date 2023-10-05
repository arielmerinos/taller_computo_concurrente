package kas.concurrente;

import java.util.concurrent.atomic.AtomicReference;

public class MCSLock implements Lock {

    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;

    public MCSLock() {
        tail = new AtomicReference<QNode>(null);
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                System.out.println(Thread.currentThread().getName() + " - Inicializando nodo para el hilo.");
                return new QNode();
            }
        };
    }
    
    @Override
    public void lock() {
        System.out.println(Thread.currentThread().getName() + " - Intentando adquirir el bloqueo...");
        
        QNode qnode = myNode.get();
        QNode pred = tail.getAndSet(qnode);
        if (pred != null) {
            System.out.println(Thread.currentThread().getName() + " - Hay un predecesor. Esperando...");
            qnode.locked = true;
            pred.next = qnode;
            // Espera activamente hasta que el predecesor libere el bloqueo
            while (qnode.locked) {}
        }
        
        System.out.println(Thread.currentThread().getName() + " - Bloqueo adquirido.");
    }

    @Override
    public void unlock() {
        System.out.println(Thread.currentThread().getName() + " - Intentando liberar el bloqueo...");
        
        QNode qnode = myNode.get();
        if (qnode.next == null) {
            if (tail.compareAndSet(qnode, null)) {
                System.out.println(Thread.currentThread().getName() + " - No hay sucesor. Liberando tail...");
                return;
            }
            // Espera hasta que el predecesor llene su campo next
            System.out.println(Thread.currentThread().getName() + " - Esperando a que el sucesor llene su campo next...");
            while (qnode.next == null) {}
        }
        
        System.out.println(Thread.currentThread().getName() + " - Notificando al sucesor para que adquiera el bloqueo...");
        qnode.next.locked = false;
        qnode.next = null;

        System.out.println(Thread.currentThread().getName() + " - Bloqueo liberado y sucesor notificado.");
    }  

    class QNode {
        volatile boolean locked = false;
        volatile QNode next = null;
    }
    
}
