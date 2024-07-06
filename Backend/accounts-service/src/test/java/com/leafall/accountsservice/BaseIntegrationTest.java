package com.leafall.accountsservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leafall.accountsservice.core.db.DbCleaner;
import com.leafall.accountsservice.initializer.PostgresInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest()
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
}
