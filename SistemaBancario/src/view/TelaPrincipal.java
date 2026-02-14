package view;

import model.*;
import service.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private BancoService banco = new BancoService();
    private JTextArea areaTexto;

    private final Color COR_FUNDO = new Color(240, 244, 248);
    private final Color COR_CONSOLE = new Color(33, 37, 41);
    private final Color COR_TEXTO_CONSOLE = new Color(0, 255, 127);
    private final Color AZUL_BORDAS = new Color(74, 144, 226);

    public TelaPrincipal() {
        // COMENTEI A LINHA ABAIXO: Ela que estava deixando os botões brancos/feios
        // try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}

        PersistenciaService.carregar(banco);

        setTitle("Sistema Bancário - Mateus Gomes");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new BorderLayout(15, 15));

        JLabel lblTitulo = new JLabel("🏦 Banco Digital", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(44, 62, 80));
        lblTitulo.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setBackground(COR_CONSOLE);
        areaTexto.setForeground(COR_TEXTO_CONSOLE);
        areaTexto.setFont(new Font("Consolas", Font.PLAIN, 15));
        areaTexto.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(0, 20, 0, 20),
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(AZUL_BORDAS, 2), 
                " Painel de Monitoramento ", 
                TitledBorder.LEFT, 
                TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 12), 
                AZUL_BORDAS)
        ));
        add(scroll, BorderLayout.CENTER);

        JPanel painelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        painelContainer.setBackground(COR_FUNDO);

        // Grid de 2 linhas e 4 colunas
        JPanel painelBotoes = new JPanel(new GridLayout(2, 4, 12, 12));
        painelBotoes.setBackground(COR_FUNDO);

        // CORES VIVAS E FIXAS
        JButton btnCriar = criarBotaoPersonalizado("Abrir Conta", new Color(52, 152, 219)); // Azul
        JButton btnDep = criarBotaoPersonalizado("Depositar", new Color(40, 167, 69));   // Verde
        JButton btnSaq = criarBotaoPersonalizado("Sacar", new Color(220, 53, 69));       // Vermelho
        JButton btnTrans = criarBotaoPersonalizado("Transferir", new Color(255, 152, 0)); // Laranja
        JButton btnExt = criarBotaoPersonalizado("Ver Extrato", new Color(108, 117, 125)); // Cinza
        JButton btnRend = criarBotaoPersonalizado("Render Juros", new Color(111, 66, 193)); // Roxo
        JButton btnSair = criarBotaoPersonalizado("Sair", new Color(33, 37, 41));         // Preto

        painelBotoes.add(btnCriar);
        painelBotoes.add(btnDep);
        painelBotoes.add(btnSaq);
        painelBotoes.add(btnTrans);
        painelBotoes.add(btnExt);
        painelBotoes.add(btnRend);
        painelBotoes.add(btnSair);

        painelContainer.add(painelBotoes);
        add(painelContainer, BorderLayout.SOUTH);

        // AÇÕES
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

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                PersistenciaService.salvar(banco.getContas());
            }
        });
    }

    private JButton criarBotaoPersonalizado(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(160, 50));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        
        // Estas duas linhas garantem a cor no Java puro
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(cor.darker(), 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return btn;
    }

    // Sobre sua dúvida de Conta Corrente/Poupança:
    // O botão "Abrir Conta" já faz essa distinção! Mas vamos deixar as mensagens mais claras.

    private void criarConta() {
        try {
            String[] tipos = {"Corrente", "Poupança"};
            int tipo = JOptionPane.showOptionDialog(null, "Qual tipo de conta deseja abrir?", "Abertura de Conta", 
                    0, JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
            if (tipo == -1) return;

            String nome = JOptionPane.showInputDialog("Nome do titular:");
            String cpf = JOptionPane.showInputDialog("CPF:");
            int num = Integer.parseInt(JOptionPane.showInputDialog("Número da conta:"));

            Cliente c = new Cliente(nome, cpf);
            Conta nova = (tipo == 1) ? new ContaPoupanca(num, c) : new ContaCorrente(num, c);
            
            banco.adicionarConta(nova);
            areaTexto.setText("✅ SUCESSO! Conta " + (tipo == 1 ? "POUPANÇA" : "CORRENTE") + " criada.\n" + nova);
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }

    private void depositar() {
        try {
            int num = Integer.parseInt(JOptionPane.showInputDialog("Nº Conta:"));
            double val = Double.parseDouble(JOptionPane.showInputDialog("Valor do Depósito:"));
            banco.buscarConta(num).depositar(val);
            areaTexto.setText("💰 DEPÓSITO REALIZADO!\nConta: " + num + "\nValor: R$ " + String.format("%.2f", val));
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }

    private void sacar() {
        try {
            int num = Integer.parseInt(JOptionPane.showInputDialog("Nº Conta:"));
            double val = Double.parseDouble(JOptionPane.showInputDialog("Valor do Saque:"));
            banco.buscarConta(num).sacar(val);
            areaTexto.setText("💸 SAQUE REALIZADO!\nConta: " + num + "\nValor: R$ " + String.format("%.2f", val));
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }

    private void transferir() {
        try {
            int ori = Integer.parseInt(JOptionPane.showInputDialog("Nº Origem:"));
            int des = Integer.parseInt(JOptionPane.showInputDialog("Nº Destino:"));
            double val = Double.parseDouble(JOptionPane.showInputDialog("Valor da Transferência:"));
            banco.transferir(ori, des, val);
            areaTexto.setText("🔄 TRANSFERÊNCIA CONCLUÍDA!\nDe: " + ori + " Para: " + des + "\nValor: R$ " + String.format("%.2f", val));
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
                ((ContaPoupanca) c).aplicarRendimento(0.005);
                areaTexto.setText("📈 RENDIMENTO APLICADO!\nConta Poupança: " + num + "\nTaxa: 0.5%\nNovo Saldo: R$ " + String.format("%.2f", c.getSaldo()));
            } else { JOptionPane.showMessageDialog(this, "A conta " + num + " é CORRENTE. Rendimento só para POUPANÇA."); }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }
}