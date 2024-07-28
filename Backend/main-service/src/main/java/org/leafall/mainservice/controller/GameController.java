package org.leafall.mainservice.controller;

import jakarta.validation.constraints.Min;
import org.leafall.mainservice.dto.game.GameCreateDto;
import org.leafall.mainservice.dto.game.GameResponseDto;
import org.leafall.mainservice.dto.game.GameResponseShortDto;
import org.leafall.mainservice.dto.game.GameUpdateDto;
import org.leafall.mainservice.entity.GameEntity;
import org.leafall.mainservice.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.leafall.mainservice.utils.LogUtils.getRequest;
import static org.leafall.mainservice.utils.LogUtils.getResultRequest;

@RestController
@RequestMapping("/games")
@Slf4j
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ResponseEntity<Page<GameResponseShortDto>> findAll(@RequestParam(defaultValue = "1") @Min(1) Integer page,
                                                              @RequestParam(defaultValue = "5") @Min(0) Integer size) {
        log.info(getRequest(GameEntity.class, "find all"));
        var result = gameService.findAll(page, size);
        log.info(getRequest(GameEntity.class, (int)result.getTotalElements()));

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
    public ResponseEntity<GameResponseDto> create(@ModelAttribute @Valid GameCreateDto createDto) {
        log.info(getRequest(GameEntity.class, "create", createDto));
        var result = gameService.create(createDto);
        log.info(getResultRequest(GameEntity.class, "created", result));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<GameResponseDto> update(@RequestBody @Valid GameUpdateDto updateDto) {
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
