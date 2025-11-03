public class concursoLambda {
    public static void main(String[] args) {
        // Variable compartida entre los hilos
        final int[] contador = {0};

        // Tarea 1: incrementa el contador muchas veces
        Runnable tareaIncrementar = () -> {
            for (int i = 0; i < 100000; i++) {
                contador[0]++; // NO sincronizado → condición de carrera
            }
        };

        // Tarea 2: decrementa el contador muchas veces
        Runnable tareaDecrementar = () -> {
            for (int i = 0; i < 100000; i++) {
                contador[0]--; // NO sincronizado → condición de carrera
            }
        };

        // Creamos los hilos a partir de las lambdas
        Thread hilo1 = new Thread(tareaIncrementar);
        Thread hilo2 = new Thread(tareaDecrementar);

        // Mostramos el valor inicial
        System.out.println("Valor inicial del contador: " + contador[0]);

        // Iniciamos los hilos
        hilo1.start();
        hilo2.start();

        // Esperamos que terminen
        try {
            hilo1.join();
            hilo2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Mostramos el resultado final
        System.out.println("Valor final del contador: " + contador[0]);
        System.out.println("El resultado debería ser 0, pero probablemente no lo es debido a la condición de carrera.");
    }
}
