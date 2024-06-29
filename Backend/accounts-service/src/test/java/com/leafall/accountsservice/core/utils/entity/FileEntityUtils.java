package com.leafall.accountsservice.core.utils.entity;

import com.github.javafaker.Faker;
import com.leafall.accountsservice.core.utils.FakerUtils;
import com.leafall.accountsservice.entity.FileEntity;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;

public abstract class FileEntityUtils {

    public static FileEntity generate() {
        var file = new FileEntity();
        file.setFileUrl(faker.internet().image());
        file.setOriginalFileName(faker.file().fileName());

        return file;
    }
}
