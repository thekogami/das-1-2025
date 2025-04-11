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

        DefaultAzureCredential credential = 
            new DefaultAzureCredentialBuilder()
            .build();
        
        ServiceBusAdministrationClient adminClient 
            = new ServiceBusAdministrationClientBuilder()
            .credential(fqdns, credential)
            .buildClient();
        
        adminClient.createSubscription(topicName, subscriptionName);
    }
}
