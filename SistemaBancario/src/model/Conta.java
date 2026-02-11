package model;

import exception.SaldoInsuficienteException;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta {

    protected int numero;
    protected Cliente cliente;
    protected double saldo;
    protected List<Transacao> historico = new ArrayList<>();

    public Conta(int numero, Cliente cliente) {
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Transacao> getHistorico() {
        return historico;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo.");
        }

        saldo += valor;
        historico.add(new Transacao("Depósito", valor));
    }

    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo.");
        }

        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente para saque.");
        }

        saldo -= valor;
        historico.add(new Transacao("Saque", valor));
    }

    @Override
    public String toString() {
        return "Conta{" +
                "numero=" + numero +
                ", cliente=" + cliente.getNome() +
                ", saldo=" + saldo +
                '}';
    }
}