package br.univille.ativchat.service.impl;

import java.util.List;

import com.azure.core.amqp.AmqpTransportType;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClient;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClientBuilder;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import br.univille.ativchat.model.Mensagem;
import br.univille.ativchat.service.BrokerMensagemService;
import br.univille.ativchat.view.Form;

public class BrokerMensagemServiceImpl implements BrokerMensagemService {
    private final String topicName = "topic-das1"; // Nome do tópico
    private final String serviceBus = "https://sb-das12025-test-brazilsouth.servicebus.windows.net";
    private final String subscription = "subscription-felipe";

    private final DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();

    public BrokerMensagemServiceImpl() {
        ServiceBusAdministrationClient adminClient = new ServiceBusAdministrationClientBuilder()
                .endpoint(serviceBus)
                .credential(credential)
                .buildClient();

        try {
            System.out.println("Verificando se o tópico '" + topicName + "' existe...");
            if (!adminClient.getTopicExists(topicName)) {
                System.out.println("Tópico não encontrado. Criando o tópico...");
                adminClient.createTopic(topicName);
                System.out.println("Tópico criado: " + topicName);
            } else {
                System.out.println("Tópico já existe: " + topicName);
            }

            System.out.println("Verificando se a assinatura '" + subscription + "' existe...");
            if (!adminClient.getSubscriptionExists(topicName, subscription)) {
                System.out.println("Assinatura não encontrada. Criando a assinatura...");
                adminClient.createSubscription(topicName, subscription);
                System.out.println("Assinatura criada: " + subscription);
            } else {
                System.out.println("Assinatura já existe: " + subscription);
            }
        } catch (Exception e) {
            System.err.println("Erro ao verificar ou criar tópico/assinatura: " + e.getMessage());
        }
    }

    @Override
    public void enviarMensagem(Mensagem mensagem) {
        try (ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(serviceBus.replace("https://", "")) // Remove o protocolo para o namespace
                .credential(credential)
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .sender()
                .topicName(topicName)
                .buildClient()) {

            senderClient.sendMessage(new ServiceBusMessage(mensagem.toString()));
            System.out.println("Mensagem enviada: " + mensagem);
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
        }
    }

    @Override
    public void buscarMensagens(Form form) {
        String topicName = "topic-das1"; // Nome do tópico
        String subscriptionName = "subscription-felipe"; // Nome da assinatura
        String fqdns = "sb-das12025-test-brazilsouth.servicebus.windows.net"; // Namespace do Service Bus

        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();

        ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(fqdns)
                .credential(credential)
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .processor()
                .topicName(topicName)
                .subscriptionName(subscriptionName)
                .receiveMode(ServiceBusReceiveMode.PEEK_LOCK)
                .processMessage(context -> {
                    String mensagemRecebida = context.getMessage().getBody().toString();
                    System.out.println("Mensagem recebida: " + mensagemRecebida);
                    form.setMensagem("Recebido: " + mensagemRecebida); // Atualiza o chat
                    try {
                        context.complete();
                    } catch (Exception e) {
                        System.err.println("Erro ao confirmar mensagem: " + e.getMessage());
                    }
                })
                .processError(context -> {
                    System.err.println("Erro ao processar mensagem: " + context.getException().getMessage());
                })
                .buildProcessorClient();

        processorClient.start();
        System.out.println("Aguardando mensagens...");
    }
}