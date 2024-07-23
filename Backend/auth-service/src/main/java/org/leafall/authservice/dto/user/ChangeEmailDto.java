package org.leafall.authservice.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeEmailDto {

    @NotNull(message = "Request must contains user id")
    private Long id;

    @Email(message = "Request must contains new email")
    @NotBlank(message = "Request must contains new email")
    private String newEmail;
}
