package br.univille;

import com.azure.core.amqp.AmqpTransportType;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import br.univille.ativchat.model.Mensagem;

public class Producer {
    private static final String QUEUE_NAME = "queue-das1";
    private static final String FQDNS = "sb-das12025-test-brazilsouth.servicebus.windows.net";

    public static void enviarMensagem(Mensagem mensagem) {
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();

        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
            .fullyQualifiedNamespace(FQDNS)
            .credential(credential)
            .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
            .sender()
            .queueName(QUEUE_NAME)
            .buildClient();

        senderClient.sendMessage(new ServiceBusMessage(mensagem.toString()));
        senderClient.close();
    }
}