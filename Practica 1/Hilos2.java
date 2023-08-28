/**
 * Clase que ejemplifica los Hilos
 * @author Sunny Mirael
 * @version 1.1
 */
public class Hilos2 {
    
    public void imprimeAlgo(){ //Metodazo que hara algo con estilo B)
        System.out.println("Imprimiendo con Estilo xD");
    }

    public static void main(String[] args) throws InterruptedException{
        Thread t1 = new Thread( //Creamos un hilo
            new Runnable() { //Usamos un new Runnable() para implementarlo directamente aqui
            Hilos2 h = new Hilos2(); //Creamos instancia de la clase
            
            @Override
            public void run() { //definimos manualmente el comportamiento del Hilo
                h.imprimeAlgo();
            }
        },
         "Hilo1");//Nombre que le damos al hilo.
        
        t1.start(); //Inicializamos el hilo
        t1.join();//?????
    }
}