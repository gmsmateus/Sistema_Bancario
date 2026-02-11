package service;

import model.*;
import exception.*;

import java.util.ArrayList;
import java.util.List;

public class BancoService {

    private List<Conta> contas = new ArrayList<>();

    public void adicionarConta(Conta conta) {
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

        Conta origem = buscarConta(numeroOrigem);
        Conta destino = buscarConta(numeroDestino);

        origem.sacar(valor);
        destino.depositar(valor);
    }
}