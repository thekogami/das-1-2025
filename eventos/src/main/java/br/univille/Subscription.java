package br.univille;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClient;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClientBuilder;

public class Subscription {
    public static void main(String[] args) {
        String topicName = "topic-das1"; // Nome do tópico
        String subscriptionName = "subscription-felipe"; // Nome da assinatura
        String fqdns = "sb-das12025-test-brazilsouth.servicebus.windows.net"; // Namespace do Service Bus

        // Configura as credenciais padrão do Azure
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder()
                .build();

        // Cria o cliente de administração do Service Bus
        ServiceBusAdministrationClient adminClient = new ServiceBusAdministrationClientBuilder()
                .endpoint("https://" + fqdns) // Configura o namespace corretamente
                .credential(credential) // Configura as credenciais
                .buildClient();

        try {
            // Verifica se o tópico existe
            if (!adminClient.getTopicExists(topicName)) {
                System.out.println("Tópico não encontrado. Criando o tópico...");
                adminClient.createTopic(topicName);
                System.out.println("Tópico criado: " + topicName);
            } else {
                System.out.println("Tópico já existe: " + topicName);
            }

            // Verifica se a assinatura existe
            if (!adminClient.getSubscriptionExists(topicName, subscriptionName)) {
                System.out.println("Assinatura não encontrada. Criando a assinatura...");
                adminClient.createSubscription(topicName, subscriptionName);
                System.out.println("Assinatura criada: " + subscriptionName);
            } else {
                System.out.println("Assinatura já existe: " + subscriptionName);
            }
        } catch (Exception e) {
            System.err.println("Erro ao criar tópico ou assinatura: " + e.getMessage());
        }
    }
}