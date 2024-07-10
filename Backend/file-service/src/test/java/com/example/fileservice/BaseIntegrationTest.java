package com.example.fileservice;

import com.example.fileservice.core.db.DbCleaner;
import com.example.fileservice.core.db.FileDbHelper;
import com.example.fileservice.core.utils.FakerUtils;
import com.example.fileservice.entity.FileEntity;
import com.example.fileservice.initializer.PostgresInitializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.example.fileservice.core.utils.FakerUtils.*;

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
    private DbCleaner dbCleaner;

    @Autowired
    protected FileDbHelper fileDbHelper;

    @BeforeEach
    void setUp() {
        dbCleaner.clear();
    }
}
