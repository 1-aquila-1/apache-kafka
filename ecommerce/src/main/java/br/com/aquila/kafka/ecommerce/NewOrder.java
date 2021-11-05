package br.com.aquila.kafka.ecommerce;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class NewOrder {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var producer = new KafkaProducer<String, String>(properties());
        var key = UUID.randomUUID().toString();
        var value = key+"1234,3456,111"; 
        var newOrderRecord = new ProducerRecord<>(Topic.ECOMMERCE_NEW_ORDER.name(), key, value); 
        
        Callback callback = (data, ex)->{
            if(ex != null){
                ex.printStackTrace();
                return;
            }
            System.out.println("Sucesso enviado " + data.topic()
            .concat(" /particion " + data.partition()+"")
            .concat(" /offset " + data.offset())
            .concat(" /timestamp " + data.timestamp()));
        };
        
        var emailValue = "Pedido de compra em processamento";
        var emailRecord = new ProducerRecord<>(Topic.ECOMMERCE_SEND_EMAIL.name(), key, emailValue); 
        producer.send(emailRecord, callback).get();
        producer.send(newOrderRecord, callback).get();
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
