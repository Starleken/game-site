package config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fileservice-starter")
public class FileServiceMapProperties {

    private String fileServiceUrl;

    public String getFileServiceUrl() {
        return fileServiceUrl;
    }

    public void setFileServiceUrl(String fileServiceUrl) {
        this.fileServiceUrl = fileServiceUrl;
    }
}
