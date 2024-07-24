package org.leafall.mainservice.service.impl;

import org.leafall.fileservicestarter.dto.FileResponseDto;
import org.leafall.fileservicestarter.service.FileService;
import org.leafall.mainservice.dto.game.*;
import org.leafall.mainservice.entity.GameEntity;
import org.leafall.mainservice.mapper.GameMapper;
import org.leafall.mainservice.repository.GameRepository;
import org.leafall.mainservice.repository.GenreRepository;
import org.leafall.mainservice.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.leafall.mainservice.utils.ExceptionUtils.*;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final GameMapper gameMapper;
    private final FileService fileService;

    @Override
    public List<GameResponseShortDto> findAll() {
        var entities = gameRepository.findAll();

        var imageIds = entities.stream()
                        .map(GameEntity::getHeaderImage)
                        .toList();
        var files = fileService.findAllByIds(imageIds);

        List<GameResponseShortDto> dtos = new ArrayList<>();
        for (var entity : entities) {
            var dto = gameMapper.mapToShortDto(entity);
            for (var file : files) {
                if (file.getId().equals(entity.getHeaderImage())) {
                    dto.setHeaderImage(file);
                }
            }
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public GameResponseDto findById(Long id) {
        var found = gameRepository.findById(id)
                .orElseThrow(() -> getEntityNotFoundException(GameEntity.class));

        var dto = gameMapper.mapToDto(found);
        dto.setHeaderImage(fileService.findById(found.getHeaderImage()));
        return dto;
    }

    @Override
    public GameResponseDto create(GameCreateDto createDto) {
        var toSave = gameMapper.mapToEntity(createDto);

        toSave.setGenres(genreRepository.findAllById(createDto.getGenres()));
        toSave.setHeaderImage(fileService.upload(createDto.getHeaderImage()).getId());
        var savedImages = fileService.uploadAll(createDto.getImages());
        var imageIds = savedImages.stream()
                .map(FileResponseDto::getId)
                .toList();
        toSave.setImages(imageIds);

        var saved = gameRepository.save(toSave);
        var dto = gameMapper.mapToDto(saved);
        dto.setHeaderImage(fileService.findById(saved.getHeaderImage()));
        return dto;
    }

    @Override
    public GameResponseDto update(GameUpdateDto updateDto) {
        var toUpdate = gameRepository.findById(updateDto.getId())
                .orElseThrow(() -> getEntityNotFoundException(GameEntity.class));

        gameMapper.update(toUpdate, updateDto);
        toUpdate.setGenres(genreRepository.findAllById(updateDto.getGenres()));
        var updated = gameRepository.save(toUpdate);

        var responseDto = gameMapper.mapToDto(updated);
        responseDto.setHeaderImage(fileService.findById(updated.getHeaderImage()));
        return responseDto;
    }

    @Override
    public GameResponseDto changeImage(GameChangeImageDto changeImageDto) {
        var toUpdate = gameRepository.findById(changeImageDto.getId())
                .orElseThrow(() -> getEntityNotFoundException(GameEntity.class));

        var newFile = fileService.upload(changeImageDto.getFile());
        toUpdate.setHeaderImage(newFile.getId());

        var updated = gameRepository.save(toUpdate);
        var dto = gameMapper.mapToDto(updated);
        dto.setHeaderImage(newFile);
        return dto;
    }

    @Override
    public void deleteById(Long id) {
        gameRepository.findById(id)
                        .orElseThrow(() -> getEntityNotFoundException(GameEntity.class));

        gameRepository.deleteById(id);
    }
}
