package br.univille;

import com.azure.core.amqp.AmqpTransportType;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import br.univille.ativchat.view.Form;

public class Consumer {
    private static final String QUEUE_NAME = "queue-das1";
    private static final String FQDNS = "sb-das12025-test-brazilsouth.servicebus.windows.net";

    public static void iniciarRecebimento(Form form) {
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();

        ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(FQDNS)
                .credential(credential)
                .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
                .processor()
                .queueName(QUEUE_NAME)
                .receiveMode(ServiceBusReceiveMode.RECEIVE_AND_DELETE)
                .processMessage(context -> {
                    String mensagem = context.getMessage().getBody().toString();
                    if (!mensagem.contains("nome=" + form.getNome())) { // Ignora mensagens do próprio usuário
                        form.setMensagem("Recebido: " + mensagem);
                    }
                })
                .processError(context -> {
                    form.setMensagem("Erro ao receber mensagem: " + context.getException().getMessage());
                })
                .buildProcessorClient();

        processorClient.start();
    }

    public static void main(String[] args) {
        Form form = new Form();
        iniciarRecebimento(form);
    }
}