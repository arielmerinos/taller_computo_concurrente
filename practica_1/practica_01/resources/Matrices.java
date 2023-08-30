import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Matrices {

    public static int[][] readMatrixFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int[][] matrix = null;
        int row = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.trim().split(" ");
            if (tokens.length == 1 && tokens[0].isEmpty()) continue; // Saltar líneas vacías
            if (matrix == null) {
                matrix = new int[tokens.length][tokens.length];
            }
            for (int col = 0; col < tokens.length; col++) {
                matrix[row][col] = Integer.parseInt(tokens[col]);
            }
            row++;
        }
        reader.close();
        if (matrix == null) {
            throw new IllegalArgumentException("El archivo estaba vacío o mal formateado");
        }
        return matrix;
    }

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
//        System.out.println("Hola soy el hilo de la pareja: "+ Thread.currentThread().getName()+" mi suma fue: "+finalMat[fila][columna]);//Pedimos el nombre del hilo pidiendo primero que se seleccione el Hilo
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

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Probrar con la matriz de 10X10 escribe: 1\n" +
                            "Probrar con la matriz de 100X100 escribe: 2\n" +
                            "Probrar con la matriz de 1000X1000 escribe: 3\n");
        int choice1 = scanner.nextInt();
        String fileName = "";
        if (choice1 == 1) {
            fileName = "practica_1/practica_01/resources/matrix10.txt";
        } else if (choice1 == 2) {
            fileName = "practica_1/practica_01/resources/matrix100.txt";
        } else if (choice1 == 3){
            fileName = "practica_1/practica_01/resources/matrix1000.txt";

        }

        System.out.println("Para probar secuencialmente escriba: 1\n" +
                            "Para probar concurrentemente escriba: 2");
        int choice2 = scanner.nextInt();
        Boolean isConcurrent = false;
        if (choice2 == 1) {
            isConcurrent = false;
        } else if (choice2 == 2) {
            isConcurrent = true;
        } else {
            System.out.println("Opción no válida.");
            return;
        }

        scanner.close();



        int[][] matrizA = readMatrixFromFile(fileName);
        int[][] matrizB = readMatrixFromFile(fileName);
//        int[][] matrizA = {
//                {1, 2, 3},
//                {4, 5, 6},
//                {2, 1, 1},
//
//        };
//
//        int[][] matrizB = {
//                {9, 8, 7},
//                {6, 5, 4},
//                {3, 2, 1}
//        };
        long startTime = System.nanoTime();
        int[][] finalMat = new int[matrizA.length][matrizB.length];
        if (isConcurrent) {
            List<Thread> list = new ArrayList<>();
            for (int i = 0; i < matrizA.length; i++) {
                for (int j = 0; j < matrizA.length; j++) {
                    int finalI = i;
                    int finalJ = j;
                    int[][] finalMat1 = finalMat;
                    Thread t = new Thread( //Creamos un hilo
                            new Runnable() { //Usamos un new Runnable() para implementarlo directamente aqui
                                Matrices m = new Matrices(); //Creamos instancia de la clase

                                @Override
                                public void run() { //definimos manualmente el comportamiento del Hilo
                                    m.multMatThreads(finalI, finalJ, matrizA, matrizB, finalMat1);
                                }
                            },
                            String.format("%d,%d", finalI, finalJ));
                    list.add(t);
                    t.start();

                }
            }

            for (Thread t : list) {
                t.join();
            }
        }else{
            finalMat = multMatrices(matrizA,matrizB);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("A");
        imprimirMatriz(matrizA);

        System.out.println("\nB");
        imprimirMatriz(matrizB);

        System.out.println("\nMatriz final");
        imprimirMatriz(finalMat);
        System.out.println("Tiempo tardado (concurrente): " + duration + " nanosegundos");

    }
}
