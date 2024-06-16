package com.leafall.accountsservice.controller;

import com.leafall.accountsservice.dto.user.*;
import com.leafall.accountsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    
    @GetMapping
    public ResponseEntity<List<UserResponseShortDto>> findAll() {
        log.info("Find all users requested");
        var entities = userService.findAll();
        log.info("The number of users found: {}", entities.size());

        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/ids")
    public ResponseEntity<List<UserResponseShortDto>> findAllByIds(@RequestBody List<Long> ids) {
        log.info("Find all by ids requested");
        var entities = userService.findAllById(ids);
        log.info("The number of users found: {}", entities.size());

        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable long id) {
        log.info("Find user by id requested. Id: {}", id);
        var entity = userService.findById(id);
        log.info("Found user: {}", entity);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto createDto) {
        log.info("User create request: {}", createDto);
        var entity = userService.create(createDto);
        log.info("Created user: {}", entity);

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> update(@RequestBody UserUpdateDto updateDto) {
        log.info("User update request: {}", updateDto);
        var entity = userService.update(updateDto);
        log.info("Updated user: {}", entity);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        log.info("Delete user by id requested. Id: {}", id);
        userService.deleteById(id);
        log.info("Deleted user. id: {}", id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordDto passwordDto) {
        log.info("Change password requested. Id: {}", passwordDto.getId());
        userService.changePassword(passwordDto);
        log.info("Password changed. id: {}", passwordDto.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/email")
    public ResponseEntity<Void> changeEmail(@RequestBody ChangeEmailDto emailDto) {
        log.info("Change email requested. Id: {}", emailDto.getId());
        userService.changeEmail(emailDto);
        log.info("Email changed. id: {}", emailDto.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
