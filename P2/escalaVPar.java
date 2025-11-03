import java.util.Random;
import java.util.Scanner;

public class escalaVPar extends Thread{

    private int[] vector;
    private double[] resultado;
    private double factor;
    private int inicio, fin;

    public escalaVPar(int[] vector, double[] resultado, double factor, int inicio, int fin) {
        this.vector = vector;
        this.resultado = resultado;
        this.factor = factor;
        this.inicio = inicio;
        this.fin = fin;
    }

    public void run(){
        //código del escalado paralelo del vector
        for (int i = inicio; i < fin; i++) {
            resultado[i] = factor * vector[i];
        }
    }


    public static void main(String[] args){
        //creamos el vector y lo rellenamos aleatoriamente
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

        // Crear y arrancar 4 hilos
        escalaVPar t1 = new escalaVPar(vector, resultado, factor, 0, n/4);
        escalaVPar t2 = new escalaVPar(vector, resultado, factor, n/4, n/2);
        escalaVPar t3 = new escalaVPar(vector, resultado, factor, n/2, 3*n/4);
        escalaVPar t4 = new escalaVPar(vector, resultado, factor, 3*n/4, n);

        long inicio = System.currentTimeMillis();
        

        t1.start();
        t2.start();
        t3.start();
        t4.start();

         // Esperar a que terminen los hilos
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Mostrar los primeros 10 resultados
        System.out.println("Primeros 10 resultados escalados:");
        for(int i=0; i<10; i++){
            System.out.println(resultado[i]);
        }

        long fin = System.currentTimeMillis();
        double tiempo = (fin - inicio) / 1000.0;
        System.out.printf("%nTiempo total de cálculo: %.3f segundos%n", tiempo);



        sc.close();

    }

}