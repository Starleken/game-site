package com.leafall.accountsservice.entity.listener;

import com.leafall.accountsservice.entity.*;
import com.leafall.accountsservice.exception.HistoryMappingException;
import com.leafall.accountsservice.mapper.HistoryMapper;
import entity.HistoryAction;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.HistoryService;

import java.time.Instant;

import static java.lang.String.format;

@Component
@Slf4j
public class HistoryListener {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private HistoryMapper historyMapper;

    @PostPersist
    private void afterFirstSave(Object entity) {
        sendMessage(entity, HistoryAction.CREATED);
    }

    @PostUpdate
    private void afterAnyUpdate(Object entity) {
        sendMessage(entity, HistoryAction.UPDATED);
    }

    @PostRemove
    private void afterRemove(Object entity) {
        sendMessage(entity, HistoryAction.DELETED);
    }

    private void sendMessage(Object entity, HistoryAction action) {
        try {
            var entryId = String.valueOf(Instant.now().toEpochMilli());
            var historyDto = mapToHistoryDto(entity);
            historyService.sendMessage(historyDto, entryId, action);
        } catch (Exception ex) {
            log.error("Exception on sending history", ex);
        }
    }

    private Object mapToHistoryDto(Object entity) {
        if (entity instanceof CommentEntity) {
            return historyMapper.mapToHistoryDto((CommentEntity) entity);
        } else if (entity instanceof GenreEntity) {
            return historyMapper.mapToHistoryDto((GenreEntity) entity);
        } else if (entity instanceof PostEntity) {
            return historyMapper.mapToHistoryDto((PostEntity) entity);
        } else if (entity instanceof ReviewEntity) {
            return historyMapper.mapToHistoryDto((ReviewEntity) entity);
        } else if (entity instanceof GameEntity) {
            return historyMapper.mapToHistoryDto((GameEntity) entity);
        }

        throw new HistoryMappingException(
                format("History mapper is not implemented for %s class", entity.getClass().getSimpleName())
        );
    }
}
