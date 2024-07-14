package com.example.fileservice.controller;

import com.example.fileservice.dto.*;
import com.example.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.HistoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
@Slf4j
public class FileController {

    private final FileService fileService;

    @GetMapping("/url")
    public ResponseEntity<DownloadFileResponseDto> download(@RequestBody DownloadFileRequestDto requestDto) {
        log.info("FileEntity download requested: " + requestDto.toString());
        var response = fileService.download(requestDto);
        log.info("FileEntity download request. Response: " + response.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileResponseDto> upload(@ModelAttribute FileUploadDto uploadDto) {
        log.info("FileEntity upload requested: " + uploadDto.toString());
        var response = fileService.upload(uploadDto);
        log.info("FileEntity upload request. Response: " + response.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/upload/all", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<FileResponseDto>> uploadAll(@ModelAttribute FileUploadDtoWrapper fileUploadDto) {
        log.info("FileEntity upload all requested. Files: " + fileUploadDto.getFiles().size());
        var response = fileService.uploadAll(fileUploadDto);
        log.info("FileEntity upload all request. Files: " + response.size());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileResponseDto> getById(@PathVariable UUID id) {
        log.info("FileEntity get by id requested. Id: " + id.toString());
        var response = fileService.getById(id);
        log.info("FileEntity get by id request. Response: " + response.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/ids")
    public ResponseEntity<List<FileResponseDto>> getAllByIds(@RequestParam List<UUID> id) {
        log.info("FileEntity get all by ids requested. Id count: " + id.size());
        var response = fileService.getAllByIds(id);
        log.info("FileEntity get all by ids request. Files: " + response.size());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
