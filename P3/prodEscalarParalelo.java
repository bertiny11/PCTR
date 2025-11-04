import java.util.Random;

public class prodEscalarParalelo extends Thread {
    private static final int N = 1_000_000; // Tamaño de los vectores
    private int idHebra;
    private int inicio;
    private int fin;
    private double[] vectorA;
    private double[] vectorB;
    private double[] productoParcial;

    public prodEscalarParalelo(int idHebra, int inicio, int fin,
                               double[] vectorA, double[] vectorB, double[] productoParcial) {
        this.idHebra = idHebra;
        this.inicio = inicio;
        this.fin = fin;
        this.vectorA = vectorA;
        this.vectorB = vectorB;
        this.productoParcial = productoParcial;
    }

    public void run() {
        double suma = 0.0;
        for (int i = inicio; i < fin; i++) {
            suma += vectorA[i] * vectorB[i];
        }
        productoParcial[idHebra] = suma;
    }

    public static void main(String[] args) {
        int[] numHebras = {2, 4, 6, 8, 10};
        double[] vector1 = new double[N];
        double[] vector2 = new double[N];
        Random rand = new Random();

        // Inicializar vectores
        for (int i = 0; i < N; i++) {
            vector1[i] = rand.nextDouble() * 100;
            vector2[i] = rand.nextDouble() * 100;
        }

        // Probar con diferentes números de hebras
        for (int nHebras : numHebras) {
            double[] productoParcial = new double[nHebras];
            prodEscalarParalelo[] hebras = new prodEscalarParalelo[nHebras];
            int bloque = N / nHebras;

            long inicio = System.nanoTime();

            // Crear y lanzar hebras
            for (int i = 0; i < nHebras; i++) {
                int ini = i * bloque;
                int fin = (i == nHebras - 1) ? N : ini + bloque;
                hebras[i] = new prodEscalarParalelo(i, ini, fin, vector1, vector2, productoParcial);
                hebras[i].start();
            }

            // Esperar a que terminen
            for (int i = 0; i < nHebras; i++) {
                try {
                    hebras[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Sumar los productos parciales
            double productoFinal = 0.0;
            for (double parcial : productoParcial) {
                productoFinal += parcial;
            }

            long fin = System.nanoTime();
            double tiempoMs = (fin - inicio) / 1e6;

            System.out.printf("Hebras: %2d | Producto escalar: %.6f | Tiempo: %.3f ms%n",
                    nHebras, productoFinal, tiempoMs);
        }
    }
}
