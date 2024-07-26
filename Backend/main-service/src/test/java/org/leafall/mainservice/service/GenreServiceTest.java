package org.leafall.mainservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.leafall.mainservice.core.utils.entity.GenreEntityUtils;
import org.leafall.mainservice.core.utils.equals.GenreEqualsUtils;
import org.leafall.mainservice.exception.EntityNotFoundException;
import org.leafall.mainservice.mapper.GenreMapper;
import org.leafall.mainservice.repository.GenreRepository;
import org.leafall.mainservice.service.impl.GenreServiceImpl;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.leafall.mainservice.core.utils.dto.GenreDtoUtils.*;
import static org.leafall.mainservice.core.utils.entity.GenreEntityUtils.*;
import static org.leafall.mainservice.core.utils.equals.GenreEqualsUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @Spy
    private GenreMapper genreMapper = Mappers.getMapper(GenreMapper.class);

    @InjectMocks
    private GenreServiceImpl genreService;

    @Test
    void testFindAll_happyPath() {
        //given
        when(genreRepository.findAll()).thenReturn(List.of(generateGenre(), generateGenre()));

        //when
        var responseDtos = genreService.findAll();

        //then
        assertEquals(2, responseDtos.size());
    }

    @Test
    void testCreate_happyPath() {
        //given
        var createDto = generateCreateDto();

        when(genreRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        //when
        var responseDto = genreService.create(createDto);

        //then
        equal(responseDto, createDto);
    }

    @Test
    void testUpdate_happyPath() {
        //given
        var idToGenerate = 1L;
        var generatedGenre = generateGenre();
        generatedGenre.setId(idToGenerate);
        var updateDto = generateUpdateDto(generatedGenre.getId());

        when(genreRepository.findById(idToGenerate)).thenReturn(Optional.of(generatedGenre));
        when(genreRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        //when
        var responseDto = genreService.update(updateDto);

        //then
        equal(responseDto, updateDto);
    }

    @Test
    void testUpdate_whenGenreNotFound() {
        //given
        var idToSearch = 1L;
        var updateDto = generateUpdateDto(idToSearch);

        when(genreRepository.findById(idToSearch)).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> genreService.update(updateDto));

        //then
    }

    @Test
    void testDeleteById_happyPath() {
        //given
        var idToSearch = 1L;
        var generatedGenre = generateGenre();
        generatedGenre.setId(idToSearch);

        when(genreRepository.findById(idToSearch)).thenReturn(Optional.of(generatedGenre));

        //when
        genreService.deleteById(idToSearch);

        //then
        verify(genreRepository).deleteById(generatedGenre.getId());
    }

    @Test
    void testDeleteById_whenGenreNotFound() {
        //given
        var idToSearch = 1L;

        when(genreRepository.findById(idToSearch)).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> genreService.deleteById(idToSearch));

        //then
    }
}
