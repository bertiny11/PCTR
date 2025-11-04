import java.util.Random;

public class matVector{

    public static double[] ProductoMatriz(double[][] matriz, double[] vector){
        int filas = matriz.length;
        int columnas = matriz[0].length;
        double[] resultado = new double[filas];

        for(int i= 0; i< filas; i++){
            double suma = 0.0;
            for(int j= 0; j< columnas; j++){
                suma += matriz[i][j] * vector[j];
            }
            resultado[i] = suma;
        }
        return resultado;
    }


    public static void main(String[] args) {
        int n= 1000;
        double[][] matriz = new double[n][n];
        double[] vector = new double[n];
        double[] resultado = new double[n];

        Random rand = new Random();

        for(int i= 0; i<n; i++){
            for (int j= 0; j<n; j++){
                matriz[i][j] = rand.nextDouble() * 100;
            }
            vector[i] = rand.nextDouble() * 100;
        }
        long inicio = System.nanoTime();

        resultado = ProductoMatriz(matriz, vector);

        long fin = System.nanoTime();
        double tiempoEjecucion = (fin - inicio) / 1e6; // Convertir a milisegundos
        System.out.println("Tiempo de ejecucion: " + tiempoEjecucion + " ms");

    }
}