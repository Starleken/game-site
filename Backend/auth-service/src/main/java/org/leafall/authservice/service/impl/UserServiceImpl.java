package org.leafall.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.leafall.authservice.dto.token.RefreshTokenRequestDto;
import org.leafall.authservice.dto.token.RefreshTokenResponseDto;
import org.leafall.authservice.dto.user.*;
import org.leafall.authservice.entity.UserEntity;
import org.leafall.authservice.entity.UserEntityRole;
import org.leafall.authservice.mapper.UserMapper;
import org.leafall.authservice.repository.UserRepository;
import org.leafall.authservice.service.EncodingService;
import org.leafall.authservice.service.TokenService;
import org.leafall.authservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.leafall.authservice.entity.UserEntityRole.*;
import static org.leafall.authservice.utils.ExceptionUtils.getEntityNotFoundException;
import static org.leafall.authservice.utils.ExceptionUtils.throwIllegalActionException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EncodingService encodingService;
    private final TokenService tokenService;
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

    @Override
    public void signup(UserSignupDto signupDto) {
        checkIfUniqueFieldsExists(signupDto.getEmail(), signupDto.getUsername());

        var toSave = userMapper.mapToEntity(signupDto);
        toSave.setRole(USER);
        toSave.setPassword(encodingService.encode(signupDto.getPassword()));

        userRepository.save(toSave);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginDto) {
        var found = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class));

        if (!encodingService.isMatch(loginDto.getPassword(), found.getPassword())) {
            throwIllegalActionException("User by email: " + loginDto.getEmail() + ". Invalid password");
        }

        return LoginResponseDto.builder()
                .userId(found.getId())
                .accessToken(tokenService.generateAccessToken(found.getId()))
                .refreshToken(tokenService.generateRefreshToken(found.getId()))
                .build();
    }

    @Override
    public RefreshTokenResponseDto refresh(RefreshTokenRequestDto refreshDto) {
        var refreshToken = refreshDto.getRefreshToken();

        return tokenService.refresh(refreshToken);
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
