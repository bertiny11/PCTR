public class cajero implements Runnable{
   // Código de la clase redCajeros
   private CuentaCorriente cuenta;
   private boolean operacionDeposito;
   private double cantidad;

   public cajero(CuentaCorriente cuenta, boolean operacionDeposito, double cantidad) {
      this.cuenta = cuenta;
      this.operacionDeposito = operacionDeposito;
      this.cantidad = cantidad;
   }

   @Override
   public void run() {
      if (operacionDeposito) {
         cuenta.depositar(cantidad);
      } else {
         cuenta.retirar(cantidad);
      }
   }

}