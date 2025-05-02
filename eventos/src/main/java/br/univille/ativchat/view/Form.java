package br.univille.ativchat.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.univille.Producer;
import br.univille.Consumer;
import br.univille.ativchat.model.Mensagem;
import br.univille.ativchat.controller.Controller;
import br.univille.ativchat.service.BrokerMensagemService;

public class Form extends JFrame {
    private JPanel jpnSul;
    private JScrollPane jpnCentro;
    private JTextArea txtChat;
    private JTextField txtNovaMsg;
    private JButton btnEnviar;
    private String nome;
    private Controller controller;

    public String getNome() {
        return this.nome;
    }

    public String getMensagem() {
        return txtNovaMsg.getText();
    }

    public void setMensagem(String msg) {
        txtChat.append(msg + "\n");
    }

    public Form() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CHAT");
        setSize(500, 400);
        solicitarNome();

        criaJpnCentro();
        criaJpnSul();

        controller = new Controller(this); // Inicializa o Controller
        controller.iniciarBuscaDeMensagens(); // Inicia a busca de mensagens

        setVisible(true);
    }

    private void solicitarNome() {
        this.nome = javax.swing.JOptionPane.showInputDialog("Digite seu nome:");
        if (nome != null && !nome.trim().isEmpty()) {
            setTitle("CHAT - " + nome);
        } else {
            solicitarNome();
        }
    }

    private void criaJpnCentro() {
        txtChat = new JTextArea(10, 20);
        jpnCentro = new JScrollPane(txtChat);
        txtChat.setText("Bem-vindo ao chat!\n\n");
        txtChat.setEditable(false);
        txtChat.setLineWrap(true);
        getContentPane().add(jpnCentro, "Center");
    }

    private void criaJpnSul() {
        jpnSul = new JPanel();
        jpnSul.setBorder(BorderFactory.createTitledBorder("Nova Mensagem"));
        jpnSul.setBounds(0, 350, 300, 50);
        txtNovaMsg = new JTextField(20);
        btnEnviar = new JButton("Enviar");
        jpnSul.add(txtNovaMsg);
        jpnSul.add(btnEnviar);

        // Adiciona ação ao botão "Enviar"
        btnEnviar.addActionListener(e -> enviarMensagem());

        // Adiciona ação ao pressionar Enter no campo de texto
        txtNovaMsg.addActionListener(e -> enviarMensagem());

        getContentPane().add(jpnSul, "South");
    }

    private void enviarMensagem() {
        String mensagem = txtNovaMsg.getText();
        if (!mensagem.isEmpty()) {
            new Thread(() -> {
                try {
                    // Envia a mensagem real usando o Producer
                    Producer.enviarMensagem(new Mensagem(nome, mensagem));
                    txtChat.append("Você: " + mensagem + "\n");
                    txtNovaMsg.setText("");
                } catch (Exception ex) {
                    txtChat.append("Erro ao enviar mensagem: " + ex.getMessage() + "\n");
                }
            }).start();
        }
    }

}