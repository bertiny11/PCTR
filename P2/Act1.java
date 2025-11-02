public class Act1 extends Thread{
    private static int n = 0;

    private int repeticiones;
    private int tipo_ope;

    public Act1 (int tipo_ope, int repeticiones){
        this.tipo_ope = tipo_ope;
        this.repeticiones = repeticiones;
    }
    public void run(){
        for(int i=0; i< repeticiones; i++){
            n += tipo_ope;
        }
    }

    public static void main(String[] args) throws Exception{
        int N = 1000;
	    Act1 h1 = new Act1(1, N);
	    Act1 h2 = new Act1(-1, N);
	    h1.start(); h2.start();
	    h1.join(); h2.join();

        System.out.println("Valor teórico final de n = 0");
        System.out.println("Valor real obtenido de n = " + n);
        System.out.println("Número de iteraciones por hilo = " + N);
    }
}