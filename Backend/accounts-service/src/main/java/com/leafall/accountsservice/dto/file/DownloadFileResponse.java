package com.leafall.accountsservice.dto.file;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

@Data
@Builder
public class DownloadFileResponse {

    private InputStream in;
    private String originalFileName;
}
