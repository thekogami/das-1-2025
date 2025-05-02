package br.univille;

import com.azure.core.amqp.AmqpTransportType;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;

public class Subscriber {
    public static void main(String[] args) {
        String topicName = "topic-das1";
        String subscriptionName = "subscription-felipe";
        String fqdns = "sb-das12025-test-brazilsouth.servicebus.windows.net";

        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();

        // Configura o cliente do Service Bus
        ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(fqdns)
                .credential(credential)
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .processor()
                .topicName(topicName)
                .subscriptionName(subscriptionName)
                .receiveMode(ServiceBusReceiveMode.PEEK_LOCK)
                .processMessage(context -> {
                    // Processa a mensagem recebida
                    String mensagem = context.getMessage().getBody().toString();
                    System.out.println("Mensagem recebida: " + mensagem);

                    // Confirma o processamento da mensagem
                    try {
                        context.complete();
                    } catch (Exception e) {
                        System.out.println("Erro ao confirmar mensagem: " + e.getMessage());
                    }
                })
                .processError(context -> {
                    // Trata erros
                    System.out.println("Erro ao processar mensagem: " + context.getException().getMessage());
                })
                .buildProcessorClient();

        processorClient.start();
        System.out.println("Aguardando mensagens...");

        try {
            System.in.read(); // Aguarda entrada do usuário para encerrar
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            processorClient.close();
        }
    }
}