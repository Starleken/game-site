package org.leafall.mainservice.service;

import org.leafall.mainservice.dto.genre.GenreCreateDto;
import org.leafall.mainservice.dto.genre.GenreResponseDto;
import org.leafall.mainservice.dto.genre.GenreUpdateDto;

import java.util.List;

public interface GenreService {

    List<GenreResponseDto> findAll();

    GenreResponseDto create(GenreCreateDto createDto);

    GenreResponseDto update(GenreUpdateDto updateDto);

    void deleteById(Long id);
}
