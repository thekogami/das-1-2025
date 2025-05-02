package br.univille;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClient;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClientBuilder;

public class Subscription {
    public static void main(String[] args) {
        String topicName = "topic-das1";
        String subscriptionName = "subscription-felipe";
        String fqdns = "sb-das12025-test-brazilsouth.servicebus.windows.net";

        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder()
                .build();

        ServiceBusAdministrationClient adminClient = new ServiceBusAdministrationClientBuilder()
                .credential(fqdns, credential) // Configura o namespace diretamente
                .buildClient();

        try {
            // Verifica se a assinatura existe
            if (!adminClient.getSubscriptionExists(topicName, subscriptionName)) {
                System.out.println("Assinatura não encontrada. Criando a assinatura...");
                adminClient.createSubscription(topicName, subscriptionName);
                System.out.println("Assinatura criada: " + subscriptionName);
            } else {
                System.out.println("Assinatura já existe: " + subscriptionName);
            }
        } catch (Exception e) {
            System.err.println("Erro ao verificar ou criar assinatura: " + e.getMessage());
        }
    }
}