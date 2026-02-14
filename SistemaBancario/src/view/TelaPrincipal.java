package view;

import model.*;
import service.*;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private BancoService banco = new BancoService();
    private JTextArea areaTexto;

    public TelaPrincipal() {
        PersistenciaService.carregar(banco);

        setTitle("Sistema Bancário Pro - Mateus Gomes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Estilização da área de texto
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setBackground(new Color(245, 245, 245));
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        // Painel de botões organizado
        JPanel painelBotoes = new JPanel(new GridLayout(3, 3, 8, 8));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnCriar = new JButton("Nova Conta");
        JButton btnDep = new JButton("Depositar");
        JButton btnSaq = new JButton("Sacar");
        JButton btnTrans = new JButton("Transferir");
        JButton btnExt = new JButton("Ver Extrato");
        JButton btnRend = new JButton("Render (Poupança)");
        JButton btnSair = new JButton("Salvar e Sair");

        painelBotoes.add(btnCriar);
        painelBotoes.add(btnDep);
        painelBotoes.add(btnSaq);
        painelBotoes.add(btnTrans);
        painelBotoes.add(btnExt);
        painelBotoes.add(btnRend);
        painelBotoes.add(btnSair);

        add(painelBotoes, BorderLayout.SOUTH);

        // Ações dos Botões
        btnCriar.addActionListener(e -> criarConta());
        btnDep.addActionListener(e -> depositar());
        btnSaq.addActionListener(e -> sacar());
        btnTrans.addActionListener(e -> transferir());
        btnExt.addActionListener(e -> verExtrato());
        btnRend.addActionListener(e -> aplicarRendimento());
        btnSair.addActionListener(e -> {
            PersistenciaService.salvar(banco.getContas());
            System.exit(0);
        });

        // Salvar ao fechar no "X"
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                PersistenciaService.salvar(banco.getContas());
            }
        });
    }

    private void criarConta() {
        try {
            String[] tipos = {"Corrente", "Poupança"};
            int tipo = JOptionPane.showOptionDialog(null, "Tipo de conta:", "Cadastro", 0, 1, null, tipos, tipos[0]);
            if (tipo == -1) return;

            String nome = JOptionPane.showInputDialog("Nome do titular:");
            String cpf = JOptionPane.showInputDialog("CPF:");
            int num = Integer.parseInt(JOptionPane.showInputDialog("Número da conta:"));

            Cliente c = new Cliente(nome, cpf);
            Conta nova = (tipo == 1) ? new ContaPoupanca(num, c) : new ContaCorrente(num, c);
            
            banco.adicionarConta(nova);
            areaTexto.setText("Sucesso!\n" + nova);
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }

    private void depositar() {
        try {
            int num = Integer.parseInt(JOptionPane.showInputDialog("Nº Conta:"));
            double val = Double.parseDouble(JOptionPane.showInputDialog("Valor:"));
            banco.buscarConta(num).depositar(val);
            areaTexto.setText("Depósito OK!");
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }

    private void sacar() {
        try {
            int num = Integer.parseInt(JOptionPane.showInputDialog("Nº Conta:"));
            double val = Double.parseDouble(JOptionPane.showInputDialog("Valor:"));
            banco.buscarConta(num).sacar(val);
            areaTexto.setText("Saque realizado!");
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }

    private void transferir() {
        try {
            int ori = Integer.parseInt(JOptionPane.showInputDialog("Nº Origem:"));
            int des = Integer.parseInt(JOptionPane.showInputDialog("Nº Destino:"));
            double val = Double.parseDouble(JOptionPane.showInputDialog("Valor:"));
            banco.transferir(ori, des, val);
            areaTexto.setText("Transferência concluída!");
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }

    private void verExtrato() {
        try {
            int num = Integer.parseInt(JOptionPane.showInputDialog("Nº Conta:"));
            areaTexto.setText(banco.buscarConta(num).getExtratoParaTela());
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }

    private void aplicarRendimento() {
        try {
            int num = Integer.parseInt(JOptionPane.showInputDialog("Nº Conta Poupança:"));
            Conta c = banco.buscarConta(num);
            if (c instanceof ContaPoupanca) {
                ((ContaPoupanca) c).aplicarRendimento(0.005); // 0.5% padrão
                areaTexto.setText("Rendimento aplicado!\n" + c);
            } else { JOptionPane.showMessageDialog(this, "Não é uma poupança."); }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }
}