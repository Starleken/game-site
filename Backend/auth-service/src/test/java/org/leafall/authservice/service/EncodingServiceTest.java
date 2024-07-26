package org.leafall.authservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.leafall.authservice.BaseIntegrationTest;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.leafall.authservice.core.utils.FakerUtils.faker;

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
    void testEncode_whenEncodeSamePasswords_thenReturnDifferentEncoded() {
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
        var isMatch = encodingService.isMatch(password, encoded);

        //then
        Assertions.assertTrue(isMatch);
    }

    @Test
    void testMatch_whenPasswordsIsNotMatch() {
        //given
        var password = faker.internet().password();
        var encoded = encodingService.encode(password + "fake");

        //when
        var isMatch = encodingService.isMatch(password, encoded);

        //then
        Assertions.assertFalse(isMatch);
    }
}
