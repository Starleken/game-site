package com.leafall.accountsservice.core.utils.entity;

import com.leafall.accountsservice.core.utils.FakerUtils;
import com.leafall.accountsservice.entity.GenreEntity;

public abstract class GenreEntityUtils {

    public static GenreEntity generate() {
        var genre = new GenreEntity();
        genre.setName(FakerUtils.faker.lorem().sentence());

        return genre;
    }
}
