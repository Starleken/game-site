package com.leafall.accountsservice.core.utils.entity;

import com.leafall.accountsservice.entity.FileEntity;
import com.leafall.accountsservice.entity.PostEntity;

import java.util.Date;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;

public abstract class PostEntityUtils {

    public static PostEntity generate(FileEntity image) {
        var entity = new PostEntity();
        entity.setImageId(image.getId());
        entity.setHeader(faker.name().title());
        entity.setContent(faker.lorem().paragraph());

        return entity;
    }
}
