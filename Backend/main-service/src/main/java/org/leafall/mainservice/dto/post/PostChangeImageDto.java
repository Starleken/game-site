package org.leafall.mainservice.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class PostChangeImageDto {

    @NotNull(message = "Request must contain id of the post")
    private Long id;

    @NotNull(message = "Request must contain an image of the post")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MultipartFile file;
}
