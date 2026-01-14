import java.util.Scanner;
import java.util.Random;

public class ConvolucionParalela {

    // --- VARIABLES COMPARTIDAS (Memoria Común) ---
    static double[][] imagen;
    static double[][] resultado;
    static double[][] kernel;
    static int N;

    // --- 1. GENERADOR DE DATOS (Igual al secuencial) ---
    public static double[][] generarMatriz(int n) {
        Random rand = new Random();
        double[][] m = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = rand.nextDouble() * 255.0;
            }
        }
        return m;
    }

    public static double[][] obtenerKernel(int op) {
        switch (op) {
            case 1: return new double[][]{{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}}; // Enfoque
            case 2: return new double[][]{{0.11, 0.11, 0.11}, {0.11, 0.11, 0.11}, {0.11, 0.11, 0.11}}; // Desenfoque
            case 3: return new double[][]{{0, 0, 0}, {-1, 1, 0}, {0, 0, 0}}; // Realce bordes
            case 4: return new double[][]{{-2, -1, 0}, {-1, 1, 1}, {0, 1, 2}}; // Repujado
            case 5: return new double[][]{{0, 1, 0}, {1, -4, 1}, {0, 1, 0}}; // Detección bordes
            case 6: return new double[][]{{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}}; // Sobel
            case 7: return new double[][]{{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}}; // Sharpen
            case 8: return new double[][]{{1, 1, 1}, {1, -2, 1}, {-1, -1, -1}}; // Norte
            case 9: return new double[][]{{-1, 1, 1}, {-1, -2, 1}, {-1, 1, 1}}; // Este
            case 10: return new double[][]{{0.06, 0.12, 0.06}, {0.12, 0.25, 0.12}, {0.06, 0.12, 0.06}}; // Gauss
            default: return new double[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        }
    }

    // --- 2. TAREA DEL HILO (WORKER) ---
    // Cada hilo recibe un rango de filas (start -> end)
    static class Worker extends Thread {
        int inicioFila, finFila;

        public Worker(int inicio, int fin) {
            this.inicioFila = inicio;
            this.finFila = fin;
        }

        @Override
        public void run() {
            int kAlto = kernel.length;
            int kAncho = kernel[0].length;
            int padH = kAlto / 2;
            int padW = kAncho / 2;

            // Recorremos SOLO la franja de filas asignada a este hilo
            for (int i = inicioFila; i < finFila; i++) {
                for (int j = 0; j < N; j++) {
                    double suma = 0.0;
                    for (int ki = 0; ki < kAlto; ki++) {
                        for (int kj = 0; kj < kAncho; kj++) {
                            int imgI = i - padH + ki;
                            int imgJ = j - padW + kj;
                            
                            // Chequeo de bordes seguro
                            if (imgI >= 0 && imgI < N && imgJ >= 0 && imgJ < N) {
                                suma += imagen[imgI][imgJ] * kernel[ki][kj];
                            }
                        }
                    }
                    resultado[i][j] = suma;
                }
            }
        }
    }

    // --- 3. MAIN ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- CONVOLUCION PARALELA JAVA ---");
        
        System.out.print("Tamano de la matriz (N): ");
        N = scanner.nextInt();
        
        System.out.print("Numero de hebras (hilos): "); // Pide los hilos
        int numHebras = scanner.nextInt();

        System.out.println("Seleccione filtro (1-10): ");
        int op = scanner.nextInt();

        // Inicialización
        System.out.println("Generando datos...");
        imagen = generarMatriz(N);
        kernel = obtenerKernel(op);
        resultado = new double[N][N];

        Worker[] hilos = new Worker[numHebras];
        int filasPorHebra = N / numHebras;

        System.out.println("Iniciando procesamiento paralelo con " + numHebras + " hebras...");
        long inicio = System.nanoTime();

        // A. LANZAR HILOS (FORK)
        for (int i = 0; i < numHebras; i++) {
            int inicioFila = i * filasPorHebra;
            // El último hilo se queda con el resto (si N no es divisible exacto)
            int finFila = (i == numHebras - 1) ? N : (inicioFila + filasPorHebra);
            
            hilos[i] = new Worker(inicioFila, finFila);
            hilos[i].start();
        }

        // B. ESPERAR HILOS (JOIN)
        for (int i = 0; i < numHebras; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;
        
        System.out.println("Tiempo Total (Paralelo): " + tiempoMs + " ms");
        System.out.println("Proceso finalizado.");
        scanner.close();
    }
}