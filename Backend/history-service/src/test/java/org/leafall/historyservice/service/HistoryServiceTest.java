package org.leafall.historyservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.leafall.historyservice.entity.HistoryEntity;
import org.leafall.historyservice.repository.HistoryRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceTest {

    @Mock
    private HistoryRepository historyRepository;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private HistoryService consumerService;

    @Test
    void testSave_happyPath() {
        //given
        var record = new ConsumerRecord<>("history", 0, 0L, "key", "payload");
        record.headers().add(new RecordHeader("header-key", "header-value".getBytes()));

        //when
        consumerService.save(record);

        //when
        verify(historyRepository, times(1)).save(any(HistoryEntity.class));
    }
}
