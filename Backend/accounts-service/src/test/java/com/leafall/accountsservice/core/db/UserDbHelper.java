package com.leafall.accountsservice.core.db;

import com.leafall.accountsservice.core.utils.entity.UserEntityUtils;
import com.leafall.accountsservice.entity.UserEntity;
import com.leafall.accountsservice.repository.UserRepository;
import com.leafall.accountsservice.service.EncodingService;
import com.leafall.accountsservice.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.leafall.accountsservice.core.utils.entity.UserEntityUtils.*;
import static com.leafall.accountsservice.utils.ExceptionUtils.*;

@Component
@RequiredArgsConstructor
public class UserDbHelper {

    private final UserRepository userRepository;
    private final EncodingService encodingService;

    public void clear() {
        userRepository.deleteAll();
    }

    public UserEntity saveUser() {
        var generated = generateUser();
        generated.setPassword(encodingService.encode(generated.getPassword()));
        return userRepository.save(generated);
    }

    public UserEntity saveUser(String username, String email) {
        var generated = generateUser(username, email);
        generated.setPassword(encodingService.encode(generated.getPassword()));
        return userRepository.save(generated);
    }

    public UserEntity saveUser(String password) {
        var generated = generateUser(password);
        generated.setPassword(encodingService.encode(generated.getPassword()));
        return userRepository.save(generated);
    }

    public List<UserEntity> saveUsers(int count) {
        List<UserEntity> entities = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            var generated = generateUser();
            generated.setPassword(encodingService.encode(generated.getPassword()));
            entities.add(generated);
        }
        return userRepository.saveAll(entities);
    }

    public UserEntity findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class));
    }
}
