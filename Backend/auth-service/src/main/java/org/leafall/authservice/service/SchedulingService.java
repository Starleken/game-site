package org.leafall.authservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


public interface SchedulingService {

    void deleteOldTokens();
}
