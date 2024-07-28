package org.leafall.mainservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.leafall.fileservicestarter.service.FileService;
import org.leafall.mainservice.core.utils.FileUtils;
import org.leafall.mainservice.core.utils.dto.PostDtoUtils;
import org.leafall.mainservice.core.utils.entity.PostEntityUtils;
import org.leafall.mainservice.core.utils.equals.PostEqualsUtils;
import org.leafall.mainservice.exception.EntityNotFoundException;
import org.leafall.mainservice.mapper.PostMapper;
import org.leafall.mainservice.repository.PostRepository;
import org.leafall.mainservice.service.impl.PostServiceImpl;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.leafall.mainservice.core.utils.FileUtils.*;
import static org.leafall.mainservice.core.utils.dto.PostDtoUtils.*;
import static org.leafall.mainservice.core.utils.entity.PostEntityUtils.*;
import static org.leafall.mainservice.core.utils.equals.PostEqualsUtils.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private FileService fileService;

    @Spy
    private PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void testFindAll_happyPath() {
        //given
        var postsToReturn = List.of(generatePost(), generatePost());
        when(postRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(postsToReturn));

        //when
        var responseDtos = postService.findAll(1, postsToReturn.size());

        //then
        assertEquals(postsToReturn.size(), responseDtos.getTotalElements());
    }

    @Test
    void testFindById_happyPath() {
        //given
        var idToSearch = 1L;
        var generatedFile = getFileResponseDto();
        var generatedPost = generatePost(generatedFile);
        generatedPost.setId(idToSearch);

        when(postRepository.findById(idToSearch)).thenReturn(Optional.of(generatedPost));
        when(fileService.findById(any())).thenReturn(generatedFile);

        //when
        var responseDto = postService.findById(idToSearch);

        //then
        equal(generatedPost, responseDto);
        assertNotNull(responseDto.getImage());
    }

    @Test
    void testFindById_whenPostNotFound() {
        //given
        var idToSearch = 1L;

        when(postRepository.findById(idToSearch)).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> postService.findById(idToSearch));

        //then
    }

    @Test
    void testCreate_happyPath() {
        //given
        var generatedFile = getFileResponseDto();
        var createDto = generateCreateDto();

        when(fileService.upload(any())).thenReturn(generatedFile);
        when(postRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        //when
        var responseDto = postService.create(createDto);

        //then
        assertNotNull(responseDto.getImage());
        equal(createDto, responseDto);
    }

    @Test
    void testUpdate_happyPath() {
        //given
        var generatedFile = getFileResponseDto();
        var idToGenerate = 1L;
        var generatedPost = generatePost();
        generatedPost.setId(idToGenerate);
        var updateDto = generateUpdateDto(idToGenerate);

        when(postRepository.findById(idToGenerate)).thenReturn(Optional.of(generatedPost));
        when(postRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(fileService.findById(any())).thenReturn(generatedFile);

        //when
        var responseDto = postService.update(updateDto);

        //then
        assertNotNull(responseDto.getImage());
        equal(updateDto, responseDto);
    }

    @Test
    void testUpdate_whenPostNotFound() {
        //given
        var idToSearch = 1L;
        var updateDto = generateUpdateDto(idToSearch);

        when(postRepository.findById(idToSearch)).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> postService.update(updateDto));

        //then
    }

    @Test
    void testDeleteById_happyPath() {
        //given
        var idToGenerate = 1L;
        var generatedPost = generatePost();
        generatedPost.setId(idToGenerate);

        when(postRepository.findById(idToGenerate)).thenReturn(Optional.of(generatedPost));

        //when
        postService.deleteById(idToGenerate);

        //then
        verify(postRepository).deleteById(generatedPost.getId());
    }

    @Test
    void testDeleteById_whenPostNotFound() {
        //given
        var idToSearch = 1L;

        when(postRepository.findById(idToSearch)).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> postService.deleteById(idToSearch));

        //then
    }

    @Test
    void testChangeImage_happyPath() {
        //given
        var idToGenerate = 1L;
        var generatedFile = getFileResponseDto();
        var generatedPost = generatePost();
        generatedPost.setId(idToGenerate);
        var changeImageDto = generateChangeImageDto(idToGenerate);

        when(postRepository.findById(idToGenerate)).thenReturn(Optional.of(generatedPost));
        when(fileService.upload(any())).thenReturn(generatedFile);
        when(postRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(fileService.findById(any())).thenReturn(generatedFile);

        //when
        var responseDto = postService.changeImage(changeImageDto);

        //then
        assertEquals(generatedFile, responseDto.getImage());
    }

    @Test
    void testChangeImage_whenPostNotFound() {
        //given
        var idToSearch = 1L;
        var changeImageDto = generateChangeImageDto(idToSearch);

        when(postRepository.findById(idToSearch)).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> postService.changeImage(changeImageDto));

        //then
    }
}
