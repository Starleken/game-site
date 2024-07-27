package org.leafall.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leafall.authservice.constants.TimeConstants;
import org.leafall.authservice.repository.TokenRepository;
import org.leafall.authservice.service.SchedulingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.leafall.authservice.constants.TimeConstants.*;
import static org.leafall.authservice.constants.TimeConstants.DAY;
import static org.leafall.authservice.utils.TimeUtils.getCurrentTimeFromUTC;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingServiceImpl implements SchedulingService {

    private final TokenRepository tokenRepository;

    @Scheduled(fixedDelay = DAY)
    @Override
    @Transactional
    public void deleteOldTokens() {
        var time = getCurrentTimeFromUTC() - WEEK;
        var tokens = tokenRepository.findAllByCreatedAtLessThan(time);

        tokenRepository.deleteAllInBatch(tokens);
        log.info("Old tokens deleted: {}", tokens.size());
    }
}
