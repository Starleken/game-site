package org.leafall.historyservicestarter.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.leafall.historyservicestarter.service.HistoryService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(HistoryServiceMapProperties.class)
public class HistoryServiceMapAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HistoryService historyService(HistoryServiceMapProperties properties) {
        return new HistoryService(kafkaTemplate(properties));
    }

    @Bean
    public Map<String, Object> producerConfigs(HistoryServiceMapProperties properties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                properties.getKafkaUrl());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory(HistoryServiceMapProperties properties) {
        return new DefaultKafkaProducerFactory<>(producerConfigs(properties));
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(HistoryServiceMapProperties properties) {
        return new KafkaTemplate<>(producerFactory(properties));
    }
}
