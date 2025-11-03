import java.util.Scanner;
import java.util.Random;

public class escalaVector{

    public static void main(String[] args) {

        int n = 10000000; // tamaño del vector
        int[] vector = new int[n];
        Random rand = new Random();
        double[] resultado = new double[n];
        Scanner sc = new Scanner(System.in);

        for(int i=0; i<n; i++){
            vector[i] = rand.nextInt(100);
        }

        // Pedir al usuario el factor de escala
        System.out.print("Introduce el factor de escala: ");
        double factor = sc.nextDouble();

        long inicio = System.currentTimeMillis();

        // Escalado secuencial del vector
        for (int i = 0; i < n; i++) {
            resultado[i] = factor * vector[i];
        }

        long fin = System.currentTimeMillis();
        double tiempo = (fin - inicio) / 1000.0;

        // Mostrar algunos resultados de ejemplo
        System.out.println("\nEjemplo de escalado:");
        for (int i = 0; i < 5; i++) {
            System.out.printf("vector[%d] = %d → resultado = %.2f%n", i, vector[i], resultado[i]);
        }

        System.out.printf("%nTiempo total de cálculo: %.3f segundos%n", tiempo);
        
        sc.close();

    }
}