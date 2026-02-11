package app;

import model.*;
import service.BancoService;
//import exception.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BancoService banco = new BancoService();

        boolean rodando = true;

        while (rodando) {

            System.out.println("\n===== SISTEMA BANCÁRIO =====");
            System.out.println("1 - Criar conta");
            System.out.println("2 - Depositar");
            System.out.println("3 - Sacar");
            System.out.println("4 - Transferir");
            System.out.println("5 - Ver extrato");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            try {

                switch (opcao) {

                    case 1:
                        System.out.print("Número da conta: ");
                        int numero = scanner.nextInt();
                        scanner.nextLine(); // limpar buffer

                        System.out.print("Nome do cliente: ");
                        String nome = scanner.nextLine();

                        System.out.print("CPF do cliente: ");
                        String cpf = scanner.nextLine();

                        Cliente cliente = new Cliente(nome, cpf);
                        Conta conta = new ContaCorrente(numero, cliente);

                        banco.adicionarConta(conta);
                        System.out.println("Conta criada com sucesso!");
                        break;

                    case 2:
                        System.out.print("Número da conta: ");
                        int numDep = scanner.nextInt();

                        System.out.print("Valor do depósito: ");
                        double valorDep = scanner.nextDouble();

                        banco.buscarConta(numDep).depositar(valorDep);
                        System.out.println("Depósito realizado!");
                        break;

                    case 3:
                        System.out.print("Número da conta: ");
                        int numSaq = scanner.nextInt();

                        System.out.print("Valor do saque: ");
                        double valorSaq = scanner.nextDouble();

                        banco.buscarConta(numSaq).sacar(valorSaq);
                        System.out.println("Saque realizado!");
                        break;

                    case 4:
                        System.out.print("Conta origem: ");
                        int origem = scanner.nextInt();

                        System.out.print("Conta destino: ");
                        int destino = scanner.nextInt();

                        System.out.print("Valor: ");
                        double valor = scanner.nextDouble();

                        banco.transferir(origem, destino, valor);
                        System.out.println("Transferência realizada!");
                        break;

                    case 5:
                        System.out.print("Número da conta: ");
                        int numExt = scanner.nextInt();

                        banco.buscarConta(numExt).exibirExtrato();
                        break;

                    case 0:
                        rodando = false;
                        System.out.println("Encerrando sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        scanner.close();
    }
}