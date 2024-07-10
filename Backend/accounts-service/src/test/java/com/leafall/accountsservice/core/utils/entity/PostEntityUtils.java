package com.leafall.accountsservice.core.utils.entity;

import com.leafall.accountsservice.entity.PostEntity;
import dto.FileResponseDto;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;

public abstract class PostEntityUtils {

    public static PostEntity generate(FileResponseDto image) {
        var entity = new PostEntity();
        entity.setImageId(image.getId());
        entity.setHeader(faker.name().title());
        entity.setContent(faker.lorem().paragraph());

        return entity;
    }
}
