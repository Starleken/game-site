package com.leafall.accountsservice.controller;

import com.leafall.accountsservice.dto.user.*;
import com.leafall.accountsservice.entity.UserEntity;
import com.leafall.accountsservice.service.UserService;
import com.leafall.accountsservice.utils.LogUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.leafall.accountsservice.utils.LogUtils.*;
import static com.leafall.accountsservice.utils.LogUtils.getRequest;

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
}
