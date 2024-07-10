package service;

import dto.FileResponseDto;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;

public class FileService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String fileServiceUrl;

    public FileService(String fileServiceUrl) {
        this.fileServiceUrl = fileServiceUrl;
    }

    public FileResponseDto findById(UUID id) {
        var url = fileServiceUrl + "/" + id;
        return restTemplate.getForObject(url, FileResponseDto.class);
    }

    public List<FileResponseDto> findAllByIds(List<UUID> ids) {
        var url = fileServiceUrl + "/ids?" + ids.stream()
                .map(UUID::toString)
                .collect(Collectors.joining("&id=", "id=", ""));
        var requestEntity = new HttpEntity<>(getHeaders(APPLICATION_JSON));
        ParameterizedTypeReference<List<FileResponseDto>> responseType = new ParameterizedTypeReference<>() {};
        var response = restTemplate.exchange(url, GET, requestEntity, responseType);

        return response.getBody();
    }

    @SneakyThrows
    public FileResponseDto upload(MultipartFile file) {
        var url = fileServiceUrl + "/upload";
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, getHeaders(MULTIPART_FORM_DATA));

        return restTemplate.exchange(url, POST, requestEntity, FileResponseDto.class).getBody();
    }

    @SneakyThrows
    public List<FileResponseDto> uploadAll(List<MultipartFile> files) {
        var url = fileServiceUrl + "/upload/all";
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (var file : files) {
            body.add("files", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, getHeaders(MULTIPART_FORM_DATA));
        ParameterizedTypeReference<List<FileResponseDto>> responseType = new ParameterizedTypeReference<>() {};

        return restTemplate.exchange(url, POST, requestEntity, responseType).getBody();
    }


    private HttpHeaders getHeaders(MediaType mediaType) {
        var headers = new HttpHeaders();
        headers.setContentType(mediaType);

        return headers;
    }
}
