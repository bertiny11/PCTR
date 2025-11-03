public class CuentaCorriente {

    // ====== Atributos ======
    private String numeroCuenta;
    private double saldo;

    // ====== Constructor ======
    public CuentaCorriente(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }

    // ====== Métodos ======

    // Método para realizar un depósito
    public void depositar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
            System.out.printf("Depósito de %.2f realizado. Nuevo saldo: %.2f%n", cantidad, saldo);
        } else {
            System.out.println("Error: la cantidad a depositar debe ser positiva.");
        }
    }

    // Método para realizar un reintegro (retiro)
    public void retirar(double cantidad) {
        if (cantidad > 0 && cantidad <= saldo) {
            saldo -= cantidad;
            System.out.printf("Reintegro de %.2f realizado. Nuevo saldo: %.2f%n", cantidad, saldo);
        } else if (cantidad > saldo) {
            System.out.println("Error: saldo insuficiente.");
        } else {
            System.out.println("Error: la cantidad a retirar debe ser positiva.");
        }
    }

    // Consultar saldo
    public double getSaldo() {
        return saldo;
    }

    // Consultar número de cuenta
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    // Mostrar información general
    public void mostrarInfo() {
        System.out.printf("Cuenta: %s | Saldo actual: %.2f%n", numeroCuenta, saldo);
    }

    // ====== Método main de prueba ======
    // public static void main(String[] args) {
    //     CuentaCorriente cuenta1 = new cuentaCorriente("ES12 3456 7890 1234 5678", 1000.0);

    //     cuenta1.mostrarInfo();

    //     cuenta1.depositar(250.75);
    //     cuenta1.retirar(100.00);
    //     cuenta1.retirar(2000.00); // ejemplo de error
    //     cuenta1.mostrarInfo();
    // }
}
