package com.leafall.accountsservice.service;

import com.leafall.accountsservice.BaseIntegrationTest;
import com.leafall.accountsservice.core.utils.FakerUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;

public class EncodingServiceTest extends BaseIntegrationTest {

    @Autowired
    private EncodingService encodingService;

    @Test
    void testEncode_happyPath() {
        //given
        var password = faker.internet().password();

        //when
        var encoded = encodingService.encode(password);

        //then
        Assertions.assertNotEquals(password, encoded);
    }

    @Test
    void testEncode_whenEncodeSamePassword_thenReturnDifferent() {
        //given
        var password = faker.internet().password();

        //when
        var encoded = encodingService.encode(password);
        var secEncoded = encodingService.encode(password);

        //then
        Assertions.assertNotEquals(encoded, secEncoded);
    }

    @Test
    void testMatch_happyPath() {
        //given
        var password = faker.internet().password();
        var encoded = encodingService.encode(password);

        //when
        Assertions.assertTrue(encodingService.isMatch(password, encoded));

        //then
    }

    @Test
    void testMatch_whenPasswordsIsNotEquals() {
        //given
        var password = faker.internet().password();
        var encoded = encodingService.encode(password);

        //when
        Assertions.assertFalse(encodingService
                .isMatch("Fake" + password, encoded));

        //then
    }
}
