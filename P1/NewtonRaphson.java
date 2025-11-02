import java.util.Scanner;

public class NewtonRaphson {

    // --- Funciones de ejemplo ---

    // f(x) = cos(x) - x^3
    public static double f1(double x) {
        return Math.cos(x) - Math.pow(x, 3);
    }

    // f'(x) = -sin(x) - 3x^2
    public static double df1(double x) {
        return -Math.sin(x) - 3 * Math.pow(x, 2);
    }

    // f(x) = x^2 - 5
    public static double f2(double x) {
        return Math.pow(x, 2) - 5;
    }

    // f'(x) = 2x
    public static double df2(double x) {
        return 2 * x;
    }

    // --- Método de Newton-Raphson genérico ---
    public static void newtonRaphson(double x0, int iteraciones, int opcion) {
        double xN = x0, xN1 = 0;

        System.out.println("\nIteraciones del método de Newton-Raphson:");

        for (int i = 0; i < iteraciones; i++) {
            double fx, dfx;

            // Seleccionar función según opción
            if (opcion == 1) {
                fx = f1(xN);
                dfx = df1(xN);
            } else {
                fx = f2(xN);
                dfx = df2(xN);
            }

            if (dfx == 0) {
                System.out.println("Derivada nula. No se puede continuar.");
                return;
            }

            xN1 = xN - fx / dfx;
            System.out.printf("Iteración %d: x = %.8f%n", i + 1, xN1);
            xN = xN1;
        }

        System.out.printf("Aproximación final: %.8f%n", xN);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Método de Newton-Raphson para encontrar raíces");
        System.out.println("Seleccione la función a evaluar:");
        System.out.println("1. f(x) = cos(x) - x^3   (intervalo [0, 1])");
        System.out.println("2. f(x) = x^2 - 5        (intervalo [2, 3])");
        System.out.print("Opción: ");
        int opcion = sc.nextInt();

        System.out.print("Ingrese la aproximación inicial x0: ");
        double x0 = sc.nextDouble();

        System.out.print("Ingrese el número de iteraciones: ");
        int iteraciones = sc.nextInt();

        newtonRaphson(x0, iteraciones, opcion);

        sc.close();
    }
}

