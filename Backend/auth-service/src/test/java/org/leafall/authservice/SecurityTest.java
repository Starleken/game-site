package org.leafall.authservice;

import org.junit.jupiter.api.Test;
import org.leafall.authservice.core.db.TokenDbHelper;
import org.leafall.authservice.core.db.UserDbHelper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc(addFilters = true)
public class SecurityTest extends BaseIntegrationTest {

    private TokenDbHelper tokenDbHelper;

    @Test
    void testFilter_happyPath() {
        //given
        var token = tokenDbHelper.saveToken();

        //when

        //then
    }
}
