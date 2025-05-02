package br.univille.ativchat.service.impl;

import java.util.List;
import java.util.function.Consumer;

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

public class BrokerMensagemServiceImpl implements BrokerMensagemService {
    String topicName = "topic-chat";
    String serviceBus = "sb-das12025-test-brazilsouth.servicebus.windows.net";
    String subscription = "subscription-felipe";

    DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();

    ServiceBusProcessorClient processorClient;

    public BrokerMensagemServiceImpl() {
        ServiceBusAdministrationClient adminClient = new ServiceBusAdministrationClientBuilder()
                .endpoint("https://" + serviceBus)
                .credential(credential)
                .buildClient();

        try {
            if (!adminClient.getSubscriptionExists(topicName, subscription)) {
                adminClient.createSubscription(topicName, subscription);
                System.out.println("Assinatura criada: " + subscription);
            } else {
                System.out.println("Assinatura já existe: " + subscription);
            }
        } catch (Exception e) {
            System.err.println("Erro ao verificar ou criar assinatura: " + e.getMessage());
        }

        processorClient = new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(serviceBus)
                .credential(credential)
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .processor()
                .topicName(topicName)
                .subscriptionName(subscription)
                .receiveMode(ServiceBusReceiveMode.RECEIVE_AND_DELETE)
                .processMessage(context -> {
                    String texto = context.getMessage().getBody().toString();
                    System.out.println("Mensagem recebida: " + texto);
                })
                .processError(context -> {
                    System.err.println("Erro ao processar mensagem: " + context.getException().getMessage());
                })
                .buildProcessorClient();

        processorClient.start();
    }

    @Override
    public void enviarMensagem(Mensagem mensagem) {
        try (ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(serviceBus)
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
    public void buscarMensagens(List<Mensagem> mensagens) {
        // Implementação do método
    }
}