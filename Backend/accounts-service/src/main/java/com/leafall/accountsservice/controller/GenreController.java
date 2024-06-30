package com.leafall.accountsservice.controller;

import com.leafall.accountsservice.dto.genre.GenreCreateDto;
import com.leafall.accountsservice.dto.genre.GenreResponseDto;
import com.leafall.accountsservice.dto.genre.GenreUpdateDto;
import com.leafall.accountsservice.entity.GenreEntity;
import com.leafall.accountsservice.entity.PostEntity;
import com.leafall.accountsservice.service.GenreService;
import com.leafall.accountsservice.utils.LogUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.leafall.accountsservice.utils.LogUtils.*;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@Slf4j
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<GenreResponseDto>> findAll() {
        log.info(getRequest(GenreEntity.class, "Find all"));
        var entities = genreService.findAll();
        log.info(getRequest(GenreEntity.class, entities.size()));

        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenreResponseDto> create(@RequestBody GenreCreateDto createDto) {
        log.info(getRequest(GenreEntity.class, "create", createDto));
        var created = genreService.create(createDto);
        log.info(getResultRequest(GenreEntity.class, "created", created));

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<GenreResponseDto> update(@RequestBody GenreUpdateDto updateDto) {
        log.info(getRequest(GenreEntity.class, "update", updateDto));
        var updated = genreService.update(updateDto);
        log.info(getResultRequest(GenreEntity.class, "updated", updated));

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info(getRequest(GenreEntity.class, "delete", "id", id));
        genreService.deleteById(id);
        log.info(getResultRequest(GenreEntity.class, "deleted", "id", id));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
