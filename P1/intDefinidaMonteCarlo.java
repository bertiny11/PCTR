import java.util.Scanner;

public class intDefinidaMonteCarlo {

    // Función f(x) = sin(x)
    public static double f1(double x) {
        return Math.sin(x);
    }

    // Función f(x) = x
    public static double f2(double x) {
        return x;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Integración Monte Carlo en [0,1] ===");
        System.out.print("Ingrese el número de puntos aleatorios a generar: ");
        int n = sc.nextInt();

        double puntosBajoSin = 0;
        double puntosBajoX = 0;

        // Como las funciones están en [0,1] y no superan 1, el cuadrado es de lado 1.
        for (int i = 0; i < n; i++) {
            double x = Math.random(); // entre 0 y 1
            double y = Math.random(); // entre 0 y 1

            if (y <= f1(x)) puntosBajoSin++;
            if (y <= f2(x)) puntosBajoX++;
        }

        double integralSin = puntosBajoSin / n; // área aproximada bajo sin(x)
        double integralX = puntosBajoX / n;     // área aproximada bajo x

        System.out.println("\nResultados aproximados:");
        System.out.printf("∫[0,1] sin(x) dx ≈ %.6f%n", integralSin);
        System.out.printf("∫[0,1] x dx ≈ %.6f%n", integralX);

        System.out.println("\nValores reales (para comparación):");
        System.out.printf("∫[0,1] sin(x) dx = %.6f%n", 1 - Math.cos(1)); // ≈ 0.4597
        System.out.printf("∫[0,1] x dx = %.6f%n", 0.5); // = 1/2
        sc.close();
    }
}
