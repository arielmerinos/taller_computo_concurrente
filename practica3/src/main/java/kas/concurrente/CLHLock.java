package kas.concurrente;

import java.util.concurrent.atomic.AtomicReference;

public class CLHLock implements Lock {
    class QNode {
        volatile boolean locked = false;
    }

    private AtomicReference<QNode> tail;
    private ThreadLocal<QNode> myPred;
    private ThreadLocal<QNode> myNode;

    public CLHLock() {
        tail = new AtomicReference<>(new QNode());
        myNode = ThreadLocal.withInitial(QNode::new);
        myPred = ThreadLocal.withInitial(() -> null);
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        qnode.locked = true;
        QNode pred = tail.getAndSet(qnode);
        myPred.set(pred);

        while (pred.locked) {
            // Espera adaptativa (puedes ajustar esta parte)
            Thread.yield();
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        qnode.locked = false;

        // Reinicializa myNode y hace que apunte al nodo predecesor
        myNode.set(myPred.get());
    }
}