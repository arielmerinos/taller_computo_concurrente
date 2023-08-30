import java.util.ArrayList;
import java.util.List;

/**
 * Clase que modela un contador concurrente.
 */
public class Contador implements Runnable {

    private static final int RONDAS = 10000;
    private int valor;
    private int valor2;
    private String cadena;

    /** Metodo constructor. */
    public Contador() {
        this.valor = 0;
        this.valor2 = 0;
        this.cadena = "";

    }


    public int getValor2() {
        return valor2;
    }

    public void setValor2(int valor2) {
        this.valor2 = valor2;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    /**
     * Metodo que obtiene el valor.
     * @return El valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * Metodo que asigna un nuevo valor.
     * @param valor El nuevo valor
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public void run() {
        int ID = Integer.parseInt(Thread.currentThread().getName());
        if (ID<5) {
            ejemploEscribe();
        } else if (ID<7){
            suma();
        } else  {
            suma2();
        }
    }
    /**
     * Metodo que concatena cadenas por cada hilo. Si no se usa sinchronized,
     * la cadena se sobreesrcribe por un hilo y no sale todo lo que se espera.
     */
    public synchronized void ejemploEscribe() {
        cadena += "Escribe hilo: "+Thread.currentThread().getName()+"; ";
    }

    /**
     * Metodo que suma
     */
    public void suma() {
        for(int i = 0; i < RONDAS; ++i){
            valor = valor + 1;
        }
    }
    /**
     * Metodo que suma y guarda un segundo valor, 300000
     */
    public void suma2()  {
        for(int i = 0; i < RONDAS; ++i){
            valor2 = valor2 + 1;
        }
    }

/*     public synchronized void suma(){
        for(int i = 0; i < RONDAS; ++i){
            valor = valor + 1;
        }
    }*/

/*     public void suma(){
        synchronized(this){
            for(int i = 0; i < RONDAS; ++i){
                valor = valor + 1;
            }
        }
    }*/

    public static void main(String[] args) throws InterruptedException {

        Contador c = new Contador();
        List<Thread> list = new ArrayList<>();
        //Hilos para el ejemplo de concatenar cadenas. Al usar sinchronized no es necesario usar sleep
        for (int i=0;i<5 ;i++ ) {
            Thread t = new Thread(c,Integer.toString(i));
            list.add(t);
            t.start();
        }
        //Hilos para las sumas, usamos sleep, por eso los separamos de los primeros hilos
        for (int i=5;i<10 ;i++ ) {
            Thread t = new Thread(c,Integer.toString(i));
            list.add(t);
            t.start();
            Thread.sleep(1);
        }
        for (Thread t:list) {
            t.join();
        }
        System.out.println("Primera suma: " + c.getValor());
        System.out.println("Segunda suma: " + c.getValor2());
        System.out.println("Ejemplo con sinchronized: "+c.cadena);

    }

}
