package kas.concurrente.modelos;

import kas.concurrente.candado.Semaphore;

/**
 * Clase que modela un Lugar
 * El lugar consta de un id
 * un booleano que nos dice si esta dispoible
 * y un objeto del tipo filtro Modificado
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Lugar {
    private int id;
    private volatile boolean disponible;
    private Semaphore FiltroModificado;
    private int vecesEstacionado;

    /**
     * Metodo constructor
     * El lugar por defecto esta disponible
     * Pueden llegar un numero n de carros en el 
     * peor de los casos
     * veces estacionado sera el numero de veces que se han estacianado en el lugar
     * @param id El id del Lugar
     */
    public Lugar(int id){
    }

    /**
     * En este metodo se simula que se estaciona
     * PELIGRO: ESTAS ENTRANDO A LA SECCION CRITICA
     * Cambia el valor de disponible a false
     * Y se simula que vas por barbacoa
     * Al final, imprime un texto color ROJO diciendo que va salir
     * @throws InterruptedException Si algo falla
     */
    public void estaciona() throws InterruptedException{
        /*
         * Aui va tu codigo
         */
    }

    /**
     * En este metodo se genera la sumulación
     * Se genera un tiempo entre 1 y 5 segundos
     * Es pseudo aleatorio
     * @throws InterruptedException En caso de que falle
     */
    public void vePorBarbacoa() throws InterruptedException{
        /*
         * Aqui va tu codigo
         */
    }
    
}
