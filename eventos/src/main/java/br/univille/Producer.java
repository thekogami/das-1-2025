package br.univille;

import com.azure.core.amqp.AmqpTransportType;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

import br.univille.ativchat.model.Mensagem;

public class Producer {
    private static final String NAMESPACE = "sb-das12025-test-brazilsouth.servicebus.windows.net";
    private static final String QUEUE_NAME = "queue-das1";

    private static final DefaultAzureCredential CREDENTIAL = new DefaultAzureCredentialBuilder().build();

    public static void enviarMensagem(Mensagem mensagem) {
        try (ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(NAMESPACE)
                .credential(CREDENTIAL)
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .sender()
                .queueName(QUEUE_NAME)
                .buildClient()) {

            senderClient.sendMessage(new ServiceBusMessage(mensagem.texto()));
            System.out.println("Mensagem enviada: " + mensagem.texto());
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
        }
    }
}