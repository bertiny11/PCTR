class Contador {
    private int n = 0;

    public void incremento() {
        n++; // incrementa el atributo de la clase
    }

    public void decremento() {
        n--; // decrementa el atributo de la clase
    }

    public int ver() {
        return n; // devuelve el valor actual
    }
}




public class Act2 implements Runnable{
    private Contador contador;
    private int repeticiones;
    private int tipo_ope;

    private Act2 (Contador contador, int repeticiones, int tipo_ope){
        this.contador = contador;
        this.repeticiones = repeticiones;
        this.tipo_ope = tipo_ope;
    }
    

    public void run(){
        for(int i=0; i< repeticiones; i++){
            if(tipo_ope == 1){
                contador.incremento();
            }
            else{
                contador.decremento();
            }
        }
    }

    public static void main(String[] args) throws Exception{

        int N = 1_000_000; // número de operaciones por hilo

        // Objeto compartido entre los dos hilos
        Contador c = new Contador();

        // Dos tareas Runnable que comparten el mismo objeto
        Act2 tareaInc = new Act2(c, 1, N);
        Act2 tareaDec = new Act2(c, -1, N);

        // Creación de hilos con esas tareas
        Thread h1 = new Thread(tareaInc);
        Thread h2 = new Thread(tareaDec);

        h1.start();
        h2.start();

        h1.join();
        h2.join();

        System.out.println("Valor teórico final de n = 0");
        System.out.println("Valor real obtenido de n = " + c.ver());
        System.out.println("Número de iteraciones por hilo = " + N);

    }

}