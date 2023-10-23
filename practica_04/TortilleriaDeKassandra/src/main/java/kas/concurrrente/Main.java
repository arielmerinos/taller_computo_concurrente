package kas.concurrrente;

import java.util.*;
import kas.concurrrente.snapshotImp.WFSnapshot;

public class Main implements Runnable {
    
    private static WFSnapshot<Long> snapshot = new WFSnapshot<>(10, 0L);

    @Override
    public void run() {
        int threadId = Integer.parseInt(Thread.currentThread().getName());
        
        // actualiza el snapshot con el actual
        long currentTime = System.currentTimeMillis();
        snapshot.update(currentTime);
        System.out.println("Thread " + threadId + " entered at: " + new Date(currentTime));
        
        // obtiene un snapshot y lo ordena basado en el tiempo
        List<Long> sortedSnapshot = new ArrayList<>(snapshot.scan());
        Collections.sort(sortedSnapshot);
        
        // imprime los snapshots ordenados
        System.out.println("Order of service based on timestamps:");
        for (Long time : sortedSnapshot) {
            System.out.println(new Date(time));
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Main());
            thread.setName(String.valueOf(i));
            thread.start();
            try {
                Thread.sleep(100); // agregamos un pequeño delay para que los threads no se ejecuten todos al mismo tiempo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
