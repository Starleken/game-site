package com.example.fileservice.entity.listener;

import com.example.fileservice.entity.aware.TimestampAware;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class TimestampListener {

    @PrePersist
    private void beforeFirstSave(Object entity) {
        if (entity instanceof TimestampAware) {
            ((TimestampAware) entity).setCreatedAt(Instant.now().toEpochMilli());
        }
    }

    @PreUpdate
    private void beforeUpdate(Object entity) {
        if (entity instanceof TimestampAware) {
            ((TimestampAware) entity).setUpdatedAt(Instant.now().toEpochMilli());
        }
    }
}
