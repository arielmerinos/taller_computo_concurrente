import java.util.List;
import java.util.ArrayList;

/**
 * Clase que ejemplifica los Hilos implementando Runnable
 */
public class Hilos implements Runnable {

    @Override
    public void run() { 
        int a = 10;
        int b = 12;
        int ID = Integer.parseInt(Thread.currentThread().getName());
        if(ID == 1){
            System.out.println("Soy el hilo 1");
        }else{
            System.out.println("Hola soy el: "+ Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Hilos h = new Hilos();

        List<Thread> list = new ArrayList<>();
        for (int i=0;i<10 ;i++ ) {
            Thread t = new Thread(h,Integer.toString(i));
            list.add(t);
            t.start();
        }

        for (Thread t:list) {
            t.join();
        }
    }
}