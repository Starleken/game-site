package org.leafall.historyservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.leafall.historyservice.entity.HistoryEntity;
import org.leafall.historyservice.repository.HistoryRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final ObjectMapper objectMapper;

    private final String TOPIC_NAME = "history";

    @KafkaListener(topics = TOPIC_NAME)
    public void save(ConsumerRecord<String, String> record) {
        var entity = new HistoryEntity();
        entity.setHeaders(writeHeadersAsJson(record.headers()));
        entity.setPayload(record.value());

        historyRepository.save(entity);
    }


    @SneakyThrows
    private String writeHeadersAsJson(Headers kafkaHeaders) {
        Map<String, String> headers = new HashMap<>();

        for (Header header : kafkaHeaders) {
            headers.put(header.key(), new String(header.value(), StandardCharsets.UTF_8));
        }
        return objectMapper.writeValueAsString(headers);
    }
}
