package org.leafall.authservice.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordDto {

    @NotNull(message = "Request must contains user id")
    private Long id;

    @NotBlank(message = "Request must contains old password")
    private String oldPassword;

    @NotBlank(message = "Request must contains new password")
    private String newPassword;
}
