public class RedCajeros {
    public static void main(String[] args) {
        CuentaCorriente cuenta = new CuentaCorriente("ES12 3456 7890 1234 5678", 1000.0);
        System.out.println("Saldo inicial: " + cuenta.getSaldo());

        Thread[] cajeros = new Thread[10];

        for (int i = 0; i < cajeros.length; i++) {
            boolean esDeposito = (i % 2 == 0); // alternar depósitos y reintegros
            cajeros[i] = new Thread(new cajero(cuenta, esDeposito, 10.0));
        }

        for (Thread t : cajeros) t.start();

        for (Thread t : cajeros) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nSaldo final: " + cuenta.getSaldo());
        System.out.println("El saldo debería ser igual al inicial, pero probablemente no lo es (condición de carrera).");
    }
}