package kas.concurrente;

import java.util.concurrent.atomic.AtomicReference;


public class MCSLock implements Lock {
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;

    public MCSLock() {
        tail = new AtomicReference<QNode>(null);
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
    }
    
    @Override
    public void lock() {
        QNode qnode = myNode.get();
        QNode pred = tail.getAndSet(qnode);
        if (pred != null) {
            qnode.locked = true;
            pred.next = qnode;
            // Espera activamente hasta que el predecesor libere el bloqueo
            while (qnode.locked) {}
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        if (qnode.next == null) {
            if (tail.compareAndSet(qnode, null)) return;
            // Espera hasta que el predecesor llene su campo next
            while (qnode.next == null) {}
        }
        qnode.next.locked = false;
        qnode.next = null;
    }  

    class QNode {
        boolean locked = false;
        QNode next = null;
    }
    
}
