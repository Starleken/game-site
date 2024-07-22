package org.leafall.authservice.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
public class UserSignupDto {

    private String email;
    private String username;
    private String password;
}
