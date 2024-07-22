package com.leafall.accountsservice.entity.listener;

import com.leafall.accountsservice.entity.aware.AuthorAware;
import com.leafall.tokenservice.service.AuthContextHolder;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorListener {


    @PrePersist
    private void beforeFirstSave(Object entity) {
        if (entity instanceof AuthorAware) {
            var context = AuthContextHolder.get();
            if (context != null) {
                ((AuthorAware) entity).setCreatedBy(context.getUserId().toString());
            }
        }
    }

    @PreUpdate
    private void beforeUpdate(Object entity) {
        if (entity instanceof AuthorAware) {
            var context = AuthContextHolder.get();
            if (context != null) {
                ((AuthorAware) entity).setUpdatedBy(context.getUserId().toString());
            }
        }
    }
}
