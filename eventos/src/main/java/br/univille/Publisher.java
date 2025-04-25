package br.univille;

import com.azure.core.amqp.AmqpTransportType;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

public class Publisher {
    public static void main(String[] args) {
        String topicName = "topic-das1";
        
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder()
                .build();

        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .fullyQualifiedNamespace("sb-das12025-test-brazilsouth.servicebus.windows.net")
                .credential(credential)
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .sender()
                .topicName(topicName)
                .buildClient();

        senderClient.sendMessage(new ServiceBusMessage("Ola mundo!"));
        senderClient.close();
        System.out.println("Enviado");

    }
}
