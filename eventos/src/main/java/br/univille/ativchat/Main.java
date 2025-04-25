package br.univille.ativchat;

import br.univille.ativchat.view.Form;
import br.univille.ativchat.controller.Controller;

public class Main {
    public static void main(String[] args) {
        // Inicializa a interface gráfica
        Form form = new Form();

        // Inicializa o controlador
        Controller controller = new Controller(form);

        // Inicia o recebimento de mensagens no terminal e no chat
        new Thread(() -> {
            controller.getService().buscarMensagens(form);
        }).start();
    }
}