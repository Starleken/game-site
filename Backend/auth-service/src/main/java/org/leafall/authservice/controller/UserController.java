package org.leafall.authservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leafall.authservice.dto.token.RefreshTokenRequestDto;
import org.leafall.authservice.dto.token.RefreshTokenResponseDto;
import org.leafall.authservice.dto.user.*;
import org.leafall.authservice.entity.UserEntity;
import org.leafall.authservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.leafall.authservice.utils.LogUtils.getRequest;
import static org.leafall.authservice.utils.LogUtils.getResultRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseShortDto>> findAll() {
        log.info(getRequest(UserEntity.class, "find all"));
        var entities = userService.findAll();
        log.info(getRequest(UserEntity.class, entities.size()));

        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/ids")
    public ResponseEntity<List<UserResponseShortDto>> findAllByIds(@RequestBody List<Long> ids) {
        log.info(getRequest(UserEntity.class, "find all by ids"));
        var entities = userService.findAllById(ids);
        log.info(getRequest(UserEntity.class, entities.size()));

        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable long id) {
        log.info(getRequest(UserEntity.class, "find user by id", "id", id));
        var entity = userService.findById(id);
        log.info(getRequest("User", "found", "Id", id));

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto createDto) {
        log.info("User create request: {}", createDto);
        var entity = userService.create(createDto);
        log.info(getResultRequest(UserEntity.class, "created", entity));

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> update(@RequestBody UserUpdateDto updateDto) {
        log.info("User update request: {}", updateDto);
        var entity = userService.update(updateDto);
        log.info(getResultRequest(UserEntity.class, "updated", entity));

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        log.info(getRequest(UserEntity.class, "delete by id", "Id", id));
        userService.deleteById(id);
        log.info(getResultRequest(UserEntity.class, "deleted", "Id", id));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordDto passwordDto) {
        log.info(getRequest("password", "change", "Id", passwordDto.getId()));
        userService.changePassword(passwordDto);
        log.info(getRequest("password", "changed", "Id", passwordDto.getId()));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/email")
    public ResponseEntity<Void> changeEmail(@RequestBody ChangeEmailDto emailDto) {
        log.info(getRequest("email", "change", "Id", emailDto.getId()));
        userService.changeEmail(emailDto);
        log.info(getRequest("email", "changed", "Id", emailDto.getId()));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserSignupDto signupDto) {
        log.info(getRequest(UserEntity.class, "signup", signupDto));
        userService.signup(signupDto);
        log.info(getResultRequest(UserEntity.class, "registered user", signupDto.getEmail()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginDto) {
        log.info(getRequest(UserEntity.class, "login", loginDto));
        var response = userService.login(loginDto);
        log.info(getResultRequest(UserEntity.class, "is logged in", response));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/tokens/refresh")
    public ResponseEntity<RefreshTokenResponseDto> refresh(@RequestBody RefreshTokenRequestDto refreshDto) {
        log.info(getRequest(UserEntity.class, "refresh", refreshDto));
        var response = userService.refresh(refreshDto);
        log.info(getResultRequest(UserEntity.class, " is refreshed", response));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
