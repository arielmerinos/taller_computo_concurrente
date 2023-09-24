package kas.concurrente.inversionista;

import kas.concurrente.tenedor.Tenedor;

/**
 * Clase abstracta que modela al inversionista.
 * El inversionista tiene 2 tenedores a sus lados.
 * El inversionista posee un ID para que se pueda identificar.
 * El inversionista tiene una variable que indica el numero de veces que ha comido.
 * @version 1.0
 * @author Kassandra Mirael
 */
public abstract class Inversionista implements Runnable {
    private int id;
    private Tenedor tenedorIzq;
    private Tenedor tenedorDer;
    private int vecesComido;

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("Inversionista " + id + " está pensando.");
                piensa();
    
                System.out.println("Inversionista " + id + " está tratando de tomar tenedores.");
                tomaTenedores();
                
                System.out.println("Inversionista " + id + " ha tomado los tenedores y ahora está comiendo.");
                come();
    
                System.out.println("Inversionista " + id + " ha terminado de comer y está soltando los tenedores.");
                sueltaTenedores();
            }
        } catch (InterruptedException e) {
            System.out.println("Inversionista " + id + " fue interrumpido.");
            Thread.currentThread().interrupt();
        }
    }
    

    /**
     * Metodo que nos permite entrar a la mesa.
     * El inversionista por fin dejo de pensar y de escribir en su
     * servilleta y se digna en entrar.
     * PRIMERO toma los tenedores.
     * DESPUES come.
     * FINALMENTE los suelta para que los demas los puedan usar.
     * @throws InterruptedException <Escribe porque se lanzaria esta exception>
     */
    public void entraALaMesa() throws InterruptedException{
        tomaTenedores();
        come();
        sueltaTenedores();
    }

    /**
     * Una vez que termino de pensar sobre finanzas el inversionista
     * se prepara para comer.
     * El inversionista le toma un par de milisegundos comer.
     * ESTA ES LA SECCION CRITICA, SIGNIFICA PELIGRO
     * Incrementa el numero de veces que ha comido.
     * @throws InterruptedException <Escribe porque se lanzaria esta exception>
     */
    public void come() throws InterruptedException{
        Thread.sleep(generaTiempoDeEspera());  // Simulate eating time
        setVecesComido(getVecesComido() + 1);  // Increment the eating count
    }

    /**
     * Metodo que hace que el inversionista piense.
     * El inversionista pensara por una par de milisegundos.
     * Esto pasa antes de que tome sus tenedores.
     * @throws InterruptedException <Escribe porque se lanzaria esta exception>
     */
    public void piensa() throws InterruptedException {
        Thread.sleep(generaTiempoDeEspera());  // Simulate thinking time
    }

    /**
     * Metodo que nos permite tomar los tenedores.
     * Los toma uno por uno.
     */
    public abstract void tomaTenedores();

    /**
     * Metodo que hace que el inversionista suelte ambos tenedores una vez
     * que terminara de comer. 
     * De esta manera otro los puede usar.
     * Suelta los tenedores uno por uno.
     */
    public abstract void sueltaTenedores();
    
    /**
     * Metodo que genera un numero pseudoaleatorio entre 1 y 10
     * @return El tiempo de espera
     */
    private long generaTiempoDeEspera(){
        double i=Math.random()*10.0;
        return (long)i ;
    }

    /*
     * Rellena Getter and Setters primero
     * Documenta los metodos.
     * Cuando acabes borra estew comentario
     */
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;

    }

    public Tenedor getTenedorIzq(){
        return tenedorIzq;
    }

    public void setTenedorIzq(Tenedor t){
        this.tenedorIzq = t;

    }

    public Tenedor getTenedorDer(){
        return tenedorDer;
    }

    public void setTenedorDer(Tenedor t){
        this.tenedorDer = t;

    }

    public int getVecesComido(){
        return vecesComido;
    }

    public void setVecesComido(int vecesComido){
        this.vecesComido = vecesComido;
    }
}
