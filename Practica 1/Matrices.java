import java.util.ArrayList;
import java.util.List;

public class Matrices {

    public static int[][] multMatrices(int[][] matA, int[][] matB){
        int[][] finalMat = new int[matA.length][matB[0].length];

        for (int i = 0; i < matA.length; i++) {
            for (int j = 0; j < matB[0].length; j++) {
                for (int k = 0; k < matB[0].length; k++) {
                    finalMat[i][j]+= matA[i][k]*matB[k][j];
                }
            }
        }
        return finalMat;
    }
    public int[][] multMatThreads(int fila, int columna, int[][] matA, int[][] matB, int[][] finalMat){
        int suma  = 0;
        for (int k = 0; k < matA.length; k++) {
            suma += matA[fila][k]*matB[k][columna];
        }
        finalMat[fila][columna] = suma;
        System.out.println("Hola soy el hilo de la pareja: "+ Thread.currentThread().getName()+" mi suma fue: "+finalMat[fila][columna]);//Pedimos el nombre del hilo pidiendo primero que se seleccione el Hilo
        return finalMat;
    }

    public static void imprimirMatriz(int[][] matriz) {
        for (int[] fila : matriz) {
            for (int elem : fila) {
                System.out.print(elem + " | ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int[][] matrizA = {
                {1, 2, 3},
                {4, 5, 6},
                {2, 1, 1},

        };

        int[][] matrizB = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        int[][] finalMat = multMatrices(matrizA, matrizB);
        List<Thread> list = new ArrayList<>();


        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizA.length; j++) {
                int finalI = i;
                int finalJ = j;
                Thread t = new Thread( //Creamos un hilo
                        new Runnable() { //Usamos un new Runnable() para implementarlo directamente aqui
                            Matrices m = new Matrices(); //Creamos instancia de la clase

                            @Override
                            public void run() { //definimos manualmente el comportamiento del Hilo
                                m.multMatThreads(finalI, finalJ,matrizA,matrizB,finalMat);
                            }
                        },
                        String.format("%d,%d",finalI,finalJ));
                list.add(t);
                t.start();

            }
        }

        for (Thread t:list) {
            t.join();
        }

        System.out.println("A");
        imprimirMatriz(matrizA);

        System.out.println("\nB");
        imprimirMatriz(matrizB);

        System.out.println("\nMatriz final");
        imprimirMatriz(finalMat);
    }
}
