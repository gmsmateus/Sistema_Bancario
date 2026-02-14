package service;

import model.*;

import java.io.*;
import java.util.List;

public class PersistenciaService {

    private static final String ARQUIVO = "contas.txt";

    public static void salvar(List<Conta> contas) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {

            for (Conta conta : contas) {

                writer.write(
                        conta.getNumero() + ";" +
                        conta.getCliente().getNome() + ";" +
                        conta.getCliente().getCpf() + ";" +
                        conta.getSaldo()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Erro ao salvar contas: " + e.getMessage());
        }
    }

    public static void carregar(BancoService banco) {

        File file = new File(ARQUIVO);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {

            String linha;

            while ((linha = reader.readLine()) != null) {

                String[] partes = linha.split(";");

                int numero = Integer.parseInt(partes[0]);
                String nome = partes[1];
                String cpf = partes[2];
                double saldo = Double.parseDouble(partes[3]);

                Cliente cliente = new Cliente(nome, cpf);
                Conta conta = new ContaCorrente(numero, cliente);

                conta.setSaldo(saldo); // restaura saldo

                banco.adicionarConta(conta);
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar contas: " + e.getMessage());
        }
    }
}