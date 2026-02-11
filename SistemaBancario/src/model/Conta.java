package model;

import exception.SaldoInsuficienteException;

public abstract class Conta {

    protected int numero;
    protected Cliente cliente;
    protected double saldo;

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

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }

    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente para saque.");
        }
        saldo -= valor;
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