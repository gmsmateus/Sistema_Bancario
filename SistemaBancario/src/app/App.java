package app;

import model.*;
import service.BancoService;
import exception.*;

public class App {
    public static void main(String[] args) {

        BancoService banco = new BancoService();

        Cliente cliente1 = new Cliente("Mateus", "123");
        Cliente cliente2 = new Cliente("Ana", "456");

        Conta conta1 = new ContaCorrente(1, cliente1);
        Conta conta2 = new ContaPoupanca(2, cliente2);

        banco.adicionarConta(conta1);
        banco.adicionarConta(conta2);

        conta1.depositar(1000);
        conta1.depositar(-100);

        try {
            banco.transferir(1, 2, 300);
        } catch (ContaNaoEncontradaException | SaldoInsuficienteException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(conta1);
        System.out.println(conta2);
    }
}
