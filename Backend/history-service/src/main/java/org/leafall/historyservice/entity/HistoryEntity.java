package org.leafall.historyservice.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("history")
@Data
public class HistoryEntity {

    @Id
    private String id;

    private String headers;
    private String payload;
}
