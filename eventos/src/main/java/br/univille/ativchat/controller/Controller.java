package br.univille.ativchat.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;

import br.univille.ativchat.model.Mensagem;
import br.univille.ativchat.service.BrokerMensagemService;
import br.univille.ativchat.util.AppModule;
import br.univille.ativchat.view.Form;

public class Controller implements ActionListener {
    Injector injector = Guice.createInjector(new AppModule());
    BrokerMensagemService service = injector.getInstance(BrokerMensagemService.class);
    private Form form;

    public Controller(Form form) {
        this.form = form;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String mensagem = form.getMensagem();
        if (mensagem != null && !mensagem.trim().isEmpty()) {
            form.setMensagem(form.getNome() + ": " + mensagem);
            service.enviarMensagem(new Mensagem(form.getNome(), mensagem));
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Digite uma mensagem!");
        }
    }

    public void iniciarBuscaDeMensagens() {
        List<Mensagem> mensagens = new ArrayList<>();
        service.buscarMensagens(mensagens);
        for (Mensagem mensagem : mensagens) {
            form.setMensagem(mensagem.nome() + ": " + mensagem.texto());
        }
    }
}