package com.leafall.accountsservice.entity.aware;

public interface AuthorAware {

    void setCreatedBy(String createdBy);

    String getCreatedBy();

    void setUpdatedBy(String updatedBy);

    String getUpdatedBy();
}
