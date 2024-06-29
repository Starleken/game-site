package com.leafall.accountsservice.service.impl;

import com.leafall.accountsservice.dto.user.*;
import com.leafall.accountsservice.entity.UserEntity;
import com.leafall.accountsservice.mapper.UserMapper;
import com.leafall.accountsservice.repository.UserRepository;
import com.leafall.accountsservice.service.EncodingService;
import com.leafall.accountsservice.service.UserService;
import com.leafall.accountsservice.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.leafall.accountsservice.utils.ExceptionUtils.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EncodingService encodingService;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseShortDto> findAll() {
        var entities = userRepository.findAll();

        return entities.stream()
                .map(userMapper::mapToShortDto)
                .toList();
    }

    @Override
    public UserResponseDto findById(long id) {
        var found = userRepository.findById(id)
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class));

        return userMapper.mapToDto(found);
    }

    @Override
    public List<UserResponseShortDto> findAllById(List<Long> ids) {
        var entities = userRepository.findAllById(ids);

        return entities.stream()
                .map(userMapper::mapToShortDto)
                .toList();
    }

    @Override
    public UserResponseDto create(UserCreateDto createDto) {
        checkIfUniqueFieldsExists(createDto.getEmail(), createDto.getUsername());

        var entity = userMapper.mapToEntity(createDto);
        entity.setCreatedAt(new Date());
        entity.setPassword(encodingService.encode(createDto.getPassword()));

        var saved = userRepository.save(entity);
        return userMapper.mapToDto(saved);
    }

    @Override
    public UserResponseDto update(UserUpdateDto updateDto) {
        checkIfUsernameExists(updateDto.getUsername());

        var found = userRepository.findById(updateDto.getId())
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class));

        userMapper.update(found, updateDto);
        var saved = userRepository.save(found);

        return userMapper.mapToDto(saved);
    }

    @Override
    public void deleteById(long id) {
        userRepository.findById(id)
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class));

        userRepository.deleteById(id);
    }

    @Override
    public void changePassword(ChangePasswordDto passwordDto) {
        var found = userRepository.findById(passwordDto.getId())
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class));

        if (encodingService.isMatch(passwordDto.getOldPassword(), found.getPassword())) {
            found.setPassword(encodingService.encode(passwordDto.getNewPassword()));
            userRepository.save(found);
            return;
        }

        throwIllegalActionException("Passwords don't match");
    }

    @Override
    public void changeEmail(ChangeEmailDto emailDto) {
        checkIfEmailExists(emailDto.getNewEmail());

        var found = userRepository.findById(emailDto.getId())
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class));
        found.setEmail(emailDto.getNewEmail());

        userRepository.save(found);
    }

    private void checkIfUniqueFieldsExists(String email, String username) {
        checkIfEmailExists(email);
        checkIfUsernameExists(username);
    }

    private void checkIfEmailExists(String email) {
        var found = userRepository.findByEmail(email);

        if (found.isPresent()) {
            throwIllegalActionException("Email: " + email + " exists");
        }
    }

    private void checkIfUsernameExists(String username) {
        var found = userRepository.findByUsername(username);

        if (found.isPresent()) {
            throwIllegalActionException("Username: " + username + " exists");
        }
    }
}
