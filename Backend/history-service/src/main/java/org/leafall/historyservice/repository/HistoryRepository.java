package org.leafall.historyservice.repository;

import org.bson.types.ObjectId;
import org.leafall.historyservice.entity.HistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends MongoRepository<HistoryEntity, String> {
}
