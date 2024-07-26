package org.leafall.mainservice.core.utils.entity;

import org.leafall.mainservice.core.utils.FakerUtils;
import org.leafall.mainservice.entity.GenreEntity;

public abstract class GenreEntityUtils {

    public static GenreEntity generateGenre() {
        var genre = new GenreEntity();
        genre.setName(FakerUtils.faker.lorem().sentence());

        return genre;
    }
}
