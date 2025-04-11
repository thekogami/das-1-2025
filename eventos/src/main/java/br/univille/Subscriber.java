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
        String subscriptionName = "subscription-walter";
        String fqdns = "sb-das12025-test-brazilsouth.servicebus.windows.net";

        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder()
                .build();

        ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(fqdns)
                .credential(credential)
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .processor()
                .topicName(topicName)
                .subscriptionName(subscriptionName)
                .receiveMode(ServiceBusReceiveMode.PEEK_LOCK)
                .processMessage(context -> {
                    System.out.println("Mensagem recebida: " + context.getMessage().getBody().toString());
                    context.complete();
                })
                .processError(context -> {
                    System.out.println("Erro: " + context.getException().getMessage());
                })
                .buildProcessorClient();

        processorClient.start();
        System.out.println("Aguardando mensagens...");
        try {
            Thread.sleep(5000);
            System.in.read(); // colocar sleep aqui
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            processorClient.close();
        }
    }
}
