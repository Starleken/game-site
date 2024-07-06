package com.leafall.accountsservice.service.impl;

import com.leafall.accountsservice.dto.file.FileResponseDto;
import com.leafall.accountsservice.dto.game.*;
import com.leafall.accountsservice.entity.GameEntity;
import com.leafall.accountsservice.mapper.GameMapper;
import com.leafall.accountsservice.repository.FileRepository;
import com.leafall.accountsservice.repository.GameRepository;
import com.leafall.accountsservice.repository.GenreRepository;
import com.leafall.accountsservice.service.FileService;
import com.leafall.accountsservice.service.GameService;
import com.leafall.accountsservice.service.GenreService;
import com.leafall.accountsservice.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.leafall.accountsservice.utils.ExceptionUtils.*;

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
        var files = fileService.getAllByIds(imageIds);

        List<GameResponseShortDto> dtos = new ArrayList<>();
        for (var entity : entities) {
            var dto = gameMapper.mapToShortDto(entity);
            for (var file : files) {
                if (file.getId() == entity.getHeaderImage()) {
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
        dto.setHeaderImage(fileService.getById(found.getHeaderImage()));
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
        dto.setHeaderImage(fileService.getById(saved.getHeaderImage()));
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
        responseDto.setHeaderImage(fileService.getById(updated.getHeaderImage()));
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
