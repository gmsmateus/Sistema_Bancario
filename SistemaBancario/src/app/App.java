package app;

import model.*;
import exception.SaldoInsuficienteException;

public class App {
    public static void main(String[] args) {

        Cliente cliente = new Cliente("Mateus", "123");

        Conta conta1 = new ContaCorrente(1, cliente);
        ContaPoupanca conta2 = new ContaPoupanca(2, cliente);

        conta1.depositar(500);

        try {
            conta1.sacar(200);
        } catch (SaldoInsuficienteException e) {
            System.out.println(e.getMessage());
        }

        conta2.depositar(1000);
        conta2.aplicarRendimento(0.05);

        System.out.println(conta1);
        System.out.println(conta2);
    }
}