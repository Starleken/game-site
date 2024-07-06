package com.leafall.accountsservice.controller;

import com.leafall.accountsservice.dto.game.GameCreateDto;
import com.leafall.accountsservice.dto.game.GameResponseDto;
import com.leafall.accountsservice.dto.game.GameResponseShortDto;
import com.leafall.accountsservice.dto.game.GameUpdateDto;
import com.leafall.accountsservice.entity.GameEntity;
import com.leafall.accountsservice.entity.GenreEntity;
import com.leafall.accountsservice.entity.PostEntity;
import com.leafall.accountsservice.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.leafall.accountsservice.utils.LogUtils.getRequest;
import static com.leafall.accountsservice.utils.LogUtils.getResultRequest;

@RestController
@RequestMapping("/games")
@Slf4j
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ResponseEntity<List<GameResponseShortDto>> findAll() {
        log.info(getRequest(GameEntity.class, "find all"));
        var result = gameService.findAll();
        log.info(getRequest(GameEntity.class, result.size()));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponseDto> findById(@PathVariable Long id) {
        log.info(getRequest(GameEntity.class, "find by id", "id", id));
        var result = gameService.findById(id);
        log.info(getRequest(GameEntity.class, "find by id", result));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<GameResponseDto> create(@ModelAttribute GameCreateDto createDto) {
        log.info(getRequest(GameEntity.class, "create", createDto));
        var result = gameService.create(createDto);
        log.info(getResultRequest(GameEntity.class, "created", result));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<GameResponseDto> update(@RequestBody GameUpdateDto updateDto) {
        log.info(getRequest(GameEntity.class, "update", updateDto));
        var result = gameService.update(updateDto);
        log.info(getResultRequest(GameEntity.class, "updated", result));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info(getRequest(GameEntity.class, "delete", "id", id));
        gameService.deleteById(id);
        log.info(getResultRequest(GameEntity.class, "deleted", "id", id));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
