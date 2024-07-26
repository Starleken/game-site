package org.leafall.authservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.leafall.authservice.BaseIntegrationTest;
import org.leafall.authservice.core.db.TokenDbHelper;
import org.leafall.authservice.core.db.UserDbHelper;
import org.leafall.authservice.entity.TokenEntityStatus;
import org.leafall.authservice.exception.EntityNotFoundException;
import org.leafall.authservice.exception.IllegalActionException;
import org.leafall.authservice.exception.TokenIsUsedException;
import org.leafall.authservice.repository.TokenRepository;
import org.leafall.authservice.repository.UserRepository;
import org.leafall.authservice.service.impl.TokenServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.leafall.authservice.core.utils.entity.UserEntityUtils.generateUser;

public class TokenServiceTest extends BaseIntegrationTest {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenDbHelper tokenHelper;

    @Autowired
    private UserDbHelper userHelper;

    @BeforeEach
    void setUp() {
        tokenHelper.clear();
    }

    @Test
    void testGenerateAccessToken_happyPath() {
        //given
        var saved = userHelper.saveUser();

        //when
        var token = assertDoesNotThrow(() ->
                tokenService.generateAccessToken(saved.getId()));

        //then
        assertNotNull(token);
    }

    @Test
    void testGenerateAccessToken_whenUserNotFound() {
        //given
        var user = generateUser(1L);

        //when
        assertThrows(EntityNotFoundException.class,
                () -> tokenService.generateAccessToken(user.getId()));

        //then
    }

    @Test
    void testGenerateRefreshToken_happyPath() {
        //given
        var saved = userHelper.saveUser();

        //when
        var token = tokenService.generateRefreshToken(saved.getId());

        //then
        var tokenEntity = tokenHelper.findByToken(token);

        assertEquals(saved.getEmail(), tokenEntity.getUser().getEmail());
        assertEquals(tokenEntity.getStatus(), TokenEntityStatus.ISSUED);
    }

    @Test
    void testGenerateRefreshToken_whenUserNotFound() {
        //given
        var user = generateUser(1L);

        //when
        assertThrows(EntityNotFoundException.class,
                () -> tokenService.generateRefreshToken(user.getId()));

        //then
    }

    @Test
    void testValidateRefreshToken_happyPath() {
        //given
        var token = tokenHelper.saveToken();

        //when
        assertDoesNotThrow(() -> tokenService.validateRefreshToken(token));

        //then
    }


    @Test
    void testValidateRefreshToken_whenTokenIsUsed() {
        //given
        var token = tokenHelper.saveToken(TokenEntityStatus.USED);

        //when
        assertThrows(TokenIsUsedException.class,
                () -> tokenService.validateRefreshToken(token));

        //then
        var tokenEntity = tokenHelper.findByToken(token);
        assertEquals(TokenEntityStatus.WITHDRAWN, tokenEntity.getStatus());
    }

    @Test
    void testValidateRefreshToken_whenTokenIsWithdrawn() {
        //given
        var token = tokenHelper.saveToken(TokenEntityStatus.WITHDRAWN);

        //when
        assertThrows(IllegalActionException.class,
                () -> tokenService.validateRefreshToken(token));

        //then
    }

    @Test
    void testRefresh_happyPath() throws Exception {
        //given
        var token = tokenHelper.saveToken();
        Thread.sleep(1000);

        //when
        var refresh = tokenService.refresh(token);

        //then
        var newToken = tokenHelper.findByToken(refresh.getRefreshToken());
        var oldToken = tokenHelper.findByToken(token);

        assertEquals(TokenEntityStatus.ISSUED, newToken.getStatus());
        assertEquals(TokenEntityStatus.USED, oldToken.getStatus());
    }

    @Test
    void testRefresh_whenTokenIsUsed() {
        //given
        var token = tokenHelper.saveToken(TokenEntityStatus.USED);

        //when
        assertThrows(TokenIsUsedException.class,
                () -> tokenService.refresh(token));

        //then
    }
}
