package com.leafall.accountsservice.service;

import com.leafall.accountsservice.dto.genre.GenreCreateDto;
import com.leafall.accountsservice.dto.genre.GenreResponseDto;
import com.leafall.accountsservice.dto.genre.GenreUpdateDto;

import java.util.List;

public interface GenreService {

    List<GenreResponseDto> findAll();

    GenreResponseDto create(GenreCreateDto createDto);

    GenreResponseDto update(GenreUpdateDto updateDto);

    void deleteById(Long id);
}
