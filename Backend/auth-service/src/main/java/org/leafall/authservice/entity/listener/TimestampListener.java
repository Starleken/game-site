package org.leafall.authservice.entity.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.leafall.authservice.entity.aware.TimestampAware;
import org.leafall.authservice.utils.TimeUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static org.leafall.authservice.utils.TimeUtils.*;

@Component
@RequiredArgsConstructor
public class TimestampListener {

    @PrePersist
    private void beforeFirstSave(Object entity) {
        if (entity instanceof TimestampAware) {
            ((TimestampAware) entity).setCreatedAt(getCurrentTimeFromUTC());
        }
    }

    @PreUpdate
    private void beforeUpdate(Object entity) {
        if (entity instanceof TimestampAware) {
            ((TimestampAware) entity).setUpdatedAt(getCurrentTimeFromUTC());
        }
    }
}
