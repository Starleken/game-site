package org.leafall.fileservicestarter.dto;

import java.util.Objects;
import java.util.UUID;

public class FileResponseDto {

    private UUID id;
    private String fileUrl;
    private String originalFileName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileResponseDto that = (FileResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(fileUrl, that.fileUrl) && Objects.equals(originalFileName, that.originalFileName);
    }

    @Override
    public String toString() {
        return "FileResponseDto{" +
                "id=" + id +
                ", fileUrl='" + fileUrl + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileUrl, originalFileName);
    }
}
