package org.leafall.mainservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.leafall.historyservicestarter.service.HistoryService;
import org.leafall.mainservice.core.db.DbCleaner;
import org.leafall.mainservice.initializer.PostgresInitializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@ContextConfiguration(initializers = { PostgresInitializer.class })
@AutoConfigureMockMvc
public class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected DbCleaner dbCleaner;

    @MockBean
    protected KafkaProducer kafkaProducer;

    @MockBean
    protected HistoryService historyService;
}
