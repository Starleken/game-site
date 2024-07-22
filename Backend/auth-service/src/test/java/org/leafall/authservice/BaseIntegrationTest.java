package org.leafall.authservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.leafall.authservice.initializer.PostgresInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles(profiles = "local")
@ContextConfiguration(initializers = { PostgresInitializer.class })
@AutoConfigureMockMvc(addFilters = false)
public class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
