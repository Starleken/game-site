package com.example.fileservice.entity.listener;

import com.example.fileservice.entity.aware.AuthorAware;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorListener {

    //TODO get logged-in user

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
