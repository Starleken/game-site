package org.leafall.authservice.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDto {

    @NotNull(message = "Request must contains user id")
    private Long id;

    @NotBlank(message = "Request must contains username")
    private String username;
}
