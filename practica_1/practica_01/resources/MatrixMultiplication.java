import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

// Clase para la multiplicación de matrices usando concurrencia
class ConcurrentMatrixMultiplication {
    // Método para leer matriz desde archivo
    // Método para leer matriz desde archivo
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

    // Clase interna que representa una tarea de multiplicación para una fila
    static class MatrixMultiplicationTask implements Callable<int[]> {
        private final int[][] A;
        private final int[][] B;
        private final int row;

        public MatrixMultiplicationTask(int[][] A, int[][] B, int row) {
            this.A = A;
            this.B = B;
            this.row = row;
        }

        public int[] call() {
            int n = A[0].length;
            int[] result = new int[n];
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += A[row][k] * B[k][j];
                }
                result[j] = sum;
            }
            return result;
        }
    }

    // Método para multiplicar matrices de manera concurrente
    public static int[][] multiplyConcurrently(int[][] A, int[][] B) throws InterruptedException, ExecutionException {
        int n = A.length;
        int[][] C = new int[n][n];
        ExecutorService executor = Executors.newFixedThreadPool(n);

        // Crear y enviar tareas al executor
        List<Future<int[]>> futures = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            futures.add(executor.submit(new MatrixMultiplicationTask(A, B, i)));
        }

        // Recoger resultados y construir matriz C
        for (int i = 0; i < n; i++) {
            int[] row = futures.get(i).get();
            C[i] = row;
        }

        // Apagar executor
        executor.shutdown();

        return C;
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        int[][] A = readMatrixFromFile("/Users/arielmerino/Documents/concurrente/taller_computo_concurrente/practica_1/practica_01/resources/matrix1000.txt");
        int[][] B = readMatrixFromFile("/Users/arielmerino/Documents/concurrente/taller_computo_concurrente/practica_1/practica_01/resources/matrix1000.txt");

        long startTime = System.nanoTime();
        int[][] C = multiplyConcurrently(A, B);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  // tiempo en nanosegundos



        // Imprimir matriz resultante
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[0].length; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Tiempo tardado (concurrente): " + duration + " nanosegundos");
    }
}


// Clase para la multiplicación de matrices de manera secuencial
class MatrixMultiplicationSequential {
    // Método para leer matriz desde archivo
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

    // Método para multiplicar matrices de manera secuencial
    public static int[][] multiply(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += A[i][k] * B[k][j];
                }
                C[i][j] = sum;
            }
        }
        return C;
    }

    public static void main(String[] args) throws IOException {

        int[][] A = readMatrixFromFile("/Users/arielmerino/Documents/concurrente/taller_computo_concurrente/practica_1/practica_01/resources/matrix1000.txt");
        int[][] B = readMatrixFromFile("/Users/arielmerino/Documents/concurrente/taller_computo_concurrente/practica_1/practica_01/resources/matrix1000.txt");

        long startTime = System.nanoTime();
        int[][] C = multiply(A, B);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  // tiempo en nanosegundos


        // Imprimir matriz resultante
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[0].length; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Tiempo tardado (secuencial): " + duration + " nanosegundos\n");
    }
}
