package org.leafall.mainservice.entity.aware;

public interface TimestampAware {

    void setCreatedAt(Long createdAt);

    Long getCreatedAt();

    void setUpdatedAt(Long updatedAt);

    Long getUpdatedAt();
}
