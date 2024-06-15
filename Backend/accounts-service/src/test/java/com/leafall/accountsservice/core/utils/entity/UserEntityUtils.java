package com.leafall.accountsservice.core.utils.entity;

import com.leafall.accountsservice.core.utils.FakerUtils;
import com.leafall.accountsservice.entity.UserEntity;
import com.leafall.accountsservice.entity.UserEntityRole;

import java.util.Date;

import static com.leafall.accountsservice.core.utils.FakerUtils.*;

public abstract class UserEntityUtils {

    public static UserEntity generateUser() {
        var entity = new UserEntity();
        entity.setCreatedAt(new Date());
        entity.setUsername(faker.name().username());
        entity.setEmail(faker.internet().emailAddress());
        entity.setRole(UserEntityRole.USER);
        entity.setPassword(faker.internet().password());

        return entity;
    }

    public static UserEntity generateUser(String username, String email) {
        var generated = generateUser();
        generated.setUsername(username);
        generated.setEmail(email);

        return generated;
    }

    public static UserEntity generateUser(String password) {
        var generated = generateUser();
        generated.setPassword(password);

        return generated;
    }
}
