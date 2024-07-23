package org.leafall.authservice.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.leafall.authservice.entity.UserEntityRole;

@Data
@Builder
public class UserCreateDto {

    @Email(message = "Request must contains email")
    @NotBlank(message = "Request must contains new email")
    private String email;

    @NotBlank(message = "Request must contains username")
    private String username;

    @NotBlank(message = "Request must contains password")
    private String password;

    @NotNull(message = "Request must contains user role")
    private UserEntityRole role;
}
