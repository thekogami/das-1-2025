package br.univille.ativchat.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.google.inject.Guice;
import com.google.inject.Injector;

import br.univille.ativchat.model.Mensagem;
import br.univille.ativchat.service.BrokerMensagemService;
import br.univille.ativchat.util.AppModule;
import br.univille.ativchat.view.Form;


public class Controller implements ActionListener {
    Injector injector = Guice.createInjector(new AppModule());
    public BrokerMensagemService service = injector.getInstance(BrokerMensagemService.class);
    private Form form;

    public Controller(Form form) {
        this.form = form;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String mensagem = form.getMensagem();
        if (mensagem != null && !mensagem.trim().isEmpty()) {
            String nome = form.getNome();
            service.enviarMensagem(new Mensagem(nome, mensagem));
            form.setMensagem(form.getNome() + ": " + mensagem);
            form.setMensagem("");
        } else {
            javax.swing.JOptionPane.showMessageDialog(form, "Digite uma mensagem antes de enviar.");
        }
        new Thread(() -> {
            service.buscarMensagens(form);
        }).start();
    }
    public BrokerMensagemService getService() {
        return service;
    }
}
