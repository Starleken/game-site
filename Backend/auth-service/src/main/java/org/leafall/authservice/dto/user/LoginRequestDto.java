package org.leafall.authservice.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.leafall.authservice.component.JwtFilter;

@Data
@Builder
public class LoginRequestDto {

    @Email(message = "Request must contains email")
    @NotBlank(message = "Request must contains new email")
    private String email;

    @NotBlank(message = "Request must contains password")
    private String password;
}
