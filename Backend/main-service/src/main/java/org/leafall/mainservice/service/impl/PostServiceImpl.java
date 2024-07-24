package org.leafall.mainservice.service.impl;

import org.leafall.fileservicestarter.service.FileService;
import org.leafall.mainservice.entity.PostEntity;
import org.leafall.mainservice.mapper.PostMapper;
import org.leafall.mainservice.repository.PostRepository;
import org.leafall.mainservice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.leafall.mainservice.dto.post.*;
import org.leafall.mainservice.utils.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final FileService fileService;
    private final PostMapper postMapper;

    @Override
    public List<PostResponseShortDto> findAll() {
        var entities = postRepository.findAll();

        return entities.stream()
                .map(postMapper::mapToShortDto)
                .toList();
    }

    @Override
    public PostResponseDto findById(long id) {
        var found = postRepository.findById(id)
                .orElseThrow(() -> ExceptionUtils.getEntityNotFoundException(PostEntity.class));

        var responseDto = postMapper.mapToDto(found);
        responseDto.setImage(fileService.findById(found.getImageId()));
        return responseDto;
    }

    @Override
    public PostResponseDto create(PostCreateDto createDto) {
        var entity = postMapper.mapToEntity(createDto);
        var image = fileService.upload(createDto.getFile());
        entity.setImageId(image.getId());

        var saved = postRepository.save(entity);
        var responseDto = postMapper.mapToDto(saved);
        responseDto.setImage(image);
        return responseDto;
    }

    @Override
    public PostResponseDto update(PostUpdateDto updateDto) {
        var found = postRepository.findById(updateDto.getId())
                .orElseThrow(() -> ExceptionUtils.getEntityNotFoundException(PostEntity.class));
        postMapper.update(found, updateDto);

        var saved = postRepository.save(found);
        var responseDto = postMapper.mapToDto(saved);
        responseDto.setImage(fileService.findById(saved.getImageId()));
        return responseDto;
    }

    @Override
    public void deleteById(long id) {
        postRepository.findById(id)
                .orElseThrow(() -> ExceptionUtils.getEntityNotFoundException(PostEntity.class));

        postRepository.deleteById(id);
    }

    @Override
    public PostResponseDto changeImage(PostChangeImageDto imageDto) {
        var found = postRepository.findById(imageDto.getId())
                .orElseThrow(() -> ExceptionUtils.getEntityNotFoundException(PostEntity.class));

        var image = fileService.upload(imageDto.getFile());
        found.setImageId(image.getId());

        var saved = postRepository.save(found);
        var responseDto = postMapper.mapToDto(saved);
        responseDto.setImage(fileService.findById(image.getId()));
        return responseDto;
    }
}
