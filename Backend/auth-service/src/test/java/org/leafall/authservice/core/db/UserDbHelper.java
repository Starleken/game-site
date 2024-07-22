package org.leafall.authservice.core.db;

import lombok.RequiredArgsConstructor;
import org.leafall.authservice.entity.UserEntity;
import org.leafall.authservice.repository.UserRepository;
import org.leafall.authservice.service.EncodingService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.leafall.authservice.core.utils.entity.UserEntityUtils.generateUser;
import static org.leafall.authservice.utils.ExceptionUtils.getEntityNotFoundException;

@Component
@RequiredArgsConstructor
public class UserDbHelper {

    private final UserRepository userRepository;
    private final EncodingService encodingService;

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
        var generated = generateUser();
        generated.setPassword(encodingService.encode(password));
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

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class));
    }

    public void clear() {
        userRepository.deleteAll();
    }
}
