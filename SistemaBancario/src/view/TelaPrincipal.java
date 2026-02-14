package view;

import model.*;
import service.*;
//import exception.*; 
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private BancoService banco = new BancoService();
    private JTextArea areaTexto;

    public TelaPrincipal() {
        PersistenciaService.carregar(banco);

        setTitle("Sistema Bancário - Projeto Estágio");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new GridLayout(2, 3, 10, 10));
        
        JButton btnCriar = new JButton("Criar Conta");
        JButton btnDepositar = new JButton("Depositar");
        JButton btnSacar = new JButton("Sacar");
        JButton btnTransferir = new JButton("Transferir");
        JButton btnExtrato = new JButton("Ver Extrato");
        JButton btnSalvar = new JButton("Salvar e Sair");

        painelBotoes.add(btnCriar);
        painelBotoes.add(btnDepositar);
        painelBotoes.add(btnSacar);
        painelBotoes.add(btnTransferir);
        painelBotoes.add(btnExtrato);
        painelBotoes.add(btnSalvar);

        add(painelBotoes, BorderLayout.SOUTH);

        // Ações
        btnCriar.addActionListener(e -> criarConta());
        btnDepositar.addActionListener(e -> depositar());
        btnSacar.addActionListener(e -> sacar());
        btnTransferir.addActionListener(e -> transferir());
        btnExtrato.addActionListener(e -> verExtrato());
        
        btnSalvar.addActionListener(e -> {
            PersistenciaService.salvar(banco.getContas());
            JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!");
            System.exit(0);
        });

        // Corrigido o aninhamento aqui
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                PersistenciaService.salvar(banco.getContas());
                System.out.println("Dados salvos automaticamente ao fechar.");
            }
        });
    }

    private void criarConta() {
        try {
            String nome = JOptionPane.showInputDialog("Nome do cliente:");
            String cpf = JOptionPane.showInputDialog("CPF:");
            String numStr = JOptionPane.showInputDialog("Número da conta:");
            if (numStr == null) return; // Cancelar se fechar o dialog

            int numero = Integer.parseInt(numStr);
            Cliente cliente = new Cliente(nome, cpf);
            ContaCorrente conta = new ContaCorrente(numero, cliente);

            banco.adicionarConta(conta);
            areaTexto.setText("Conta criada com sucesso!\n" + conta);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void depositar() {
        try {
            int numero = Integer.parseInt(JOptionPane.showInputDialog("Número da conta:"));
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor do depósito:"));

            Conta conta = banco.buscarConta(numero);
            conta.depositar(valor);
            areaTexto.setText("Depósito realizado!\nNovo saldo: R$ " + String.format("%.2f", conta.getSaldo()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void sacar() {
        try {
            int numero = Integer.parseInt(JOptionPane.showInputDialog("Número da conta:"));
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor do saque:"));

            Conta conta = banco.buscarConta(numero);
            conta.sacar(valor);
            areaTexto.setText("Saque realizado!\nNovo saldo: R$ " + String.format("%.2f", conta.getSaldo()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void transferir() {
        try {
            int origem = Integer.parseInt(JOptionPane.showInputDialog("Conta origem:"));
            int destino = Integer.parseInt(JOptionPane.showInputDialog("Conta destino:"));
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor:"));

            banco.transferir(origem, destino, valor);
            areaTexto.setText("Transferência realizada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void verExtrato() {
        try {
            int numero = Integer.parseInt(JOptionPane.showInputDialog("Número da conta:"));
            Conta conta = banco.buscarConta(numero);
            
            StringBuilder sb = new StringBuilder();
            sb.append("Extrato da Conta: ").append(conta.getNumero()).append("\n");
            sb.append("Cliente: ").append(conta.getCliente().getNome()).append("\n");
            sb.append("----------------------------------\n");
            for (Transacao t : conta.getHistorico()) {
                sb.append(t.toString()).append("\n");
            }
            sb.append("----------------------------------\n");
            sb.append("Saldo atual: R$ ").append(String.format("%.2f", conta.getSaldo()));
            
            areaTexto.setText(sb.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}