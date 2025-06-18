package br.univille;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import java.util.List;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public interface Consumer {
    List<Event> getEvents();
}

class ConsumerImpl implements Consumer {
    private final KafkaConsumer<String, byte[]> consumer;
    private final String topic;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread consumerThread;
    private List<Event> events = new ArrayList<>(); // Lista para armazenar eventos recebidos
    
    public ConsumerImpl(String server, String password) {
        // Configurar propriedades do consumidor Kafka para Azure Event Hubs
        topic = "pixel-events";
        
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        //props.put(ConsumerConfig.GROUP_ID_CONFIG, "$Default");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        
        // Configurações de segurança para Azure Event Hubs
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config", 
                "org.apache.kafka.common.security.plain.PlainLoginModule required " +
                "username=\"$ConnectionString\" " +
                "password=\"" + password + "\";");
        
        // Configurações recomendadas pelo Azure para processamento sem limites
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        
        // Removendo limite máximo de mensagens por polling
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Integer.MAX_VALUE);
        
        // Aumentando os limites de tamanho para virtualmente ilimitado
        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, Integer.MAX_VALUE);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, Integer.MAX_VALUE);
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1); // Valor mínimo para evitar espera
        
        // Tempo de polling suficientemente longo para permitir carregar muitas mensagens
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 1000);
        
        // Configurações para evitar timeouts durante processamento de grandes volumes
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 600000); // 10 minutos
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 60000);    // 1 minuto
        
        // Criar o consumidor
        this.consumer = new KafkaConsumer<>(props);
        startConsuming();
    }
    
    private void startConsuming() {
        if (running.compareAndSet(false, true)) {
            final CountDownLatch shutdownLatch = new CountDownLatch(1);
            
            consumerThread = new Thread(() -> {
                try {
                    // Subscrever ao tópico
                    consumer.subscribe(Collections.singletonList(topic));
                    
                    while (running.get()) {

                        ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(100));
                        
                        for (ConsumerRecord<String, byte[]> record : records) {
                            try {
                                // Processar o registro recebido
                                processRecord(record);
                                
                                // Commit manual após o processamento bem-sucedido
                                consumer.commitSync();
                            } catch (Exception e) {
                                System.err.println("Erro ao processar registro: " + e.getMessage());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Erro no loop de consumo: " + e.getMessage());
                } finally {
                    consumer.close();
                    shutdownLatch.countDown();
                }
            });
            
            consumerThread.start();
        }
    }
    
    private void processRecord(ConsumerRecord<String, byte[]> record) {
        try {
            // Converter os bytes recebidos em uma string (ou objeto)
            String eventAsString = new String(record.value());
            
            System.out.print("Recebido evento: " + eventAsString);
            System.out.print("  do tópico: " + record.topic());
            System.out.print("  na partição: " + record.partition());
            System.out.print("  no offset: " + record.offset());
            System.out.println("  com chave: " + record.key());
            
            Event event = Event.fromJson(eventAsString);
            events.add(event); 

        } catch (Exception e) {
            System.err.println("Erro ao processar evento: " + e.getMessage());
        }
    }
    

    private void stopConsuming() {
        if (running.compareAndSet(true, false)) {
            consumer.wakeup(); // Sinalizar a thread para parar na próxima iteração
            
            // Aguardar pelo término da thread (opcional, com timeout)
            try {
                if (consumerThread != null) {
                    consumerThread.join(5000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interrompido enquanto aguardava a parada do consumidor");
            }
        }
    }

    @Override
    public List<Event> getEvents() {
        List<Event> tempList = new ArrayList<>(events);
        events.clear();
        return tempList;
    }
}