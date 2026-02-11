package model;

import java.time.LocalDateTime;

public class Transacao {

    private String tipo;
    private double valor;
    private LocalDateTime data;

    public Transacao(String tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return data + " - " + tipo + " - R$ " + valor;
    }
}