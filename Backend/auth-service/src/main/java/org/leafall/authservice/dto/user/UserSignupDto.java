package org.leafall.authservice.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignupDto {

    @Email(message = "Request must contains email")
    @NotBlank(message = "Request must contains new email")
    private String email;

    @NotBlank(message = "Request must contains username")
    private String username;

    @NotBlank(message = "Request must contains password")
    private String password;
}
