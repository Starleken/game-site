package com.example.fileservice.entity.listener;

import com.example.fileservice.entity.FileEntity;
import com.example.fileservice.exception.HistoryMappingException;
import com.example.fileservice.mapper.HistoryMapper;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.leafall.historyservicestarter.entity.HistoryAction;
import org.leafall.historyservicestarter.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        if (entity instanceof FileEntity) {
            return historyMapper.mapToDto((FileEntity) entity);
        }

        throw new HistoryMappingException(
                format("History mapper is not implemented for %s class", entity.getClass().getSimpleName())
        );
    }
}
