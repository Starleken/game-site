package org.leafall.authservice.core.utils.entity;

import org.leafall.authservice.entity.UserEntity;
import org.leafall.authservice.entity.UserEntityRole;

import static org.leafall.authservice.core.utils.FakerUtils.faker;

public abstract class UserEntityUtils {

    public static UserEntity generateUser() {
        var entity = new UserEntity();
        entity.setUsername(faker.name().username());
        entity.setEmail(faker.internet().emailAddress());
        entity.setRole(UserEntityRole.USER);
        entity.setPassword(faker.internet().password());

        return entity;
    }

    public static UserEntity generateUser(Long id) {
        var entity = generateUser();
        entity.setId(id);

        return entity;
    }

    public static UserEntity generateUser(String username, String email) {
        var generated = generateUser();
        generated.setUsername(username);
        generated.setEmail(email);

        return generated;
    }
}
