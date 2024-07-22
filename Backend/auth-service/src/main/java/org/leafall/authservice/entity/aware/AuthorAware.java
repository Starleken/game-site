package org.leafall.authservice.entity.aware;

public interface AuthorAware {

    void setCreatedBy(String createdBy);

    String getCreatedBy();

    void setUpdatedBy(String updatedBy);

    String getUpdatedBy();
}
