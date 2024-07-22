package org.leafall.authservice.dto.user;

import lombok.Builder;
import lombok.Data;
import org.leafall.authservice.component.JwtFilter;

@Data
@Builder
public class LoginRequestDto {

    private String email;
    private String password;
}
