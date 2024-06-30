package com.leafall.accountsservice.service.impl;

import com.leafall.accountsservice.dto.genre.GenreCreateDto;
import com.leafall.accountsservice.dto.genre.GenreResponseDto;
import com.leafall.accountsservice.dto.genre.GenreUpdateDto;
import com.leafall.accountsservice.entity.GenreEntity;
import com.leafall.accountsservice.mapper.GenreMapper;
import com.leafall.accountsservice.repository.GenreRepository;
import com.leafall.accountsservice.service.GenreService;
import com.leafall.accountsservice.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.leafall.accountsservice.utils.ExceptionUtils.*;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public List<GenreResponseDto> findAll() {
        var entities = genreRepository.findAll();

        return entities.stream()
                .map(genreMapper::mapToDto)
                .toList();
    }

    @Override
    public GenreResponseDto create(GenreCreateDto createDto) {
        var toSave = genreMapper.mapToEntity(createDto);

        var savedEntity = genreRepository.save(toSave);
        return genreMapper.mapToDto(savedEntity);
    }

    @Override
    public GenreResponseDto update(GenreUpdateDto updateDto) {
        var foundEntity = genreRepository.findById(updateDto.getId())
                .orElseThrow(() -> getEntityNotFoundException(GenreEntity.class));

        genreMapper.update(foundEntity, updateDto);
        var updated = genreRepository.save(foundEntity);
        return genreMapper.mapToDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        var foundEntity = genreRepository.findById(id)
                .orElseThrow(() -> getEntityNotFoundException(GenreEntity.class));

        genreRepository.deleteById(id);
    }
}
