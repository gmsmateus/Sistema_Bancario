package model;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(int numero, Cliente cliente) {
        super(numero, cliente);
    }

    public void aplicarRendimento(double taxa) {
        if (taxa > 0) {
            saldo += saldo * taxa;
        }
    }
}