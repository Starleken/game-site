package org.leafall.authservice.entity.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.leafall.authservice.entity.aware.AuthorAware;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorListener {

    @PrePersist
    private void beforeFirstSave(Object entity) {
        if (entity instanceof AuthorAware) {
            ((AuthorAware) entity).setCreatedBy("test");
        }
    }

    @PreUpdate
    private void beforeUpdate(Object entity) {
        if (entity instanceof AuthorAware) {
            ((AuthorAware) entity).setUpdatedBy("test");
        }
    }
}
