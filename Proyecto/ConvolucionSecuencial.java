import java.util.Random;
import java.util.Scanner;

public class ConvolucionSecuencial {

    // --- 1. GENERADOR DE DATOS Y FILTROS ---
    
    // Genera matriz aleatoria N x N con valores 0-255
    public static double[][] generarMatriz(int N) {
        Random rand = new Random();
        double[][] matriz = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matriz[i][j] = rand.nextDouble() * 255.0;
            }
        }
        return matriz;
    }

    // Devuelve el kernel según la opción elegida (Los 10 filtros de la rúbrica)
    public static double[][] obtenerKernel(int opcion) {
        switch (opcion) {
            case 1: return new double[][]{{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}}; // Enfoque
            case 2: return new double[][]{{0.11, 0.11, 0.11}, {0.11, 0.11, 0.11}, {0.11, 0.11, 0.11}}; // Desenfoque
            case 3: return new double[][]{{0, 0, 0}, {-1, 1, 0}, {0, 0, 0}}; // Realce de bordes
            case 4: return new double[][]{{-2, -1, 0}, {-1, 1, 1}, {0, 1, 2}}; // Repujado
            case 5: return new double[][]{{0, 1, 0}, {1, -4, 1}, {0, 1, 0}}; // Detección de bordes
            case 6: return new double[][]{{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}}; // Sobel
            case 7: return new double[][]{{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}}; // Sharpen
            case 8: return new double[][]{{1, 1, 1}, {1, -2, 1}, {-1, -1, -1}}; // Norte
            case 9: return new double[][]{{-1, 1, 1}, {-1, -2, 1}, {-1, 1, 1}}; // Este
            case 10: return new double[][]{{0.06, 0.12, 0.06}, {0.12, 0.25, 0.12}, {0.06, 0.12, 0.06}}; // Gauss
            default: return new double[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}; // Identidad por defecto
        }
    }

    // --- 2. NÚCLEO DE CONVOLUCIÓN ---
    
    public static double[][] aplicarConvolucion(double[][] imagen, double[][] kernel) {
        int alto = imagen.length;
        int ancho = imagen[0].length;
        int kAlto = kernel.length;
        int kAncho = kernel[0].length;
        int padH = kAlto / 2;
        int padW = kAncho / 2;

        double[][] resultado = new double[alto][ancho];

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                double suma = 0.0;
                for (int ki = 0; ki < kAlto; ki++) {
                    for (int kj = 0; kj < kAncho; kj++) {
                        // Coordenadas en la imagen original
                        int imgI = i - padH + ki;
                        int imgJ = j - padW + kj;

                        // Verificar límites para evitar excepciones
                        if (imgI >= 0 && imgI < alto && imgJ >= 0 && imgJ < ancho) {
                            suma += imagen[imgI][imgJ] * kernel[ki][kj];
                        }
                    }
                }
                resultado[i][j] = suma;
            }
        }
        return resultado;
    }

    // --- 3. MAIN Y GESTIÓN DE E/S ---

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- CONVOLUCION SECUENCIAL JAVA ---");
        System.out.print("Introduce el tamano de la matriz (N): ");
        int N = scanner.nextInt();

        System.out.println("Seleccione filtro:");
        System.out.println("1.Enfoque 2.Desenfoque 3.Realce Bordes 4.Repujado 5.Deteccion Bordes");
        System.out.println("6.Sobel 7.Sharpen 8.Norte 9.Este 10.Gauss");
        System.out.print("Opcion: ");
        int opcionFiltro = scanner.nextInt();

        System.out.println("Generando datos...");
        double[][] imagen = generarMatriz(N);
        double[][] kernel = obtenerKernel(opcionFiltro);

        System.out.println("Iniciando convolucion...");
        long inicio = System.nanoTime();

        // Llamada al núcleo
        double[][] resultado = aplicarConvolucion(imagen, kernel);

        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;

        // Imprimir tiempo obligatorio para la gráfica
        System.out.println("Tiempo de ejecucion: " + tiempoMs + " ms");
        
        System.out.println("Proceso finalizado con exito.");
        scanner.close();
    }
}