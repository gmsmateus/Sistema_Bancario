package model;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(int numero, Cliente cliente) {
        super(numero, cliente);
    }

    public void aplicarRendimento(double taxa) {
        if (taxa <= 0) {
            throw new IllegalArgumentException("Taxa deve ser positiva.");
        }

        double rendimento = saldo * taxa;
        saldo += rendimento;

        historico.add(new Transacao("Rendimento aplicado", rendimento));
    }
}
