package kas.concurrente.tenedor;

/**
 * Interfaz que modela un tenedor.
 * @author Kassandra Mirael
 * @version 1.0
 */
public interface Tenedor {
    
    /**
     * Metodo que nos permite tomar el tenedor.
     */
    public void tomar();

    /**
     * Metodo que nos permite soltar el tenedor.
     */
    public void soltar();

    /**
     * Metodo que nos retorna el id
     * @return El id
     */
    public int getId();

    /**
     * Metodo que asigna un nuevo id
     * @param id El nuevo id
     */
    public void setId(int id);


    /**
     * Metodo que nos retorna el numero de veces
     * que se tomo el tenedor.
     * @return El numero de veces que se tomo
     */
    public int getVecesTomado();

    public void setVecesTomado(int vecesTomado);
}
