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

    public void depositar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor positivo obrigatório.");
        saldo += valor;
        historico.add(new Transacao("Depósito", valor));
    }

    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor <= 0) throw new IllegalArgumentException("Valor positivo obrigatório.");
        if (valor > saldo) throw new SaldoInsuficienteException("Saldo insuficiente.");
        saldo -= valor;
        historico.add(new Transacao("Saque", valor));
    }

    public String getExtratoParaTela() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== EXTRATO DA CONTA ").append(numero).append(" =====\n");
        sb.append("Titular: ").append(cliente.getNome()).append("\n");
        sb.append("----------------------------------\n");
        if (historico.isEmpty()) {
            sb.append("Sem movimentações.\n");
        } else {
            for (Transacao t : historico) sb.append(t.toString()).append("\n");
        }
        sb.append("----------------------------------\n");
        sb.append(String.format("SALDO ATUAL: R$ %.2f", saldo));
        return sb.toString();
    }

    // Getters básicos
    public int getNumero() { return numero; }
    public Cliente getCliente() { return cliente; }
    public double getSaldo() { return saldo; }
    public List<Transacao> getHistorico() { return historico; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    @Override
    public String toString() {
        return String.format("Conta: %d | Titular: %s | Saldo: R$ %.2f", numero, cliente.getNome(), saldo);
    }
}