package org.leafall.mainservice.service.impl;

import org.leafall.mainservice.dto.genre.GenreCreateDto;
import org.leafall.mainservice.dto.genre.GenreResponseDto;
import org.leafall.mainservice.dto.genre.GenreUpdateDto;
import org.leafall.mainservice.entity.GenreEntity;
import org.leafall.mainservice.mapper.GenreMapper;
import org.leafall.mainservice.repository.GenreRepository;
import org.leafall.mainservice.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.leafall.mainservice.utils.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> ExceptionUtils.getEntityNotFoundException(GenreEntity.class));

        genreMapper.update(foundEntity, updateDto);
        var updated = genreRepository.save(foundEntity);
        return genreMapper.mapToDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        var foundEntity = genreRepository.findById(id)
                .orElseThrow(() -> ExceptionUtils.getEntityNotFoundException(GenreEntity.class));

        genreRepository.deleteById(id);
    }
}
