package org.leafall.authservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.leafall.authservice.BaseIntegrationTest;
import org.leafall.authservice.core.db.TokenDbHelper;
import org.leafall.authservice.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class SchedulingServiceTest extends BaseIntegrationTest {

    @Autowired
    private SchedulingService schedulingService;

    @Autowired
    private TokenDbHelper tokenDbHelper;

    @BeforeEach
    void setUp() {
        tokenDbHelper.clear();
    }

    @Test
    void testClearOldTokens_whenNewToken_thenDontDelete() {
        //given
        var saved = tokenDbHelper.saveToken();

        //when
        schedulingService.deleteOldTokens();

        //then
        Assertions.assertNotNull(tokenDbHelper.findByToken(saved));
    }

    @Test
    void testClearOldTokens_whenOldToken_thenDelete() {
        //given
        var saved = tokenDbHelper.saveToken(1L);

        //when
        schedulingService.deleteOldTokens();

        //then
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> tokenDbHelper.findByToken(saved));
    }
}
