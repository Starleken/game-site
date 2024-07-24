package org.leafall.historyservicestarter.service;

import org.leafall.historyservicestarter.entity.HistoryAction;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;

public class HistoryService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String TOPIC_NAME = "history";
    private final String HEADER_NAME = "action";

    public HistoryService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Object dto, String entryId, HistoryAction historyAction) {
        var record = new ProducerRecord<>(TOPIC_NAME, entryId, dto);
        var header = new RecordHeader(HEADER_NAME, historyAction.toString().getBytes());
        record.headers().add(header);
        kafkaTemplate.send(record);
    }
}
