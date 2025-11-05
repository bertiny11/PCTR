import java.util.Random;


public class matVectorConcurrente implements Runnable{
    private double[][] matriz;
    private double[] vector;
    private double[] resultado;
    private int inicioFila;
    private int finFila;

    public matVectorConcurrente(double[][] matriz, double[] vector, double[] resultado, int inicioFila, int finFila) {
        this.matriz = matriz;
        this.vector = vector;
        this.resultado = resultado;
        this.inicioFila = inicioFila;
        this.finFila = finFila;
    }

    public void run(){
        for(int i= inicioFila; i< finFila; i++){
            double suma = 0.0;
            for(int j= 0; j< vector.length; j++){
                suma += matriz[i][j] * vector[j];
            }
            resultado[i] = suma;
        }        
    }

    public static void main(String[] args){
        int n= 1000;
        int m[] = {2, 4, 8, 16}; //numero de hilos
        double[][] matriz = new double[n][n];
        double[] vector = new double[n];
        double[] resultado = new double[n];
        Random rand = new Random();

        for(int i=0; i<n; i++){
            for (int j=0; j<n; j++){
                matriz[i][j] = rand.nextDouble() * 100;
            }
            vector[i] = rand.nextDouble() * 100;
        }

        long inicio = System.nanoTime();

        for(int hilos: m){

            Thread[] thread = new Thread[hilos];
            int filasPorhilo = n/hilos;
            //una vez creado el numero de hilos, se inicia el tiempo
            long inicioT = System.nanoTime();
            for(int i=0; i<hilos; i++){
                int inicioFila = i*filasPorhilo;
                int finFila = (i==hilos-1) ? n : inicioFila + filasPorhilo;
                thread[i] = new Thread(new matVectorConcurrente(matriz, vector, resultado, inicioFila, finFila));
                thread[i].start();
            }
            for(int i=0; i<hilos; i++){
                try{
                    thread[i].join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            long fin = System.nanoTime();
            double tiempoEjecucion = (fin - inicioT) / 1e6; // Convertir a milisegundos
            System.out.println("Hilos: " + hilos + " Tiempo de ejecucion: " + tiempoEjecucion + " ms");

        }


    }
}