package org.leafall.authservice.entity.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.leafall.authservice.entity.aware.AuthorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorListener {

    @PrePersist
    private void beforeFirstSave(Object entity) {
        var username = getCurrentUser();
        if (username != null) {
            if (entity instanceof AuthorAware) {
                ((AuthorAware) entity).setCreatedBy(username);
            }
        }
    }

    @PreUpdate
    private void beforeUpdate(Object entity) {
        var username = getCurrentUser();
        if (username != null) {
            if (entity instanceof AuthorAware) {
                ((AuthorAware) entity).setUpdatedBy(username);
            }
        }
    }

    private String getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        }

        return null;
    }
}
