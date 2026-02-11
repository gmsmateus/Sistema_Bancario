package service;

import model.*;
import exception.*;

import java.util.ArrayList;
import java.util.List;

public class BancoService {

    private List<Conta> contas = new ArrayList<>();

    public void adicionarConta(Conta conta) {

        // Verificar número duplicado
        for (Conta c : contas) {
            if (c.getNumero() == conta.getNumero()) {
                throw new IllegalArgumentException("Já existe uma conta com esse número.");
            }

            // Verificar CPF duplicado
            if (c.getCliente().getCpf().equals(conta.getCliente().getCpf())) {
                throw new IllegalArgumentException("Já existe uma conta com esse CPF.");
            }
        }

        contas.add(conta);
    }

    public Conta buscarConta(int numero) throws ContaNaoEncontradaException {
        for (Conta conta : contas) {
            if (conta.getNumero() == numero) {
                return conta;
            }
        }
        throw new ContaNaoEncontradaException("Conta não encontrada.");
    }

    public void transferir(int numeroOrigem, int numeroDestino, double valor)
            throws ContaNaoEncontradaException, SaldoInsuficienteException {

        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de transferência deve ser positivo.");
        }

        Conta origem = buscarConta(numeroOrigem);
        Conta destino = buscarConta(numeroDestino);

        origem.sacar(valor);
        destino.depositar(valor);
    }
}
