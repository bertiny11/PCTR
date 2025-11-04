import java.util.Random;
import java.util.Scanner;

public class prodEscalar{
    public static void main(String[] args) {

        int n = 1000000; // tamaño del vector
        int[] vector1 = new int[n];
        int[] vector2 = new int[n];
        Random rand = new Random();
        double resultado = 0;
        Scanner sc = new Scanner(System.in);

        for(int i=0; i<n; i++){
            vector1[i] = rand.nextInt(100);
            vector2[i] = rand.nextInt(100);
        }
        long inicio = System.nanoTime();
        resultado = productoEscalar(vector1, vector2);
        long fin = System.nanoTime();
        double tiempoEjecucion = (fin - inicio) / 1e6; //

        System.out.println("El producto escalar es: " + resultado);
        System.out.println("Tiempo de ejecucion: " + tiempoEjecucion + " ms");

        sc.close();
    }

    public static double productoEscalar(int[] a, int[] b) {
        double producto = 0;
        for (int i = 0; i < a.length; i++) {
            producto += a[i] * b[i];
        }
        return producto;
    }
}