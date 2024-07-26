package org.leafall.mainservice.core.utils.entity;

import org.leafall.fileservicestarter.dto.FileResponseDto;
import org.leafall.mainservice.entity.PostEntity;

import static java.util.UUID.*;
import static org.leafall.mainservice.core.utils.FakerUtils.*;

public abstract class PostEntityUtils {

    public static PostEntity generatePost() {
        var entity = new PostEntity();
        entity.setImageId(randomUUID());
        entity.setHeader(faker.name().title());
        entity.setContent(faker.lorem().paragraph());

        return entity;
    }

    public static PostEntity generatePost(FileResponseDto image) {
        var entity = new PostEntity();
        entity.setImageId(image.getId());
        entity.setHeader(faker.name().title());
        entity.setContent(faker.lorem().paragraph());

        return entity;
    }
}
