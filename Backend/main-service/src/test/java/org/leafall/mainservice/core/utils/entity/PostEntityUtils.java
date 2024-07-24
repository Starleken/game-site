package org.leafall.mainservice.core.utils.entity;

import org.leafall.fileservicestarter.dto.FileResponseDto;
import org.leafall.mainservice.entity.PostEntity;

import static org.leafall.mainservice.core.utils.FakerUtils.*;

public abstract class PostEntityUtils {

    public static PostEntity generate(FileResponseDto image) {
        var entity = new PostEntity();
        entity.setImageId(image.getId());
        entity.setHeader(faker.name().title());
        entity.setContent(faker.lorem().paragraph());

        return entity;
    }
}
