package com.leafall.accountsservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leafall.accountsservice.core.db.DbCleaner;
import com.leafall.accountsservice.initializer.PostgresInitializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import service.HistoryService;

import static org.mockito.Mockito.reset;

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
